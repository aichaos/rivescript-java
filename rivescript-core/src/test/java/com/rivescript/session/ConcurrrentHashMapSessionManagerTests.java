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

package com.rivescript.session;

import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static com.rivescript.session.SessionManager.HISTORY_SIZE;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;

/**
 * Tests for {@link ConcurrentHashMapSessionManager}.
 *
 * @author Marcel Overdijk
 */
public class ConcurrrentHashMapSessionManagerTests {

	private ConcurrentHashMapSessionManager sessionManager;

	private String username = "local-user";

	@Before
	public void setUp() {
		this.sessionManager = new ConcurrentHashMapSessionManager();
	}

	@Test
	public void testConstructor() {
		assertThat(sessionManager.getAll().size(), is(equalTo(0)));
	}

	@Test
	public void testInit() {
		UserData expected = new UserData();
		expected.setVariable("topic", "random");
		expected.setLastMatch("");
		assertThat(sessionManager.get(username), is(equalTo(null)));
		sessionManager.init(username);
		assertThat(sessionManager.get(username), is(not(equalTo(null))));
		assertThat(sessionManager.get(username), is(equalTo(expected)));
	}

	@Test
	public void testSet() {
		sessionManager.set(username, "foo", "bar");
		assertThat(sessionManager.get(username, "foo"), is(equalTo("bar")));
	}

	@Test
	public void testSetMultipleUsers() {
		sessionManager.set("user1", "var1-user1", "value1-user1");
		sessionManager.set("user2", "var1-user2", "value1-user2");
		sessionManager.set("user2", "var2-user2", "value2-user2");
		UserData user1Data = sessionManager.get("user1");
		UserData user2Data = sessionManager.get("user2");
		assertThat(user1Data.getVariables().get("var1-user1"), is(equalTo("value1-user1")));
		assertThat(user2Data.getVariables().get("var1-user2"), is(equalTo("value1-user2")));
		assertThat(user2Data.getVariables().get("var2-user2"), is(equalTo("value2-user2")));
	}

	@Test
	public void testSetMap() {
		Map<String, String> vars = new HashMap<>();
		vars.put("var1", "value1");
		vars.put("var2", "value2");
		sessionManager.set(username, vars);
		UserData userData = sessionManager.get(username);
		assertThat(userData.getVariables().get("var1"), is(equalTo("value1")));
		assertThat(userData.getVariables().get("var2"), is(equalTo("value2")));
	}

	@Test
	public void testAddHistory() {
		sessionManager.addHistory(username, "input1", "reply1");
		History history = sessionManager.getHistory(username);
		assertThat(history.getInput(0), is(equalTo("input1")));
		assertThat(history.getReply(0), is(equalTo("reply1")));
		for (int i = 1; i < HISTORY_SIZE; i++) {
			assertThat(history.getInput(i), is(equalTo("undefined")));
			assertThat(history.getReply(i), is(equalTo("undefined")));
		}

		// Add another one.
		sessionManager.addHistory(username, "input2", "reply2");
		history = sessionManager.getHistory(username);
		assertThat(history.getInput(0), is(equalTo("input2")));
		assertThat(history.getReply(0), is(equalTo("reply2")));
		assertThat(history.getInput(1), is(equalTo("input1")));
		assertThat(history.getReply(1), is(equalTo("reply1")));
		for (int i = 2; i < HISTORY_SIZE; i++) {
			assertThat(history.getInput(i), is(equalTo("undefined")));
			assertThat(history.getReply(i), is(equalTo("undefined")));
		}
	}

	@Test
	public void testSetLastMatch() {
		sessionManager.setLastMatch(username, "foobar");
		assertThat(sessionManager.getLastMatch(username), is(equalTo("foobar")));
	}

	@Test
	public void testClear() {
		sessionManager.init("user1");
		sessionManager.init("user2");
		assertThat(sessionManager.get("user1"), is(not(equalTo(null))));
		assertThat(sessionManager.get("user2"), is(not(equalTo(null))));
		sessionManager.clear("user1");
		assertThat(sessionManager.get("user1"), is(equalTo(null)));
		assertThat(sessionManager.get("user2"), is(not(equalTo(null))));
	}

	@Test
	public void testClearAll() {
		sessionManager.init("user1");
		sessionManager.init("user2");
		assertThat(sessionManager.get("user1"), is(not(equalTo(null))));
		assertThat(sessionManager.get("user2"), is(not(equalTo(null))));
		sessionManager.clearAll();
		assertThat(sessionManager.getAll().size(), is(equalTo(0)));
		assertThat(sessionManager.get("user1"), is(equalTo(null)));
		assertThat(sessionManager.get("user2"), is(equalTo(null)));
	}
}
