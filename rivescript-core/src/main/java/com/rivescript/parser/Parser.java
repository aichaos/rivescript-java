/*
 * Copyright (c) 2016-2017 the original author or authors.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.rivescript.parser;

import com.rivescript.ConcatMode;
import com.rivescript.Config;
import com.rivescript.ast.ObjectMacro;
import com.rivescript.ast.Root;
import com.rivescript.ast.Trigger;
import com.rivescript.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Parser for RiveScript source code.
 *
 * @author Noah Petherbridge
 * @author Marcel Overdijk
 */
public class Parser {

	/**
	 * The supported version of the RiveScript language.
	 */
	public static final double RS_VERSION = 2.0;

	private static Logger logger = LoggerFactory.getLogger(Parser.class);

	private boolean strict;
	private boolean utf8;
	private boolean forceCase;
	private ConcatMode concat;

	/**
	 * Creates a new {@link Parser} with a default {@link ParserConfig}.
	 */
	public Parser() {
		this(null);
	}

	/**
	 * Creates a new {@link Parser} with the given {@link ParserConfig}.
	 *
	 * @param config the config
	 */
	public Parser(ParserConfig config) {
		if (config == null) {
			config = new ParserConfig();
		}
		this.strict = config.isStrict();
		this.utf8 = config.isUtf8();
		this.forceCase = config.isForceCase();
		this.concat = config.getConcat();
	}

