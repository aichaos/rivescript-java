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
import java.util.List;

/**
 * Represents a RiveScript ZzTrigger.
 *
 * @author Noah Petherbridge
 * @author Marcel Overdijk
 */
public class Trigger {

	private String trigger;
	private List<String> reply;
	private List<String> condition;
	private String redirect;
	private String previous;

	public Trigger() {
		this.reply = new ArrayList<>();
		this.condition = new ArrayList<>();
	}

	/**
	 * Returns the trigger pattern of this trigger.
	 *
	 * @return the trigger pattern
	 */
	public String getTrigger() {
		return trigger;
	}

	/**
	 * Sets the trigger pattern of this trigger.
	 *
	 * @param trigger the trigger pattern
	 */
	public void setTrigger(String trigger) {
		this.trigger = trigger;
	}

	/**
	 * Returns the replies of this trigger.
	 *
	 * @return the replies
	 */
	public List<String> getReply() {
		return reply;
	}

	/**
	 * Sets the replies of this trigger.
	 *
	 * @param reply the replies
	 */
	public void setReply(List<String> reply) {
		this.reply = reply;
	}

	/**
	 * Returns the conditions of this trigger.
	 *
	 * @return the conditions
	 */
	public List<String> getCondition() {
		return condition;
	}

	/**
	 * Sets the conditions of this trigger.
	 *
	 * @param condition the conditions
	 */
	public void setCondition(List<String> condition) {
		this.condition = condition;
	}

	/**
	 * Returns the redirect of this trigger.
	 *
	 * @return the redirect
	 */
	public String getRedirect() {
		return redirect;
	}

	/**
	 * Sets the redirect of this trigger.
	 *
	 * @param redirect the redirect
	 */
	public void setRedirect(String redirect) {
		this.redirect = redirect;
	}

	/**
	 * Returns the previous of this trigger.
	 *
	 * @return the previous
	 */
	public String getPrevious() {
		return previous;
	}

	/**
	 * Sets the previous of this trigger.
	 *
	 * @param previous the previous
	 */
	public void setPrevious(String previous) {
		this.previous = previous;
	}

	/**
	 * Adds the given reply to this trigger.
	 *
	 * @param reply the reply
	 */
	public void addReply(String reply) {
		this.reply.add(reply);
	}

	/**
	 * Adds the given condition to this trigger.
	 *
	 * @param condition the condition
	 */
	public void addCondition(String condition) {
		this.condition.add(condition);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		Trigger that = (Trigger) o;
		if (trigger != null ? !trigger.equals(that.trigger) : that.trigger != null) {
			return false;
		}
		if (reply != null ? !reply.equals(that.reply) : that.reply != null) {
			return false;
		}
		if (condition != null ? !condition.equals(that.condition) : that.condition != null) {
			return false;
		}
		if (redirect != null ? !redirect.equals(that.redirect) : that.redirect != null) {
			return false;
		}
		return previous != null ? previous.equals(that.previous) : that.previous == null;
	}

	@Override
	public int hashCode() {
		int result = trigger != null ? trigger.hashCode() : 0;
		result = 31 * result + (reply != null ? reply.hashCode() : 0);
		result = 31 * result + (condition != null ? condition.hashCode() : 0);
		result = 31 * result + (redirect != null ? redirect.hashCode() : 0);
		result = 31 * result + (previous != null ? previous.hashCode() : 0);
		return result;
	}

	@Override
	public String toString() {
		return "Trigger{" +
				"trigger='" + trigger + '\'' +
				", reply=" + reply +
				", condition=" + condition +
				", redirect='" + redirect + '\'' +
				", previous='" + previous + '\'' +
				'}';
	}
}
