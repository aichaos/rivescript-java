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
 * Represents the root of the Abstract Syntax Tree (AST).
 *
 * @author Noah Petherbridge
 * @author Marcel Overdijk
 */
public class Root {

	private Begin begin;
	private Map<String, Topic> topics;
	private List<ObjectMacro> objects;

	/**
	 * Creates a new Abstract Syntax Tree (AST) containing an empty "random" topic.
	 */
	public Root() {
		this.begin = new Begin();
		this.topics = new HashMap<>();
		this.objects = new ArrayList<>();
		// Initialize the 'random' topic.
		addTopic("random");
	}

	/**
	 * Returns the "begin block" (configuration) data.
	 *
	 * @return the "begin block" (configuration) data
	 */
	public Begin getBegin() {
		return begin;
	}

	/**
	 * Sets the "begin block" (configuration) data.
	 *
	 * @param begin the "begin block" (configuration) data
	 */
	public void setBegin(Begin begin) {
		this.begin = begin;
	}

	/**
	 * Returns the topic with the given name.
	 *
	 * @param name the name of the topic
	 * @return the topic or {@code null} if not found
	 */
	public Topic getTopic(String name) {
		return topics.get(name);
	}

	/**
	 * Returns the topics.
	 *
	 * @return the topics
	 */
	public Map<String, Topic> getTopics() {
		return topics;
	}

	/**
	 * Sets the topics.
	 *
	 * @param topics the topics
	 */
	public void setTopics(Map<String, Topic> topics) {
		this.topics = topics;
	}

	/**
	 * Returns the objects.
	 *
	 * @return the objects
	 */
	public List<ObjectMacro> getObjects() {
		return objects;
	}

	/**
	 * Sets the objects.
	 *
	 * @param objects the objects
	 */
	public void setObjects(List<ObjectMacro> objects) {
		this.objects = objects;
	}

	/**
	 * Adds a new topic with the given name.
	 *
	 * @param name the name of the topic
	 */
	public void addTopic(String name) {
		topics.put(name, new Topic());
	}

	/**
	 * Adds the given object.
	 *
	 * @param object the object
	 */
	public void addObject(ObjectMacro object) {
		objects.add(object);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		Root that = (Root) o;
		if (begin != null ? !begin.equals(that.begin) : that.begin != null) {
			return false;
		}
		if (topics != null ? !topics.equals(that.topics) : that.topics != null) {
			return false;
		}
		return objects != null ? objects.equals(that.objects) : that.objects == null;
	}

	@Override
	public int hashCode() {
		int result = begin != null ? begin.hashCode() : 0;
		result = 31 * result + (topics != null ? topics.hashCode() : 0);
		result = 31 * result + (objects != null ? objects.hashCode() : 0);
		return result;
	}

	@Override
	public String toString() {
		return "Root{" +
				"begin=" + begin +
				", topics=" + topics +
				", objects=" + objects +
				'}';
	}
}
