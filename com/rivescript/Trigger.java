/*
	com.rivescript.RiveScript - The Official Java RiveScript Interpreter

	Copyright (c) 2015 Noah Petherbridge

	Permission is hereby granted, free of charge, to any person obtaining a copy
	of this software and associated documentation files (the "Software"), to deal
	in the Software without restriction, including without limitation the rights
	to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
	copies of the Software, and to permit persons to whom the Software is
	furnished to do so, subject to the following conditions:

	The above copyright notice and this permission notice shall be included in all
	copies or substantial portions of the Software.

	THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
	IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
	FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
	AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
	LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
	OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
	SOFTWARE.
*/

package com.rivescript;

import java.lang.String;
import java.util.Enumeration;
import java.util.Vector;

/**
 * A trigger class for RiveScript.
 */

public class Trigger {
	// A trigger is a parent of everything that comes after it (redirect,
	// reply and conditions).
	private String pattern           = "";
	private String inTopic           = "";
	private Vector<String> redirect  = new Vector<String>(); // @Redirect
	private Vector<String> reply     = new Vector<String>(); // -Reply
	private Vector<String> condition = new Vector<String>(); // *Condition
	private boolean previous         = false;

	/**
	 * Create a new trigger object.
	 *
	 * @param pattern The match pattern for the trigger.
	 */
	public Trigger (String topic, String pattern) {
		this.inTopic = topic; // And then it's read-only! Triggers can't be moved to other topics
		this.pattern = pattern;
	}

	/**
	 * If you have the trigger object, this will tell you what topic it belongs to.
	 */
	public String topic () {
		return this.inTopic;
	}

	/**
	 * Flag that this trigger is paired with a %Previous (and shouldn't be sorted
	 * with the other triggers for reply matching purposes).
	 *
	 * @param paired Whether the trigger has a %Previous.
	 */
	public void hasPrevious (boolean paired) {
		this.previous = true;
	}

	/**
	 * Test whether the trigger has a %Previous.
	 */
	public boolean hasPrevious () {
		return this.previous;
	}

	/**
	 * Add a new reply to a trigger.
	 *
	 * @param reply The reply text.
	 */
	public void addReply (String reply) {
		this.reply.add(reply);
	}

	/**
	 * List replies under this trigger.
	 */
	public String[] listReplies () {
		return Sv2s (reply);
	}

	/**
	 * Add a new redirection to a trigger.
	 *
	 * @param meant What the user "meant" to say.
	 */
	public void addRedirect (String meant) {
		this.redirect.add(meant);
	}

	/**
	 * List redirections under this trigger.
	 */
	public String[] listRedirects () {
		return Sv2s (redirect);
	}

	/**
	 * Add a new condition to a trigger.
	 *
	 * @param condition The conditional line.
	 */
	public void addCondition (String condition) {
		this.condition.add(condition);
	}

	/**
	 * List conditions under this trigger.
	 */
	public String[] listConditions () {
		return Sv2s (condition);
	}

	/*---------------------*/
	/*-- Utility Methods --*/
	/*---------------------*/

	/**
	 * Convert a vector to a string array.
	 *
	 * @param vector The vector to convert.
	 */
	private String[] Sv2s (Vector vector) {
		String[] result = new String [ vector.size() ];
		int i = 0;
		for (Enumeration e = vector.elements(); e.hasMoreElements(); ) {
			result[i] = e.nextElement().toString();
			i++;
		}
		return result;
	}
}
