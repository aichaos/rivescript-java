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

package com.rivescript.ast;

import java.util.List;

/**
 * Represents a RiveScript Object Macro.
 *
 * @author Noah Petherbridge
 * @author Marcel Overdijk
 */
public class ObjectMacro {

	private String name;
	private String language;
	private List<String> code;

	/**
	 * Returns the name of this object.
	 *
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name of this object.
	 *
	 * @param name the name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Returns the programming language of this object.
	 *
	 * @return the programming language
	 */
	public String getLanguage() {
		return language;
	}

	/**
	 * Sets the programming language of this object.
	 *
	 * @param language the programming language
	 */
	public void setLanguage(String language) {
		this.language = language;
	}

	/**
	 * Returns the source code of this object.
	 *
	 * @return the source code
	 */
	public List<String> getCode() {
		return code;
	}

	/**
	 * Sets the source code of this object.
	 *
	 * @param code the source code
	 */
	public void setCode(List<String> code) {
		this.code = code;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		ObjectMacro that = (ObjectMacro) o;
		if (name != null ? !name.equals(that.name) : that.name != null) {
			return false;
		}
		if (language != null ? !language.equals(that.language) : that.language != null) {
			return false;
		}
		return code != null ? code.equals(that.code) : that.code == null;
	}

	@Override
	public int hashCode() {
		int result = name != null ? name.hashCode() : 0;
		result = 31 * result + (language != null ? language.hashCode() : 0);
		result = 31 * result + (code != null ? code.hashCode() : 0);
		return result;
	}

	@Override
	public String toString() {
		return "ObjectMacro{" +
				"name='" + name + '\'' +
				", language='" + language + '\'' +
				", code=" + code +
				'}';
	}
}