	/**
	 * Parses the RiveScript source code.
	 * <p>
	 * This will return an Abstract Syntax Tree {@link Root} object containing all of the relevant information parsed from the source code.
	 * <p>
	 * In case of errorMessages (e.g. a syntax error when strict mode is enabled) a {@link ParserException} will be thrown.
	 *
	 * @param filename the arbitrary name for the source code being parsed
	 * @param code     the lines of RiveScript source code
	 * @return the AST root object
	 * @throws ParserException in case of a parsing error
	 */
	public Root parse(String filename, String[] code) throws ParserException {

		logger.debug("Parsing {}", filename);

		if (logger.isTraceEnabled()) {
			for (String line : code) {
				logger.trace("{}", line);
			}
		}

		long startTime = System.currentTimeMillis();

		// Create the root Abstract Syntax Tree (AST).
		Root ast = new Root();

		// Track temporary variables.
		String topic = "random";           // Default topic = random
		int lineno = 0;                    // Line numbers for syntax tracking
		boolean inComment = false;         // In a multi-line comment
		boolean inObject = false;          // In an object macro
		String objectName = null;          // The name of the current object
		String objectLanguage = null;      // The programming language of the current object
		List<String> objectBuffer = null;  // The source code buffer of the current object
		Trigger currentTrigger = null;     // The current trigger
		String previous = null;            // The a %Previous trigger

		// Local (file-scoped) parser options.
		Map<String, String> localOptions = new HashMap<>();

		// Go through the lines of code.
		for (int lp = 0; lp < code.length; lp++) {
			lineno = lp + 1;

			// Strip the line.
			String line = code[lp].trim();
			if (line.length() == 0) {
				continue; // Skip blank lines!
			}

			// Are we inside an `> object`?
			if (inObject) {
				// End of the object?
				if (line.contains("< object") || line.contains("<object")) {
					if (objectName != null && objectName.length() > 0) {
						ObjectMacro object = new ObjectMacro();
						object.setName(objectName);
						object.setLanguage(objectLanguage);
						object.setCode(objectBuffer);
						ast.addObject(object);
					}
					inObject = false;
					objectName = null;
					objectLanguage = null;
					objectBuffer = null;
				} else {
					objectBuffer.add(line);
				}
				continue;
			}

			// Look for comments.
			if (line.startsWith("//")) {
				continue; // Single line comment.
			} else if (line.startsWith("/*")) {
				// Start of a multi-line comment.
				if (line.contains("*/")) {
					continue; // The end comment is on the same line!
				}
				// We're now inside a multi-line comment.
				inComment = true;
				continue;
			} else if (line.contains("*/")) {
				// End of a multi-line comment.
				inComment = false;
				continue;
			} else if (inComment) {
				continue;
			}

			// Separate the command from its data.
			if (line.length() < 2) {
				logger.warn("Weird single-character line '{}' at {} line {}", line, filename, lineno);
				continue;
			}
			String cmd = line.substring(0, 1);
			line = line.substring(1);

			// Ignore in-line comments if there's a space before and after the "//".
			if (line.contains(" // ")) {
				line = line.substring(0, line.indexOf(" // "));
			}

			line = line.trim();

			// In the event of a +Trigger, if we are force-lowercasing it, then do so now before the syntax check.
			if (forceCase && cmd.equals("+")) {
				line = line.toLowerCase();
			}

			logger.debug("Cmd: {}; line: {}", cmd, line);

			// Run a syntax check on this line.
			try {
				checkSyntax(cmd, line);
			} catch (ParserException e) {
				if (strict) {
					throw e; // Simply rethrow the parser exception.
				} else {
					logger.warn("Syntax error '{}' at {} line {}", e.getMessage(), filename, lineno);
				}
			}

			// Reset the %Previous state if this is a new +Trigger.
			if (cmd.equals("+")) {
				previous = null;
			}

			// Do a look-ahead for ^Continue and %Previous commands.
			if (!cmd.equals("^")) {
				for (int li = (lp + 1); li < code.length; li++) {
					String lookahead = code[li].trim();
					if (lookahead.length() < 2) {
						continue;
					}
					String lookCmd = lookahead.substring(0, 1);
					lookahead = lookahead.substring(1).trim();

					// We only care about a couple lookahead command types.
					if (!lookCmd.equals("%") && !lookCmd.equals("^")) {
						break;
					}

					// Only continue if the lookahead has any data.
					if (lookahead.length() == 0) {
						break;
					}

					logger.debug("\tLookahead {}: {} {}", li, lookCmd, lookahead);

					// If the current command is a +, see if the following is a %.
					if (cmd.equals("+")) {
						if (lookCmd.equals("%")) {
							previous = lookahead;
							break;
						} else {
							previous = null;
						}
					}

					// If the current command is a ! and the next command(s) are ^,
					// we'll tack each extension on as a line break (which is useful information for arrays).
					if (cmd.equals("!")) {
						if (lookCmd.equals("^")) {
							line += "<crlf>" + lookahead;
						}
						continue;
					}

					// If the current command is not a ^, and the line after is not a %,
					// but the line after IS a ^, then tack it on to the end of the current line.
					if (!cmd.equals("^") && !lookCmd.equals("%")) {
						if (lookCmd.equals("^")) {
							// Which character to concatenate with?
							ConcatMode concat = null;
							if (localOptions.containsKey("concat")) {
								concat = ConcatMode.fromName(localOptions.get("concat"));
							}
							if (concat == null) {
								concat = this.concat != null ? this.concat : Config.DEFAULT_CONCAT;
							}
							line += concat.getConcatChar() + lookahead;
						}
					}
				}
			}

			// Handle the types of RiveScript commands.
			switch (cmd) {

				case "!": { // ! Define
					String[] halves = line.split("=", 2);
					String[] left = halves[0].trim().split(" ", 2);
					String value = "";
					String kind = ""; // global, var, sub, ...
					String name = "";
					if (halves.length == 2) {
						value = halves[1].trim();
					}
					if (left.length >= 1) {
						kind = left[0].trim();
						if (left.length >= 2) {
							left = Arrays.copyOfRange(left, 1, left.length);
							name = StringUtils.join(left, " ").trim();
						}
					}

					// Remove 'fake' line breaks unless this is an array.
					if (!kind.equals("array")) {
						value = value.replaceAll("<crlf>", "");
					}

					// Handle version numbers.
					if (kind.equals("version")) {
						double parsedVersion = 0;
						try {
							parsedVersion = Double.parseDouble(value);
						} catch (NumberFormatException e) {
							logger.warn("RiveScript version '{}' at {} line {} is not a valid floating number", value, filename, lineno);
						}
						if (parsedVersion > RS_VERSION) {
							throw new ParserException(
									String.format("Unsupported RiveScript version at %s line %d. We only support %s", filename, lineno,
											RS_VERSION));
						}
						continue;
					}

					// All other types of define's require a value and a variable name.
					if (name.length() == 0) {
						logger.warn("Undefined variable name at {} line {}", filename, lineno);
						continue;
					}
					if (value.length() == 0) {
						logger.warn("Undefined variable value at {} line {}", filename, lineno);
						continue;
					}

					// Handle the rest of the !Define types.
					switch (kind) {

						case "local": {
							// Local file-scoped parser options.
							logger.debug("\tSet local parser option {} = {}", name, value);
							localOptions.put(name, value);
							break;
						}
						case "global": {
							// Set a 'global' variable.
							logger.debug("\tSet global {} = {}", name, value);
							ast.getBegin().addGlobal(name, value);
							break;
						}
						case "var": {
							// Set a bot variable.
							logger.debug("\tSet bot variable {} = {}", name, value);
							ast.getBegin().addVar(name, value);
							break;
						}
						case "array": {
							// Set an array.
							logger.debug("\tSet array {} = {}", name, value);

							// Did we have multiple parts?
							String[] parts = value.split("<crlf>");

							// Process each line of array data.
							List<String> fields = new ArrayList<>();
							for (String val : parts) {
								if (val.contains("|")) {
									fields.addAll(Arrays.asList(val.split("\\|")));
								} else {
									fields.addAll(Arrays.asList(val.split("\\s+")));
								}
							}

							// Convert any remaining \s's over.
							for (int i = 0; i < fields.size(); i++) {
								fields.set(i, fields.get(i).replaceAll("\\\\s", " "));
							}

							ast.getBegin().addArray(name, fields);
							break;
						}
						case "sub": {
							// Substitutions.
							logger.debug("\tSet substitution {} = {}", name, value);
							ast.getBegin().addSub(name, value);
							break;
						}
						case "person": {
							// Person substitutions.
							logger.debug("\tSet person substitution {} = {}", name, value);
							ast.getBegin().addPerson(name, value);
							break;
						}
						default:
							logger.warn("Unknown definition type '{}' found at {} line {}", kind, filename, lineno);
					}
					break;
				}
				case ">": { // > Label
					String[] temp = line.trim().split(" ");
					String kind = temp[0];
					temp = Arrays.copyOfRange(temp, 1, temp.length);
					String name = "";
					String[] fields = new String[0];
					if (temp.length > 0) {
						name = temp[0];
						temp = Arrays.copyOfRange(temp, 1, temp.length);
					}
					if (temp.length > 0) {
						fields = temp;
					}

					// Handle the label types.
					if (kind.equals("begin")) {
						logger.debug("Found the BEGIN block at {} line {}", filename, lineno);
						kind = "topic";
						name = "__begin__";
					}
					if (kind.equals("topic")) {
						// Force case on topics.
						if (forceCase) {
							name = name.toLowerCase();
						}

						logger.debug("Set topic to {}", name);
						currentTrigger = null;
						topic = name;

						// Initialize the topic tree.
						ast.addTopic(topic);

						// Does this topic include or inherit another one?
						String mode = "";
						if (fields.length >= 2) {
							for (String field : fields) {
								if (field.equals("includes") || field.equals("inherits")) {
									mode = field;
								} else if (mode.equals("includes")) {
									ast.getTopic(topic).addInclude(field, true);
								} else if (mode.equals("inherits")) {
									ast.getTopic(topic).addInherit(field, true);
								}
							}
						}
					} else if (kind.equals("object")) {
						// If a field was provided, it should be the programming language.
						String language = "";
						if (fields.length > 0) {
							language = fields[0].toLowerCase();
						}

						// Start reading the object code.
						objectName = name;
						objectLanguage = language;
						objectBuffer = new ArrayList<>();
						inObject = true;

						// Missing language?
						if (language.equals("")) {
							logger.warn("No programming language specified for object '{}' at {} line", name, filename, lineno);
							objectLanguage = "__unknown__";
							continue;
						}
					} else {
						logger.warn("Unknown label type '{}' at {} line {}", kind, filename, lineno);
					}
					break;
				}
				case "<": { // < Label
					String kind = line;
					if (kind.equals("begin") || kind.equals("topic")) {
						logger.debug("\tEnd the topic label.");
						topic = "random"; // Go back to default topic.
					} else if (kind.equals("object")) {
						logger.debug("\tEnd the object label.");
						inObject = false;
					} else {
						logger.warn("Unknown end topic type '{}' at {} line {}", kind, filename, lineno);
					}
					break;
				}
				case "+": { // + Trigger
					logger.debug("\tTrigger pattern: {}", line);

					// Initialize the trigger tree.
					currentTrigger = new Trigger();
					currentTrigger.setTrigger(line);
					currentTrigger.setPrevious(previous);
					ast.getTopic(topic).addTrigger(currentTrigger);
					break;
				}
				case "-": { // - Response
					if (currentTrigger == null) {
						logger.warn("Response found before trigger at {} line {}", filename, lineno);
						continue;
					}

					// Warn if we also saw a hard redirect.
					if (currentTrigger.getRedirect() != null) {
						logger.warn("You can't mix @Redirects with -Replies at {} line {}", filename, lineno);
					}

					logger.debug("\tResponse: {}", line);
					currentTrigger.addReply(line);
					break;
				}
				case "*": { // * Condition
					if (currentTrigger == null) {
						logger.warn("Condition found before trigger at {} line {}", filename, lineno);
						continue;
					}

					logger.debug("\tCondition: {}", line);
					currentTrigger.addCondition(line);
					break;
				}
				case "%": { // % Previous
					// This was handled above.
					continue;
				}
				case "^": { // ^ Continue
					// This was handled above.
					continue;
				}
				case "@": { // @ Redirect
					if (currentTrigger == null) {
						logger.warn("Redirect found before trigger at {} line {}", filename, lineno);
						continue;
					}

					logger.debug("\tRedirect response to: {}", line);
					currentTrigger.setRedirect(line);
					break;
				}
				default:
					logger.warn("Unknown command '{}' found at {} line {}", cmd, filename, lineno);
			}
		}

		if (logger.isDebugEnabled()) {
			long elapsedTime = System.currentTimeMillis() - startTime;
			logger.debug("Parsing {} completed in {} ms", filename, elapsedTime);
		}

		return ast;
	}

