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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Represents the "begin block" (configuration) data.
 *
 * @author Noah Petherbridge
 * @author Marcel Overdijk
 */
public class Begin {

	private Map<String, String> global;
	private Map<String, String> var;
	private Map<String, String> sub;
	private Map<String, String> person;
	private Map<String, List<String>> array = new HashMap<>();

	public Begin() {
		this.global = new HashMap<>();
		this.var = new HashMap<>();
		this.sub = new HashMap<>();
		this.person = new HashMap<>();
		this.array = new HashMap<>();
	}

	/**
	 * Returns the global variables.
	 *
	 * @return the global variables
	 */
	public Map<String, String> getGlobal() {
		return global;
	}

	/**
	 * Sets the global variables.
	 *
	 * @param global the global variables
	 */
	public void setGlobal(Map<String, String> global) {
		this.global = global;
	}

	/**
	 * Returns the bot variables.
	 *
	 * @return the bot variables
	 */
	public Map<String, String> getVar() {
		return var;
	}

	/**
	 * Sets the bot variables.
	 *
	 * @param var the bot variables
	 */
	public void setVar(Map<String, String> var) {
		this.var = var;
	}

	/**
	 * Returns the substitution variables.
	 *
	 * @return the substitution variables
	 */
	public Map<String, String> getSub() {
		return sub;
	}

	/**
	 * Sets the substitution variables.
	 *
	 * @param sub the substitution variables
	 */
	public void setSub(Map<String, String> sub) {
		this.sub = sub;
	}

	/**
	 * Retuns the person variables.
	 *
	 * @return the person variables
	 */
	public Map<String, String> getPerson() {
		return person;
	}

	/**
	 * Sets the person variables.
	 *
	 * @param person the person variables
	 */
	public void setPerson(Map<String, String> person) {
		this.person = person;
	}

	/**
	 * Returns the arrays.
	 *
	 * @return the arrays
	 */
	public Map<String, List<String>> getArray() {
		return array;
	}

	/**
	 * Sets the arrays.
	 *
	 * @param array the arrays
	 */
	public void setArray(Map<String, List<String>> array) {
		this.array = array;
	}

	/**
	 * Adds a global variable.
	 *
	 * @param name  the name of the variable
	 * @param value the value
	 */
	public void addGlobal(String name, String value) {
		global.put(name, value);
	}

	/**
	 * Adds a bot variable.
	 *
	 * @param name  the name of the variable
	 * @param value the value
	 */
	public void addVar(String name, String value) {
		var.put(name, value);
	}

	/**
	 * Adds a substitution variable.
	 *
	 * @param name  the name of the variable
	 * @param value the value
	 */
	public void addSub(String name, String value) {
		sub.put(name, value);
	}

	/**
	 * Adds a person variable
	 *
	 * @param name  the name of the variable
	 * @param value the value
	 */
	public void addPerson(String name, String value) {
		person.put(name, value);
	}

	/**
	 * Adds an array.
	 *
	 * @param name  the name of the array
	 * @param value the array
	 */
	public void addArray(String name, List<String> value) {
		array.put(name, value);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		Begin that = (Begin) o;
		if (global != null ? !global.equals(that.global) : that.global != null) {
			return false;
		}
		if (var != null ? !var.equals(that.var) : that.var != null) {
			return false;
		}
		if (sub != null ? !sub.equals(that.sub) : that.sub != null) {
			return false;
		}
		if (person != null ? !person.equals(that.person) : that.person != null) {
			return false;
		}
		return array != null ? array.equals(that.array) : that.array == null;
	}

	@Override
	public int hashCode() {
		int result = global != null ? global.hashCode() : 0;
		result = 31 * result + (var != null ? var.hashCode() : 0);
		result = 31 * result + (sub != null ? sub.hashCode() : 0);
		result = 31 * result + (person != null ? person.hashCode() : 0);
		result = 31 * result + (array != null ? array.hashCode() : 0);
		return result;
	}

	@Override
	public String toString() {
		return "Begin{" +
				"global=" + global +
				", var=" + var +
				", sub=" + sub +
				", person=" + person +
				", array=" + array +
				'}';
	}
}
