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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Represents a RiveScript zzTopic.
 *
 * @author Noah Petherbridge
 * @author Marcel Overdijk
 */
public class Topic {

	private List<Trigger> triggers;
	private Map<String, Boolean> includes;
	private Map<String, Boolean> inherits;

	public Topic() {
		this.triggers = new ArrayList<>();
		this.includes = new HashMap<>();
		this.inherits = new HashMap<>();
	}

	/**
	 * Returns the triggers of this topic.
	 *
	 * @return the triggers
	 */
	public List<Trigger> getTriggers() {
		return triggers;
	}

	/**
	 * Sets the triggers of this topic.
	 *
	 * @param triggers the triggers
	 */
	public void setTriggers(List<Trigger> triggers) {
		this.triggers = triggers;
	}

	/**
	 * Returns the includes of this topic.
	 *
	 * @return the includes
	 */
	public Map<String, Boolean> getIncludes() {
		return includes;
	}

	/**
	 * Sets the includes of this topic.
	 *
	 * @param includes the includes
	 */
	public void setIncludes(Map<String, Boolean> includes) {
		this.includes = includes;
	}

	/**
	 * Returns the inherits of this topic.
	 *
	 * @return the inherits
	 */
	public Map<String, Boolean> getInherits() {
		return inherits;
	}

	/**
	 * Sets the inherits of this topic.
	 *
	 * @param inherits the inherits
	 */
	public void setInherits(Map<String, Boolean> inherits) {
		this.inherits = inherits;
	}

	/**
	 * Adds the given trigger to this topic.
	 *
	 * @param trigger the trigger
	 */
	public void addTrigger(Trigger trigger) {
		triggers.add(trigger);
	}

	/**
	 * Adds the given include to this topic.
	 *
	 * @param name  the include
	 * @param value the value
	 */
	public void addInclude(String name, boolean value) {
		includes.put(name, value);
	}

	/**
	 * Adds the given inherit to this topic.
	 *
	 * @param name  the inherit
	 * @param value the value
	 */
	public void addInherit(String name, boolean value) {
		inherits.put(name, value);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		Topic that = (Topic) o;
		if (triggers != null ? !triggers.equals(that.triggers) : that.triggers != null) {
			return false;
		}
		if (includes != null ? !includes.equals(that.includes) : that.includes != null) {
			return false;
		}
		return inherits != null ? inherits.equals(that.inherits) : that.inherits == null;
	}

	@Override
	public int hashCode() {
		int result = triggers != null ? triggers.hashCode() : 0;
		result = 31 * result + (includes != null ? includes.hashCode() : 0);
		result = 31 * result + (inherits != null ? inherits.hashCode() : 0);
		return result;
	}

	@Override
	public String toString() {
		return "Topic{" +
				"triggers=" + triggers +
				", includes=" + includes +
				", inherits=" + inherits +
				'}';
	}
}
