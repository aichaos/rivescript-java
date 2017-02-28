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
public class TriggerIT extends BaseIT {

	@Test
	public void testAtomicTriggers() {
		rs = new RiveScript();
		setUp(new String[] {
				"+ hello bot",
				"- Hello human.",
				"",
				"+ what are you",
				"- I am a RiveScript bot."
		});
		assertReply("Hello bot", "Hello human.");
		assertReply("What are you?", "I am a RiveScript bot.");
	}

	@Test
	public void testWildcardTriggers() {
		rs = new RiveScript();
		setUp(new String[] {
				"+ my name is *",
				"- Nice to meet you, <star>.",
				"",
				"+ * told me to say *",
				"- Why did <star1> tell you to say <star2>?",
				"",
				"+ i am # years old",
				"- A lot of people are <star>.",
				"",
				"+ i am _ years old",
				"- Say that with numbers.",
				"",
				"+ i am * years old",
				"- Say that with fewer words.",
				"",
				"// Verify that wildcards in optionals are not matchable.",
				"+ my favorite [_] is *",
				"- Why is it <star1>?",
				"",
				"+ i have [#] questions about *",
				"- Well I don't have any answers about <star1>."
		});
		assertReply("my name is Bob", "Nice to meet you, bob.");
		assertReply("bob told me to say hi", "Why did bob tell you to say hi?");
		assertReply("i am 5 years old", "A lot of people are 5.");
		assertReply("i am five years old", "Say that with numbers.");
		assertReply("i am twenty five years old", "Say that with fewer words.");

		// TODO https://github.com/aichaos/rivescript-java/issues/42
		// assertReply("my favorite color is red", "Why is it red?");
		// assertReply("i have 2 questions about bots", "Well I don't have any answers about bots.");
	}

	@Test
	public void testAlternativesAndOptionals() {
		rs = new RiveScript();
		setUp(new String[] {
				"+ what (are|is) you",
				"- I am a robot.",
				"",
				"+ what is your (home|office|cell) [phone] number",
				"- It is 555-1234.",
				"",
				"+ [please|can you] ask me a question",
				"- Why is the sky blue?",
				"",
				"+ (aa|bb|cc) [bogus]",
				"- Matched.",
				"",
				"+ (yo|hi) [computer|bot] *",
				"- Matched."
		});
		assertReply("What are you?", "I am a robot.");
		assertReply("What is you?", "I am a robot.");

		assertReply("What is your home phone number?", "It is 555-1234.");
		assertReply("What is your home number?", "It is 555-1234.");
		assertReply("What is your cell phone number?", "It is 555-1234.");
		assertReply("What is your office number?", "It is 555-1234.");

		assertReply("Can you ask me a question?", "Why is the sky blue?");
		assertReply("Please ask me a question?", "Why is the sky blue?");
		assertReply("Ask me a question.", "Why is the sky blue?");

		assertReply("aa", "Matched.");
		assertReply("bb", "Matched.");
		assertReply("aa bogus", "Matched.");
		assertReply("aabogus", ERR_NO_REPLY_MATCHED);
		assertReply("bogus", ERR_NO_REPLY_MATCHED);

		assertReply("hi Aiden", "Matched.");
		assertReply("hi bot how are you?", "Matched.");
		assertReply("yo computer what time is it?", "Matched.");
		assertReply("yoghurt is yummy", ERR_NO_REPLY_MATCHED);
		assertReply("hide and seek is fun", ERR_NO_REPLY_MATCHED);
		assertReply("hip hip hurrah", ERR_NO_REPLY_MATCHED);
	}

	@Test
	public void testTriggerArrays() {
		rs = new RiveScript();
		setUp(new String[] {
				"! array colors = red blue green yellow white",
				"^ dark blue|light blue",
				"",
				"+ what color is my (@colors) *",
				"- Your <star2> is <star1>.",
				"",
				"+ what color was * (@colors) *",
				"- It was <star2>.",
				"",
				"+ i have a @colors *",
				"- Tell me more about your <star>."
		});
		assertReply("What color is my red shirt?", "Your shirt is red.");
		assertReply("What color is my blue car?", "Your car is blue.");
		assertReply("What color is my pink house?", ERR_NO_REPLY_MATCHED);
		assertReply("What color is my dark blue jacket?", "Your jacket is dark blue.");
		assertReply("What color was Napoleoan's white horse?", "It was white.");
		assertReply("What color was my red shirt?", "It was red.");
		assertReply("I have a blue car.", "Tell me more about your car.");
		assertReply("I have a cyan car.", ERR_NO_REPLY_MATCHED);
	}

	@Test
	public void testWeightedTriggers() {
		rs = new RiveScript();
		setUp(new String[] {
				"+ * or something{weight=10}",
				"- Or something. <@>",
				"",
				"+ can you run a google search for *",
				"- Sure!",
				"",
				"+ hello *{weight=20}",
				"- Hi there!"
		});
		assertReply("Hello robot.", "Hi there!");
		assertReply("Hello or something.", "Hi there!");
		assertReply("Can you run a Google search for Node", "Sure!");
		assertReply("Can you run a Google search for Node or something", "Or something. Sure!");
	}
}
