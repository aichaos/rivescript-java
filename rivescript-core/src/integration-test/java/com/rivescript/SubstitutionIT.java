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
public class SubstitutionIT extends BaseIT {

	@Test
	public void testSubstitutions() {
		rs = new RiveScript();
		setUp(new String[] {
				"+ whats up",
				"- nm.",
				"",
				"+ what is up",
				"- Not much."
		});
		assertReply("whats up", "nm.");
		assertReply("what's up?", "nm.");
		assertReply("what is up?", "Not much.");

		setUp(new String[] {
				"! sub whats  = what is",
				"! sub what's = what is"
		});
		assertReply("whats up", "Not much.");
		assertReply("what's up?", "Not much.");
		assertReply("What is up?", "Not much.");
	}

	@Test
	public void testPersonSubstitutions() {
		rs = new RiveScript();
		setUp(new String[] {
				"+ say *",
				"- <person>"
		});
		assertReply("say I am cool", "i am cool");
		assertReply("say You are dumb", "you are dumb");

		setUp(new String[] {
				"! person i am    = you are",
				"! person you are = I am"
		});
		assertReply("say I am cool", "you are cool");
		assertReply("say You are dumb", "I am dumb");
	}
}
