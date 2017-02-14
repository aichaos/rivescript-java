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


import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static com.rivescript.session.ThawAction.DISCARD;
import static com.rivescript.session.ThawAction.KEEP;
import static com.rivescript.session.ThawAction.THAW;

/**
 * Implements the default in-memory {@link SessionManager} for RiveScript, based on a {@link ConcurrentHashMap}.
 *
 * @author Noah Petherbridge
 * @author Marcel Overdijk
 */
public class ConcurrentHashMapSessionManager implements SessionManager {

	private ConcurrentHashMap<String, UserData> users;
	private ConcurrentHashMap<String, UserData> frozen;

	public ConcurrentHashMapSessionManager() {
		this.users = new ConcurrentHashMap<>();
		this.frozen = new ConcurrentHashMap<>();
	}

	@Override
	public UserData init(String username) {
		if (!users.containsKey(username)) {
			users.put(username, defaultSession());
		}
		return users.get(username);
	}

	@Override
	public void set(String username, String name, String value) {
		UserData userData = init(username);
		userData.setVariable(name, value);
	}

	@Override
	public void set(String username, Map<String, String> vars) {
		UserData userData = init(username);
		for (Map.Entry<String, String> var : vars.entrySet()) {
			userData.setVariable(var.getKey(), var.getValue());
		}
	}

	@Override
	public void addHistory(String username, String input, String reply) {
		UserData userData = init(username);
		Collections.rotate(userData.getHistory().getInput(), 1); // Rotate right.
		userData.getHistory().getInput().set(0, input.trim());   // Now set the first item
		Collections.rotate(userData.getHistory().getReply(), 1); // Rotate right.
		userData.getHistory().getReply().set(0, reply.trim());   // Now set the first item
	}

	@Override
	public void setLastMatch(String username, String trigger) {
		UserData userData = init(username);
		userData.setLastMatch(trigger);
	}

	@Override
	public String get(String username, String name) {
		if (!users.containsKey(username)) {
			return null;
		}
		return users.get(username).getVariable(name);
	}

	@Override
	public UserData get(String username) {
		if (!users.containsKey(username)) {
			return null;
		}
		return users.get(username);
	}

	@Override
	public Map<String, UserData> getAll() {
		return users;
	}

	@Override
	public String getLastMatch(String username) {
		if (!users.containsKey(username)) {
			return null;
		}
		return users.get(username).getLastMatch();
	}

	@Override
	public History getHistory(String username) {
		if (!users.containsKey(username)) {
			return null;
		}
		return users.get(username).getHistory();
	}

	@Override
	public void clear(String username) {
		users.remove(username);
		frozen.remove(username);
	}

	@Override
	public void clearAll() {
		users.clear();
		frozen.clear();
	}

	@Override
	public void freeze(String username) {
		if (users.containsKey(username)) {
			UserData data = users.get(username);
			frozen.put(username, cloneUser(data));
		}
	}

	@Override
	public void thaw(String username, ThawAction action) {
		if (frozen.containsKey(username)) {
			UserData frozen = this.frozen.get(username);
			if (action == THAW) {
				users.put(username, cloneUser(frozen));
				this.frozen.remove(username);
			} else if (action == DISCARD) {
				this.frozen.remove(username);
			} else if (action == KEEP) {
				users.put(username, cloneUser(frozen));
			}
		}
	}

	/**
	 * Makes a safe clone of {@link UserData}.
	 *
	 * @param data the data to clone
	 * @return the cloned data
	 */
	private UserData cloneUser(UserData data) {
		UserData clone = defaultSession();

		// Copy user variables.
		for (Map.Entry<String, String> entry : data.getVariables().entrySet()) {
			clone.setVariable(entry.getKey(), entry.getValue());
		}

		// Copy history.
		for (int i = 0; i < HISTORY_SIZE; i++) {
			clone.getHistory().getInput().set(i, data.getHistory().getInput(i));
			clone.getHistory().getReply().set(i, data.getHistory().getReply(i));
		}

		return clone;
	}

	/**
	 * Initializes the default session variables for a user.
	 * <p>
	 * This mostly just means the topic is set to "random".
	 *
	 * @return the user data
	 */
	private UserData defaultSession() {
		UserData userData = new UserData();
		userData.setVariable("topic", "random");
		userData.setLastMatch("");
		return userData;
	}
}
