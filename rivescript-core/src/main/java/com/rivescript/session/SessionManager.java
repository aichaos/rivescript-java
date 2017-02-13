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

package com.rivescript.session;

import com.rivescript.RiveScript;

import java.util.Map;

/**
 * Interface for RiveScript user user variables.
 * <p>
 * The session manager keeps track of getting and setting user variables, for example when the {@code <set>} or {@code <get>`} tags are used
 * in RiveScript or when API functions like {@link RiveScript#setUservar(String, String, String)} are called.
 * <p>
 * By default RiveScript stores user sessions in memory and provides methods to export and import them (e.g. to persist them when the bot
 * shuts down so they can be reloaded). If you'd prefer a more 'active' session storage, for example one that puts user variables into a
 * database or cache, you can create your own session manager that implements this interface.
 *
 * @author Noah Petherbridge
 * @author Marcel Overdijk
 */
public interface SessionManager {

	/**
	 * The number of entries stored in the history.
	 */
	int HISTORY_SIZE = 9;

	/**
	 * Makes sure a username has a session (creates one if not).
	 *
	 * @param username the username
	 * @return the user data
	 */
	UserData init(String username);

	/**
	 * Sets a user's variable.
	 *
	 * @param username the username
	 * @param name     the variable name
	 * @param value    the variable value
	 */
	void set(String username, String name, String value);

	/**
	 * Sets a user's variables.
	 *
	 * @param username the username
	 * @param vars     the user variables
	 */
	void set(String username, Map<String, String> vars);

	/**
	 * Adds input and reply to a user's history.
	 *
	 * @param username the username
	 * @param input    the input
	 * @param reply    the reply
	 */
	void addHistory(String username, String input, String reply);

	/**
	 * Sets a user's the last matched trigger.
	 *
	 * @param username the username
	 * @param trigger  the trigger
	 */
	void setLastMatch(String username, String trigger);

	/**
	 * Returns a user variable.
	 *
	 * @param username the username
	 * @param name     the variable name
	 * @return the variable value
	 */
	String get(String username, String name);

	/**
	 * Returns all variables for a user.
	 *
	 * @param username the username
	 * @return the user data
	 */
	UserData get(String username);

	/**
	 * Returns all variables about all users.
	 *
	 * @return the users and their user data
	 */
	Map<String, UserData> getAll();

	/**
	 * Returns a user's last matched trigger.
	 *
	 * @param username the username
	 * @return the last matched trigger
	 */
	String getLastMatch(String username);

	/**
	 * Returns a user's history.
	 *
	 * @param username the username
	 * @return the history
	 */
	History getHistory(String username);

	/**
	 * Clears a user's variables.
	 *
	 * @param username the username
	 */
	void clear(String username);

	/**
	 * Clear all variables of all users.
	 */
	void clearAll();

	/**
	 * Makes a snapshot of a user's variables.
	 *
	 * @param username the username
	 */
	void freeze(String username);

	/**
	 * Unfreezes a user's variables.
	 *
	 * @param username the username
	 * @param action   the thaw action
	 * @see ThawAction
	 */
	void thaw(String username, ThawAction action);
}
