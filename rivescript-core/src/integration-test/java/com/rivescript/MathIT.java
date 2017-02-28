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

import org.junit.Test;

/**
 * @author Noah Petherbridge
 * @author Marcel Overdijk
 */
public class MathIT extends BaseIT {

	@Test
	public void testMath() {
		rs = new RiveScript();
		setUp(new String[] {
				"+ test counter",
				"- <set counter=0>counter set",
				"",
				"+ show",
				"- counter = <get counter>",
				"",
				"+ add",
				"- <add counter=1>adding",
				"",
				"+ sub",
				"- <sub counter=1>subbing",
				"",
				"+ div",
				"- <set counter=10>",
				"^ <div counter=2>",
				"^ divving",
				"",
				"+ mult",
				"- <set counter=10>",
				"^ <mult counter=2>",
				"^ multing"

		});
		assertReply("test counter", "counter set");
		assertReply("show", "counter = 0");

		assertReply("add", "adding");
		assertReply("show", "counter = 1");

		assertReply("sub", "subbing");
		assertReply("show", "counter = 0");

		assertReply("div", "divving");
		assertReply("show", "counter = 5");

		assertReply("mult", "multing");
		assertReply("show", "counter = 20");
	}
}
