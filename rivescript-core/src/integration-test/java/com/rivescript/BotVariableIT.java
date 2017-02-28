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
public class BotVariableIT extends BaseIT {

	@Test
	public void testBotVariables() {
		rs = new RiveScript();
		setUp(new String[] {
				"! var name = Aiden",
				"! var age = 5",
				"",
				"+ what is your name",
				"- My name is <bot name>.",
				"",
				"+ how old are you",
				"- I am <bot age>.",
				"",
				"+ what are you",
				"- I'm <bot gender>.",
				"",
				"+ happy birthday",
				"- <bot age=6>Thanks!"
		});
		assertReply("What is your name?", "My name is Aiden.");
		assertReply("How old are you?", "I am 5.");
		assertReply("What are you?", "I'm undefined.");
		assertReply("Happy birthday!", "Thanks!");
		assertReply("How old are you?", "I am 6.");
	}

	@Test
	public void testGlobalVariables() {
		rs = new RiveScript();
		setUp(new String[] {
				"! global debug = false",
				"",
				"+ debug mode",
				"- Debug mode is: <env debug>",
				"",
				"+ set debug mode *",
				"- <env debug=<star>>Switched to <star>."
		});
		assertReply("Debug mode.", "Debug mode is: false");
		assertReply("Set debug mode true", "Switched to true.");
		assertReply("Debug mode?", "Debug mode is: true");
	}
}
