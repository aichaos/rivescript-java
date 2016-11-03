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

import java.util.HashMap;
import java.util.Vector;

/**
 * An inheritance tracker to aid in sorting replies.
 *
 * @author Noah Petherbridge
 */
public class Inheritance {

	// Private variables.
	private HashMap<Integer, Vector<String>> atomic = new HashMap<>(); // Whole words, no wildcards
	private HashMap<Integer, Vector<String>> option = new HashMap<>(); // With [optional] parts
	private HashMap<Integer, Vector<String>> alpha = new HashMap<>(); // With _alpha_ wildcards
	private HashMap<Integer, Vector<String>> number = new HashMap<>(); // With #number# wildcards
	private HashMap<Integer, Vector<String>> wild = new HashMap<>(); // With *star* wildcards
	private Vector<String> pound = new Vector<>(); // With only # in them
	private Vector<String> under = new Vector<>(); // With only _ in them
	private Vector<String> star = new Vector<>(); // With only * in them

	// Constructor
	public Inheritance() {
		// Nothing to do.
	}

	/**
	 * Dumps the buckets out and add them to the given vector.
	 */
	public Vector<String> dump(Vector<String> sorted) {
		// Sort each sort-category by the number of words they have, in descending order.
		sorted = addSortedList(sorted, atomic);
		sorted = addSortedList(sorted, option);
		sorted = addSortedList(sorted, alpha);
		sorted = addSortedList(sorted, number);
		sorted = addSortedList(sorted, wild);

		// Add the singleton wildcards too.
		sorted = addSortedList(sorted, under);
		sorted = addSortedList(sorted, pound);
		sorted = addSortedList(sorted, star);

		return sorted;
	}

	/**
	 * A helper function for sortReplies, adds a hash of (word count -> triggers vector) to the
	 * running sort buffer.
	 *
	 * @param vector The running sort buffer vector
	 * @param hash   The hash of word count -> triggers vector
	 */
	private Vector<String> addSortedList(Vector<String> vector, HashMap<Integer, Vector<String>> hash) {
		// We've been given a hash where the keys are integers (word counts) and
		// the values are all triggers with that number of words in them (where
		// words are things that aren't wildcards).

		// Sort the hash by its number of words, descending.
		int[] sorted = Util.sortKeysDesc(hash);

		// Go through the results and add them to the vector.
		for (int i = 0; i < sorted.length; i++) {
			String[] items = Util.Sv2s(hash.get(sorted[i]));
			for (int j = 0; j < items.length; j++) {
				vector.add(items[j]);
			}
		}

		// Return the new vector.
		return vector;
	}

	/**
	 * A helper function for sortReplies, adds a vector of wildcard triggers to the running sort buffer.
	 *
	 * @param vector The running sort buffer vector
	 * @param wc     The vector of wildcard triggers
	 */
	private Vector<String> addSortedList(Vector<String> vector, Vector<String> wc) {
		String[] items = Util.sortByLength(com.rivescript.Util.Sv2s(wc));
		for (int i = 0; i < items.length; i++) {
			vector.add(items[i]);
		}
		return vector;
	}

	// Accessors to the private variables.

	public void addAtomic(int wc, String trigger) {
		if (!atomic.containsKey(wc)) {
			atomic.put(wc, new Vector<String>());
		}
		atomic.get(wc).add(trigger);
	}

	public void addOption(int wc, String trigger) {
		if (!option.containsKey(wc)) {
			option.put(wc, new Vector<String>());
		}
		option.get(wc).add(trigger);
	}

	public void addAlpha(int wc, String trigger) {
		if (!alpha.containsKey(wc)) {
			alpha.put(wc, new Vector<String>());
		}
		alpha.get(wc).add(trigger);
	}

	public void addNumber(int wc, String trigger) {
		if (!number.containsKey(wc)) {
			number.put(wc, new Vector<String>());
		}
		number.get(wc).add(trigger);
	}

	public void addWild(int wc, String trigger) {
		if (!wild.containsKey(wc)) {
			wild.put(wc, new Vector<String>());
		}
		wild.get(wc).add(trigger);
	}

	public void addPound(String trigger) {
		pound.add(trigger);
	}

	public void addUnder(String trigger) {
		under.add(trigger);
	}

	public void addStar(String trigger) {
		star.add(trigger);
	}
}
