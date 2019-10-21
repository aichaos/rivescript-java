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

package com.rivescript;

import static com.rivescript.RiveScript.DEFAULT_OBJECT_NOT_FOUND_MESSAGE;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.rivescript.lang.python.PythonHandler;

/**
 * Integration tests for {@link PythonHandler}.
 *
 * @author Marcel Overdijk
 */
public class PythonObjectIT extends BaseIT {

	@BeforeClass
	public static void setup() throws Exception {
		System.setProperty("python.cachedir.skip", "true");
		System.setProperty("python.import.site", "false");
	}
	@Before
	public void setUp() {
		rs = new RiveScript();
		rs.setHandler("python", new PythonHandler());
		setUp(new String[] {
				"> object reverse python",
				"    msg = ' '.join(args)",
				"    return msg[::-1]",
				"< object",
				"> object setname python",
				"    username = rs.currentUser()",
				"    rs.setUservar(username, 'name', args[0])",
				"< object",
				"+ reverse *",
				"- <call>reverse <star></call>",
				"+ my name is *",
				"- I will remember that.<call>setname \"<formal>\"</call>",
				"+ what is my name",
				"- You are <get name>."
		});
	}

	@Test
	public void testReverse() {
		assertReply("reverse hello world", "dlrow olleh");
	}

	@Test
	public void testSetName() {
		assertReply("my name is john doe", "I will remember that.");
		assertReply("what is my name", "You are John Doe.");
	}

	@Test
	public void testNoRubyHandler() {
		rs.removeHandler("python");
		assertReply("reverse hello world", DEFAULT_OBJECT_NOT_FOUND_MESSAGE);
	}
}
