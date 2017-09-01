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

package com.rivescript;

/**
 * Specifies the concat modes.
 *
 * @author Marcel Overdijk
 */
public enum ConcatMode {

	NONE(""), NEWLINE("\n"), SPACE(" ");

	private String concatChar;

	ConcatMode(String concatChar) {
		this.concatChar = concatChar;
	}

	/**
	 * Resolves the {@link ConcatMode} by the given name (ignoring case).
	 *
	 * @param name the name
	 * @return the concat mode or {@code null} if not found
	 */
	public static ConcatMode fromName(String name) {
		for (ConcatMode concatMode : values()) {
			if (concatMode.name().equalsIgnoreCase(name)) {
				return concatMode;
			}
		}
		return null;
	}

	/**
	 * Returns the concat character.
	 *
	 * @return the concat character
	 */
	public String getConcatChar() {
		return concatChar;
	}
}
