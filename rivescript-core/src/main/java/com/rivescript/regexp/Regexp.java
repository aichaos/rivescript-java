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

package com.rivescript.regexp;

import java.util.regex.Pattern;

/**
 * Miscellaneous compiled {@link Pattern} constants.
 *
 * @author Noah Petherbridge
 * @author Marcel Overdijk
 */
public class Regexp {

	public static final Pattern RE_ANY_TAG = Pattern.compile("<([^<]+?)>");
	public static final Pattern RE_ARRAY = Pattern.compile("@(.+?)\\b");
	public static final Pattern RE_BOT_VAR = Pattern.compile("<bot (.+?)>");
	public static final Pattern RE_CALL = Pattern.compile("<call>(.+?)</call>");
	public static final Pattern RE_CONDITION = Pattern.compile("^(.+?)\\s+(==|eq|!=|ne|<>|<|<=|>|>=)\\s+(.*?)$");
	public static final Pattern RE_INHERITS = Pattern.compile("\\{inherits=(\\d+)\\}");
	public static final Pattern RE_INPUT = Pattern.compile("<input([1-9])>");
	public static final Pattern RE_META = Pattern.compile("[\\<>]+");
	public static final Pattern RE_NASTIES = Pattern.compile("[^A-Za-z0-9_ ]");
	public static final Pattern RE_OPTIONAL = Pattern.compile("\\[(.+?)\\]");
	public static final Pattern RE_PLACEHOLDER = Pattern.compile("\\\\x00(\\d+)\\\\x00");
	public static final Pattern RE_RANDOM = Pattern.compile("\\{random\\}(.+?)\\{/random\\}");
	public static final Pattern RE_REDIRECT = Pattern.compile("\\{@([^\\}]*?)\\}");
	public static final Pattern RE_REPLY = Pattern.compile("<reply([1-9])>");
	public static final Pattern RE_SET = Pattern.compile("<set (.+?)=(.+?)>");
	public static final Pattern RE_SYMBOLS = Pattern.compile("[.?,!;:@#$%^&*()]+");
	public static final Pattern RE_TOPIC = Pattern.compile("\\{topic=(.+?)\\}");
	public static final Pattern RE_USER_VAR = Pattern.compile("<get (.+?)>");
	public static final Pattern RE_WEIGHT = Pattern.compile("\\{weight=(\\d+)\\}");
	public static final Pattern RE_ZERO_WITH_STAR = Pattern.compile("^\\*$");
}
