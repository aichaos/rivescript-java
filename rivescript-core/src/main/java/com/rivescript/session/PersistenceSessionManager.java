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

import static com.rivescript.session.ThawAction.DISCARD;
import static com.rivescript.session.ThawAction.KEEP;
import static com.rivescript.session.ThawAction.THAW;

import java.util.Collections;
import java.util.Map;

/**
 * Implements the default {@link SessionManager} for RiveScript
 *
 * @author Balachandar S
 */
public class PersistenceSessionManager implements SessionManager {

	private PersistenceHandler handler = null;

	public PersistenceSessionManager(PersistenceHandler handler) {
		this.handler = handler;
	}

	@Override
	public UserData init(String username) {
		if (handler.getUserData(username) == null) {
			handler.createUserData(username, defaultSession());
		}
		return handler.getUserData(username);
	}

	@Override
	public void set(String username, String name, String value) {
		UserData userData = init(username);
		userData.setVariable(name, value);
		handler.updateUserData(username, userData);
	}

	@Override
	public void set(String username, Map<String, String> vars) {
		UserData userData = init(username);
		for (Map.Entry<String, String> var : vars.entrySet()) {
			userData.setVariable(var.getKey(), var.getValue());
		}
		handler.updateUserData(username, userData);
	}

	@Override
	public void addHistory(String username, String input, String reply) {
		UserData userData = init(username);
		Collections.rotate(userData.getHistory().getInput(), 1); // Rotate
																	// right.
		userData.getHistory().getInput().set(0, input.trim()); // Now set the
																// first item
		Collections.rotate(userData.getHistory().getReply(), 1); // Rotate
																	// right.
		userData.getHistory().getReply().set(0, reply.trim()); // Now set the
																// first item
		handler.updateUserData(username, userData);
	}

	@Override
	public void setLastMatch(String username, String trigger) {
		UserData userData = init(username);
		userData.setLastMatch(trigger);
		handler.updateUserData(username, userData);
	}

	@Override
	public String get(String username, String name) {
		return handler.getUserData(username).getVariable(name);
	}

	@Override
	public UserData get(String username) {
		UserData userData = init(username);
		if (userData == null) {
			return null;
		}
		return userData;
	}

	@Override
	public Map<String, UserData> getAll() {
		return handler.getAllUserData();
	}

	@Override
	public String getLastMatch(String username) {
		UserData userData = init(username);
		if (userData == null) {
			return null;
		}
		return userData.getLastMatch();
	}

	@Override
	public History getHistory(String username) {
		UserData userData = init(username);
		if (userData == null) {
			return null;
		}
		return userData.getHistory();
	}

	@Override
	public void clear(String username) {
		handler.deleteUserData(username);
	}

	@Override
	public void clearAll() {
		handler.deleteAllUserData();
	}

	@Override
	public void freeze(String username) {
		handler.moveUserDataToSecondaryStorage(username);
	}

	@Override
	public void thaw(String username, ThawAction action) {
		if (action == THAW) {
			handler.copyUserDataToPrimaryStorage(username);
		} else if (action == DISCARD) {
			handler.removeUserDataFromSecondaryStorage(username);
		} else if (action == KEEP) {
			handler.moveUserDataToPrimaryStorage(username);
		}
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
