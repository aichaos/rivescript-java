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

import java.util.Enumeration;
import java.util.Vector;

/**
 * A trigger class for RiveScript.
 *
 * @author Noah Petherbridge
 */
public class Trigger {

	// A trigger is a parent of everything that comes after it (redirect, reply and conditions).

	private String pattern;
	private String inTopic;
	private Vector<String> redirect = new Vector<>();  // @Redirect
	private Vector<String> reply = new Vector<>();     // -Reply
	private Vector<String> condition = new Vector<>(); // *Condition
	private boolean previous = false;

	/**
	 * Creates a new trigger object.
	 *
	 * @param topic   The topic of the trigger.
	 * @param pattern The match pattern for the trigger.
	 */
	public Trigger(String topic, String pattern) {
		this.inTopic = topic; // And then it's read-only! Triggers can't be moved to other topics
		this.pattern = pattern;
	}

	/**
	 * Returns the topic the trigger belongs to.
	 */
	public String topic() {
		return this.inTopic;
	}

	/**
	 * Sets that this trigger is paired with a {@code %Previous} (and shouldn't be sorted
	 * with the other triggers for reply matching purposes).
	 *
	 * @param paired Whether the trigger has a {@code %Previous}.
	 */
	public void hasPrevious(boolean paired) {
		this.previous = paired;
	}

	/**
	 * Returns whether the trigger has a %Previous.
	 */
	public boolean hasPrevious() {
		return this.previous;
	}

	/**
	 * Adds a new reply to this trigger.
	 *
	 * @param reply The reply text.
	 */
	public void addReply(String reply) {
		this.reply.add(reply);
	}

	/**
	 * Returns the replies under this trigger.
	 */
	public String[] listReplies() {
		return Sv2s(reply);
	}

	/**
	 * Adds a new redirection to this trigger.
	 *
	 * @param meant What the user "meant" to say.
	 */
	public void addRedirect(String meant) {
		this.redirect.add(meant);
	}

	/**
	 * Returns the redirections under this trigger.
	 */
	public String[] listRedirects() {
		return Sv2s(redirect);
	}

	/**
	 * Adds a new condition to this trigger.
	 *
	 * @param condition The conditional line.
	 */
	public void addCondition(String condition) {
		this.condition.add(condition);
	}

	/**
	 * Returns the conditions under this trigger.
	 */
	public String[] listConditions() {
		return Sv2s(condition);
	}

	/*---------------------*/
	/*-- Utility Methods --*/
	/*---------------------*/

	/**
	 * Converts a {@link Vector} to a {@link String} array.
	 *
	 * @param vector The vector to convert.
	 */
	private String[] Sv2s(Vector vector) {
		String[] result = new String[vector.size()];
		int i = 0;
		for (Enumeration e = vector.elements(); e.hasMoreElements(); ) {
			result[i] = e.nextElement().toString();
			i++;
		}
		return result;
	}
}
