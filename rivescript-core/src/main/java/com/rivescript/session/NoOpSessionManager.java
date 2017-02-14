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

/**
 * A no operation {@link SessionManager} suitable for disabling session storage.
 *
 * @author Noah Petherbridge
 * @author Marcel Overdijk
 */
public class NoOpSessionManager implements SessionManager {

	@Override
	public UserData init(String username) {
		return noOpSession();
	}

	@Override
	public void set(String username, String name, String value) {
	}

	@Override
	public void set(String username, Map<String, String> vars) {
	}

	@Override
	public void addHistory(String username, String input, String reply) {
	}

	@Override
	public void setLastMatch(String username, String trigger) {
	}

	@Override
	public String get(String username, String name) {
		return null;
	}

	@Override
	public UserData get(String username) {
		return null;
	}

	@Override
	public Map<String, UserData> getAll() {
		return Collections.emptyMap();
	}

	@Override
	public String getLastMatch(String username) {
		return null;
	}

	@Override
	public History getHistory(String username) {
		return new History();
	}

	@Override
	public void clear(String username) {
	}

	@Override
	public void clearAll() {
	}

	@Override
	public void freeze(String username) {
	}

	@Override
	public void thaw(String username, ThawAction action) {
	}

	private UserData noOpSession() {
		UserData userData = new UserData();
		userData.setLastMatch("");
		return userData;
	}
}
