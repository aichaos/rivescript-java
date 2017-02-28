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

import com.rivescript.macro.Subroutine;
import com.rivescript.util.StringUtils;
import org.junit.Before;
import org.junit.Test;

/**
 * Integration tests for Java {@link Subroutine}.
 *
 * @author Marcel Overdijk
 */
public class JavaObjectIT extends BaseIT {

	@Before
	public void setUp() {
		rs = new RiveScript();
		rs.setSubroutine("reverse", new Subroutine() {

			@Override
			public String call(RiveScript rs, String[] args) {
				String msg = StringUtils.join(args, " ");
				return new StringBuilder(msg).reverse().toString();
			}
		});
		rs.setSubroutine("setname", new Subroutine() {

			@Override
			public String call(RiveScript rs, String[] args) {
				String username = rs.currentUser();
				rs.setUservar(username, "name", args[0]);
				return null;
			}
		});
		setUp(new String[] {
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
	public void testRemoveSubroutine() {
		rs.removeSubroutine("reverse");
		assertReply("reverse hello world", ERR_OBJECT_NOT_FOUND);
	}
}
