/*
    com.rivescript.RiveScript - The Official Java RiveScript Interpreter
    Copyright (C) 2010  Noah Petherbridge

    This program is free software; you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation; either version 2 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License along
    with this program; if not, write to the Free Software Foundation, Inc.,
    51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
*/

package com.rivescript;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Vector;

/**
 * An inheritance tracker to aid in sorting replies.
 */

public class Inheritance {
	// Private variables.
	private HashMap<Integer, Vector<String> > atomic =
		new HashMap<Integer, Vector<String> >(); // Whole words, no wildcards
	private HashMap<Integer, Vector<String> > option =
		new HashMap<Integer, Vector<String> >(); // With [optional] parts
	private HashMap<Integer, Vector<String> > alpha  =
		new HashMap<Integer, Vector<String> >(); // With _alpha_ wildcards
	private HashMap<Integer, Vector<String> > number =
		new HashMap<Integer, Vector<String> >(); // With #number# wildcards
	private HashMap<Integer, Vector<String> > wild   =
		new HashMap<Integer, Vector<String> >(); // With *star* wildcards
	private Vector<String> pound = new Vector<String>();     // With only # in them
	private Vector<String> under = new Vector<String>();     // With only _ in them
	private Vector<String> star  = new Vector<String>();     // With only * in them

	// Constructor
	public Inheritance () {
		// Nothing to do.
	}

	// Dump the buckets out and add them to the given vector.
	public Vector<String> dump (Vector<String> sorted) {
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
	private Vector<String> addSortedList (Vector<String> vector, HashMap<Integer, Vector<String> > hash) {
		// We've been given a hash where the keys are integers (word counts) and
		// the values are all triggers with that number of words in them (where
		// words are things that aren't wildcards).

		// Sort the hash by its number of words, descending.
		int[] sorted = com.rivescript.Util.sortKeysDesc(hash);

		// Go through the results and add them to the vector.
		for (int i = 0; i < sorted.length; i++) {
			String[] items = com.rivescript.Util.v2s(hash.get(sorted[i]));
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
	private Vector<String> addSortedList (Vector<String> vector, Vector<String> wc) {
		for (Enumeration e = wc.elements(); e.hasMoreElements(); ) {
			vector.add( e.nextElement().toString() );
		}
		return vector;
	}

	// Accessors to the private variables.
	public void addAtomic (int wc, String trigger) {
		if (!atomic.containsKey(wc)) {
			atomic.put(wc, new Vector<String>());
		}
		atomic.get(wc).add(trigger);
	}
	public void addOption (int wc, String trigger) {
		if (!option.containsKey(wc)) {
			option.put(wc, new Vector<String>());
		}
		option.get(wc).add(trigger);
	}
	public void addAlpha (int wc, String trigger) {
		if (!alpha.containsKey(wc)) {
			alpha.put(wc, new Vector<String>());
		}
		alpha.get(wc).add(trigger);
	}
	public void addNumber (int wc, String trigger) {
		if (!number.containsKey(wc)) {
			number.put(wc, new Vector<String>());
		}
		number.get(wc).add(trigger);
	}
	public void addWild (int wc, String trigger) {
		if (!wild.containsKey(wc)) {
			wild.put(wc, new Vector<String>());
		}
		wild.get(wc).add(trigger);
	}
	public void addPound (String trigger) {
		pound.add(trigger);
	}
	public void addUnder (String trigger) {
		under.add(trigger);
	}
	public void addStar (String trigger) {
		star.add(trigger);
	}
}