	/**
	 * Checks the syntax of a RiveScript command.
	 *
	 * @param cmd  the single character command symbol
	 * @param line the rest of the line after the command
	 * @throws ParserException in case of syntax error
	 */
	private void checkSyntax(String cmd, String line) throws ParserException {
		// Run syntax tests based on the command used.
		if (cmd.equals("!")) {
			// ! Definition
			// - Must be formatted like this:
			//   ! type name = value
			//   OR
			//   ! type = value
			if (!line.matches("^(version|local|global|var|array|sub|person)(?:\\s+.+|)\\s*=\\s*.+?$")) {
				throw new ParserException("Invalid format for !Definition line: must be '! type name = value' OR '! type = value'");
			} else if (line.matches("^array")) {
				if (line.matches("\\=\\s?\\||\\|\\s?$")) {
					throw new ParserException("Piped arrays can't begin or end with a |");
				} else if (line.matches("\\|\\|")) {
					throw new ParserException("Piped arrays can't include blank entries");
				}
			}
		} else if (cmd.equals(">")) {
			// > Label
			// - The "begin" label must have only one argument ("begin").
			// - The "topic" label must be lowercased but can inherit other topics.
			// - The "object" label must follow the same rules as "topic", but don't need to be lowercased.
			String[] parts = line.split("\\s+");
			if (parts[0].equals("begin") && parts.length > 1) {
				throw new ParserException("The 'begin' label takes no additional arguments");
			} else if (parts[0].equals("topic")) {
				if (!forceCase && line.matches("[^a-z0-9_\\-\\s]")) {
					throw new ParserException("Topics should be lowercased and contain only letters and numbers");
				} else if (line.matches("[^A-Za-z0-9_\\-\\s]")) {
					throw new ParserException("Topics should contain only letters and numbers in forceCase mode");
				}
			} else if (parts[0].equals("object")) {
				if (line.matches("[^A-Za-z0-9\\_\\-\\s]")) {
					throw new ParserException("Objects can only contain numbers and letters");
				}
			}
		} else if (cmd.equals("+") || cmd.equals("%") || cmd.equals("@")) {
			// + Trigger, % Previous, @ Redirect
			// This one is strict. The triggers are to be run through the regexp engine,
			// therefore it should be acceptable for the regexp engine.
			// - Entirely lowercase.
			// - No symbols except: ( | ) [ ] * _ # { } < > =
			// - All brackets should be matched.
			int parens = 0, square = 0, curly = 0, angle = 0; // Count the brackets

			// Look for obvious errorMessages first.
			if (utf8) {
				// In UTF-8 mode, most symbols are allowed.
				if (line.matches("[A-Z\\\\.]")) {
					throw new ParserException("Triggers can't contain uppercase letters, backslashes or dots in UTF-8 mode");
				}
			} else if (line.matches("[^a-z0-9(|)\\[\\]*_#@{}<>=\\/\\s]")) {
				throw new ParserException(
						"Triggers may only contain lowercase letters, numbers, and these symbols: ( | ) [ ] * _ # { } < > = /");
			} else if (line.matches("\\(\\||\\|\\)")) {
				throw new ParserException("Piped alternations can't begin or end with a |");
			} else if (line.matches("\\([^\\)].+\\|\\|.+\\)")) {
				throw new ParserException("Piped alternations can't include blank entries");
			} else if (line.matches("\\[\\||\\|\\]")) {
				throw new ParserException("Piped optionals can't begin or end with a |");
			} else if (line.matches("\\[[^\\]].+\\|\\|.+\\]")) {
				throw new ParserException("Piped optionals can't include blank entries");
			}

			// Count the brackets.
			for (char c : line.toCharArray()) {
				switch (c) {
					case '(':
						parens++;
						break;
					case ')':
						parens--;
						break;
					case '[':
						square++;
						break;
					case ']':
						square--;
						break;
					case '{':
						curly++;
						break;
					case '}':
						curly--;
						break;
					case '<':
						angle++;
						break;
					case '>':
						angle--;
						break;
				}
			}

			// Any mismatches?
			if (parens != 0) {
				throw new ParserException("Unmatched parenthesis brackets");
			}
			if (square != 0) {
				throw new ParserException("Unmatched square brackets");
			}
			if (curly != 0) {
				throw new ParserException("Unmatched curly brackets");
			}
			if (angle != 0) {
				throw new ParserException("Unmatched angle brackets");
			}
		} else if (cmd.equals("*")) {
			// * Condition
			// Syntax for a conditional is as follows:
			// * value symbol value => response
			if (!line.matches("^.+?\\s*(?:==|eq|!=|ne|<>|<|<=|>|>=)\\s*.+?=>.+?$")) {
				throw new ParserException("Invalid format for !Condition: should be like '* value symbol value => response'");
			}
		}
	}
}
