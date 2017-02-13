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

package com.rivescript.session;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Container for user variables.
 *
 * @author Noah Petherbridge
 * @author Marcel Overdijk
 */
public class UserData implements Serializable {

	private static final long serialVersionUID = -4112596798916704937L;

	private Map<String, String> variables;
	private String lastMatch;
	private History history;

	public UserData() {
		this.variables = new HashMap<>();
		this.history = new History();
	}

	public String getVariable(String name) {
		return variables.get(name);
	}

	public Map<String, String> getVariables() {
		return variables;
	}

	public void setVariable(String name, String value) {
		variables.put(name, value);
	}

	public String getLastMatch() {
		return lastMatch;
	}

	public void setLastMatch(String lastMatch) {
		this.lastMatch = lastMatch;
	}

	public History getHistory() {
		return history;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		UserData that = (UserData) o;
		if (variables != null ? !variables.equals(that.variables) : that.variables != null) {
			return false;
		}
		if (lastMatch != null ? !lastMatch.equals(that.lastMatch) : that.lastMatch != null) {
			return false;
		}
		return history != null ? history.equals(that.history) : that.history == null;
	}

	@Override
	public int hashCode() {
		int result = variables != null ? variables.hashCode() : 0;
		result = 31 * result + (lastMatch != null ? lastMatch.hashCode() : 0);
		result = 31 * result + (history != null ? history.hashCode() : 0);
		return result;
	}

	@Override
	public String toString() {
		return "UserData{" +
				"variables=" + variables +
				", lastMatch='" + lastMatch + '\'' +
				", history=" + history +
				'}';
	}
}
