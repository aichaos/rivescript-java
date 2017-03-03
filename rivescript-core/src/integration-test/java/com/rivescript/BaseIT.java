package com.rivescript;/*
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

import java.io.File;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.isIn;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

/**
 * Base class for integration tests.
 *
 * @author Noah Petherbridge
 * @author Marcel Overdijk
 */
public class BaseIT {

	protected RiveScript rs;
	protected String username = "local-user";

	protected File getClassPathFile(String name) {
		return new File(getClass().getClassLoader().getResource(name).getFile());
	}

	/**
	 * Sets up the RiveScript source code and sorts the replies.
	 *
	 * @param code the lines of RiveScript source code
	 */
	protected void setUp(String[] code) {
		rs.stream(code);
		rs.sortReplies();
	}

	/**
	 * Asserts that a global variable has the expected value.
	 *
	 * @param name     the variable name
	 * @param expected the expected value
	 */
	protected void assertGlobal(String name, String expected) {
		assertThat(rs.getGlobal(name), is(equalTo(expected)));
	}

	/**
	 * Asserts that a bot variable has the expected value.
	 *
	 * @param name     the variable name
	 * @param expected the expected value
	 */
	protected void assertVariable(String name, String expected) {
		assertThat(rs.getVariable(name), is(equalTo(expected)));
	}

	/**
	 * Asserts that a substitution pattern has the expected value.
	 *
	 * @param name     the substitution name
	 * @param expected the expected substitution pattern
	 */
	protected void assertSubstitution(String name, String expected) {
		assertThat(rs.getSubstitution(name), is(equalTo(expected)));
	}

	/**
	 * Asserts that a person substitution pattern has the expected value.
	 *
	 * @param name     the person substitution name
	 * @param expected the expected person substitution pattern
	 */
	protected void assertPerson(String name, String expected) {
		assertThat(rs.getPerson(name), is(equalTo(expected)));
	}

	/**
	 * Sets a user variable.
	 *
	 * @param name  the variable name
	 * @param value the variable value
	 */
	protected void setUservar(String name, String value) {
		rs.setUservar(username, name, value);
	}

	/**
	 * Asserts that a user variable has the expected value.
	 *
	 * @param name     the variable name
	 * @param expected the expected value
	 */
	protected void assertUservar(String name, String expected) {
		assertThat(rs.getUservar(username, name), is(equalTo(expected)));
	}

	/**
	 * Asserts that a given input gets the expected reply.
	 *
	 * @param message  the input message
	 * @param expected the expected reply
	 */
	protected void assertReply(String message, String expected) {
		assertThat(rs.reply(username, message), is(equalTo(expected)));
	}

	/**
	 * Asserts that a given input gets one of the expected replies.
	 *
	 * @param message  the input message
	 * @param expected the expected replies
	 */
	protected void assertReply(String message, String... expected) {
		assertThat(rs.reply(username, message), isIn(expected));
	}

	/**
	 * Asserts that a given input throws the expected exception.
	 *
	 * @param message  the input message
	 * @param expected the expected exception
	 */
	protected void assertReplyThrows(String message, Class<?> expected) {
		try {
			String reply = rs.reply(username, message);
			fail("Expected: is a instance of " + expected.getCanonicalName() + " but: was \"" + reply + "\"");
		} catch (Exception e) {
			assertThat(e, is(instanceOf(expected)));
		}
	}
}
