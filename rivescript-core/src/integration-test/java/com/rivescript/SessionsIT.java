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

import com.rivescript.session.NoOpSessionManager;
import com.rivescript.session.ThawAction;
import org.junit.Test;

/**
 * @author Noah Petherbridge
 * @author Marcel Overdijk
 */
public class SessionsIT extends BaseIT {

	private String[] commonSessionTest = new String[] {
			"+ my name is *",
			"- <set name=<formal>>Nice to meet you, <get name>.",
			"+ who am i",
			"- Aren't you <get name>?",
			"+ what did i just say",
			"- You just said: <input1>",
			"+ what did you just say",
			"- I just said: <reply1>",
			"+ i hate you",
			"- How mean!{topic=apology}",
			"> topic apology",
			"+ *",
			"- Nope, I'm mad at you.",
			"< topic"
	};

	@Test
	public void testNoOpSessionManager() {
		rs = new RiveScript(Config.Builder.basic().sessionManager(new NoOpSessionManager()).build());
		setUp(commonSessionTest);
		assertReply("My name is Aiden", "Nice to meet you, undefined.");
		assertReply("Who am I?", "Aren't you undefined?");
		assertReply("What did I just say?", "You just said: undefined");
		assertReply("What did you just say?", "I just said: undefined");
		assertReply("I hate you", "How mean!");
		assertReply("My name is Aiden", "Nice to meet you, undefined.");
	}

	@Test
	public void testConcurrentHashMapSessionManager() {
		rs = new RiveScript();
		setUp(commonSessionTest);
		assertReply("My name is Aiden", "Nice to meet you, Aiden.");
		assertReply("What did I just say?", "You just said: my name is aiden");
		assertReply("Who am I?", "Aren't you Aiden?");
		assertReply("What did you just say?", "I just said: Aren't you Aiden?");
		assertReply("I hate you!", "How mean!");
		assertReply("My name is Bob", "Nope, I'm mad at you.");
	}

	@Test
	public void testFreezeThaw() {
		rs = new RiveScript();
		setUp(new String[] {
				"+ my name is *",
				"- <set name=<formal>>Nice to meet you, <get name>.",
				"",
				"+ who am i",
				"- Aren't you <get name>?"
		});
		assertReply("My name is Aiden", "Nice to meet you, Aiden.");
		assertReply("Who am I?", "Aren't you Aiden?");

		rs.freezeUservars(username);
		assertReply("My name is Bob", "Nice to meet you, Bob.");
		assertReply("Who am I?", "Aren't you Bob?");

		rs.thawUservars(username, ThawAction.THAW);
		assertReply("Who am I?", "Aren't you Aiden?");
		rs.freezeUservars(username);

		assertReply("My name is Bob", "Nice to meet you, Bob.");
		assertReply("Who am I?", "Aren't you Bob?");
		rs.thawUservars(username, ThawAction.DISCARD);
		assertReply("Who am I?", "Aren't you Bob?");
	}
}
