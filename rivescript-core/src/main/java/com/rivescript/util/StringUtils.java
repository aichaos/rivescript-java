/*
 * Copyright (c) 2016 the original author or authors.
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

package com.rivescript.util;

import com.rivescript.regexp.Regexp;

import java.util.HashSet;
import java.util.Set;

import static com.rivescript.regexp.Regexp.RE_NASTIES;

/**
 * Miscellaneous {@link String} utility methods.
 *
 * @author Noah Petherbridge
 * @author Marcel Overdijk
 */
public class StringUtils {

	private static final char ESCAPE_CHAR = '\\';

	private static final Set<Character> UNSAFE_CHARS = new HashSet<>();

	static {
		UNSAFE_CHARS.add('\\');
		UNSAFE_CHARS.add('.');
		UNSAFE_CHARS.add('+');
		UNSAFE_CHARS.add('*');
		UNSAFE_CHARS.add('?');
		UNSAFE_CHARS.add('[');
		UNSAFE_CHARS.add('^');
		UNSAFE_CHARS.add(']');
		UNSAFE_CHARS.add('$');
		UNSAFE_CHARS.add('(');
		UNSAFE_CHARS.add(')');
		UNSAFE_CHARS.add('{');
		UNSAFE_CHARS.add('}');
		UNSAFE_CHARS.add('=');
		UNSAFE_CHARS.add('!');
		UNSAFE_CHARS.add('<');
		UNSAFE_CHARS.add('>');
		UNSAFE_CHARS.add('|');
		UNSAFE_CHARS.add(':');
	}

	/**
	 * Counts the number of words in a {@link String}.
	 *
	 * @param str the string to count
	 * @param all count all
	 * @return the number of words
	 */
	public static int countWords(String str, boolean all) {
		if (str == null || str.length() == 0) {
			return 0;
		}
		String[] words;
		if (all) {
			words = str.split("\\s+"); // Splits at whitespaces.
		} else {
			words = str.split("[\\s\\*\\#\\_\\|]+");
		}

		int count = 0;
		for (String word : words) {
			if (word.length() > 0) {
				count++;
			}
		}

		return count;
	}

	/**
	 * Joins a {@link String} array into a single {@link String}.
	 *
	 * @param array     the array to join
	 * @param delimiter the delimiter character to use, {@code null} is treated as {@code ""}
	 */
	public static String join(String[] array, String delimiter) {
		if (array == null) {
			return null;
		}
		if (delimiter == null) {
			delimiter = "";
		}
		StringBuilder buf = new StringBuilder();
		for (int i = 0; i < array.length; i++) {
			buf.append(array[i]);
			if (i < array.length - 1) {
				buf.append(delimiter);
			}
		}
		return buf.toString();
	}

	/**
	 * Escapes a {@link String} for use in a regular expression.
	 *
	 * @param str the string to escape
	 * @return the escaped string
	 */
	public static String quoteMetacharacters(String str) {
		if (str == null) {
			return null;
		}

		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < str.length(); i++) {
			char c = str.charAt(i);
			if (UNSAFE_CHARS.contains(c)) {
				sb.append(ESCAPE_CHAR);
			}
			sb.append(c);
		}

		return sb.toString();
	}

	/**
	 * Strips special characters out of a {@link String}.
	 *
	 * @param str the string to strip
	 * @return the stripped string
	 * @see Regexp#RE_NASTIES
	 */
	public static String stripNasties(String str) {
		if (str == null) {
			return null;
		}
		return RE_NASTIES.matcher(str).replaceAll("");
	}
}

