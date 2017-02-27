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

package com.rivescript.cmd;

import com.rivescript.Config;
import com.rivescript.RiveScript;
import com.rivescript.RiveScriptException;
import com.rivescript.util.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import static com.rivescript.Config.DEFAULT_DEPTH;
import static com.rivescript.cmd.Shell.Color.GREEN;
import static com.rivescript.cmd.Shell.Color.RED;
import static com.rivescript.cmd.Shell.Color.YELLOW;

/**
 * Stand-alone RiveScript Interpreter.
 * <p>
 * This is an example program included with the RiveScript Java library. It serves as a way to quickly demo and test a RiveScript bot.
 * <p>
 * Usage:
 * <p>
 * {@code java com.rivescript.cmd.Shell [options] </path/to/documents>}
 * <p>
 * Options:
 * <ul>
 * <li>{@code --nostrict} Disable strict syntax checking
 * <li>{@code --utf8} Enable UTF-8 mode
 * <li>{@code --forcecase} Enable forcing triggers to lowercase
 * <li>{@code --depth=50} Override the recursion depth limit (default {@code 50})
 * <li>{@code --nocolor} Disable ANSI colors
 * </ul>
 *
 * @author Noah Petherbridge
 * @author Marcel Overdijk
 */
public class Shell {

	private boolean strict = true;
	private boolean utf8 = false;
	private boolean forceCase = false;
	private int depth = DEFAULT_DEPTH;
	private boolean noColor = false;

	/**
	 * Runs the RiveScript bot.
	 *
	 * @param args the arguments
	 */
	protected void run(String[] args) {

		// Collect command line arguments.
		List<String> arguments = new ArrayList<>(Arrays.asList(args));
		Iterator<String> it = arguments.iterator();
		while (it.hasNext()) {
			String argument = it.next();
			if (argument.charAt(0) == '-') {
				String flag = argument.replaceAll("^-*", "").trim();
				if (flag.equals("version")) {
					System.out.println("RiveScript-Java version " + RiveScript.getVersion());
					System.exit(0);
				} else if (flag.equals("nostrict")) {
					strict = false;
				} else if (flag.equals("utf8")) {
					utf8 = true;
				} else if (flag.equals("forcecase")) {
					forceCase = true;
				} else if (flag.startsWith("depth")) {
					depth = Integer.parseInt(flag.split("=", 2)[1]);
				} else if (flag.equals("nocolor")) {
					noColor = true;
				}
				it.remove();
			}
		}

		if (arguments.size() == 0) {
			System.err.println("Usage: java com.rivescript.cmd.Shell [options] </path/to/documents>");
			System.exit(0);
		}

		String root = arguments.get(0);

		Config config = Config.newBuilder()
				.throwExceptions(true)
				.strict(strict)
				.utf8(utf8)
				.forceCase(forceCase)
				.depth(depth)
				.build();

		// Initialize the bot.
		RiveScript bot = new RiveScript(config);
		init(bot);

		// Load the target directory.
		bot.loadDirectory(root);

		bot.sortReplies();

		System.out.println(""
				+ "      .   .       \n"
				+ "     .:...::      RiveScript Interpreter (Java)\n"
				+ "    .::   ::.     Library Version: " + RiveScript.getVersion() + "\n"
				+ " ..:;;. ' .;;:..  \n"
				+ "    .  '''  .     Type '/quit' to quit.\n"
				+ "     :;,:,;:      Type '/help' for more options.\n"
				+ "     :     :      \n"
				+ "\n"
				+ "Using the RiveScript bot found in: " + root + "\n"
				+ "Type a message to the bot and press Return to send it.");

		// Enter the main loop.
		BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
		while (true) {
			print(YELLOW, "You>");
			String text = "";
			try {
				text = stdin.readLine();
			} catch (IOException e) {
				print(RED, "Read error!");
			}
			text = text.trim();
			if (text.length() == 0) {
				continue;
			}

			if (text.startsWith("/help")) {
				help();
			} else if (text.startsWith("/quit")) {
				System.exit(0);
			} else if (text.startsWith("/dump t")) {
				bot.dumpTopics();
			} else if (text.startsWith("/dump s")) {
				bot.dumpSorted();
			} else {
				try {
					String reply = bot.reply("localuser", text);
					print(GREEN, "RiveScript>", reply, "\n");
				} catch (RiveScriptException e) {
					print(RED, "Error>", e.getMessage(), "\n");
				}
			}
		}
	}

	/**
	 * Initializes the RiveScript instance.
	 * <p>
	 * Override this method to define additional language handlers or Java object macro's.
	 *
	 * @param rs the RiveScript instance
	 */
	protected void init(RiveScript rs) {
	}

	/**
	 * The color names.
	 */
	protected enum Color {

		RED("1"),
		YELLOW("3"),
		GREEN("2"),
		CYAN("6");

		private String code;

		Color(String code) {
			this.code = code;
		}

		public String getCode() {
			return code;
		}
	}

	/**
	 * Prints colored text.
	 *
	 * @param color the color
	 * @param text  the text
	 */
	protected void print(Color color, String... text) {
		if (noColor) {
			System.out.print(StringUtils.join(text, " "));
		} else {
			System.out.print(String.format("\u001B[3%sm%s\u001B[0m %s", color.getCode(), text[0],
					StringUtils.join(Arrays.copyOfRange(text, 1, text.length), " ")));
		}
	}

	private void help() {
		System.out.println(""
				+ "Supported commands:\n"
				+ "- /help\n"
				+ "    Show this text.\n"
				+ "- /quit\n"
				+ "    Exit the program.\n"
				+ "- /dump <topics|sorted>\n"
				+ "    For debugging purposes, dump the topic and sorted trigger trees.");
	}

	public static void main(String[] args) {
		new Shell().run(args);
	}
}
