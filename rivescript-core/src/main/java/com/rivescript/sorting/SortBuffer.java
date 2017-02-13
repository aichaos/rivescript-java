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

package com.rivescript.sorting;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Sort buffer data.
 *
 * @author Noah Petherbridge
 * @author Marcel Overdijk
 */
public class SortBuffer {

	private Map<String, List<SortedTriggerEntry>> topics;
	private Map<String, List<SortedTriggerEntry>> thats;
	private List<String> sub;
	private List<String> person;

	public SortBuffer() {
		this.topics = new HashMap<>();
		this.thats = new HashMap<>();
		this.sub = new ArrayList<>();
		this.person = new ArrayList<>();
	}

	public List<SortedTriggerEntry> getTopic(String name) {
		return topics.get(name);
	}

	public Map<String, List<SortedTriggerEntry>> getTopics() {
		return topics;
	}

	public void addTopic(String name, List<SortedTriggerEntry> triggers) {
		topics.put(name, triggers);
	}

	public List<SortedTriggerEntry> getThats(String name) {
		return thats.get(name);
	}

	public Map<String, List<SortedTriggerEntry>> getThats() {
		return thats;
	}

	public void addThats(String name, List<SortedTriggerEntry> triggers) {
		thats.put(name, triggers);
	}

	public List<String> getSub() {
		return sub;
	}

	public void setSub(List<String> sub) {
		this.sub = sub;
	}

	public List<String> getPerson() {
		return person;
	}

	public void setPerson(List<String> person) {
		this.person = person;
	}
}
