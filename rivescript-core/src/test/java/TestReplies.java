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

import org.junit.Test;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @author Noah Petherbridge
 */
public class TestReplies extends TestBase {

	public String replies() {
		return "replies";
	}

	@Test
	public void testPrevious() {
		this.setUp("previous.rive");

		this.reply("Knock knock", "Who's there?");
		this.reply("Canoe", "Canoe who?");
		this.reply("Canoe help me with my homework?", "Haha! Canoe help me with my homework!");
		this.reply("hello", "I don't know.");
	}

	@Test
	public void testRandom() {
		this.setUp("random.rive");

		this.reply("test random response", new String[] {
				"One.",
				"Two.",
		});
		this.reply("test random tag", new String[] {
				"This sentence has a random word.",
				"This sentence has a random bit.",
		});
	}

	@Test
	public void testContinuations() {
		this.setUp("continuations.rive");

		this.reply("Tell me a poem.", "There once was a man named Tim, "
				+ "who never quite learned how to swim. "
				+ "He fell off a dock, and sank like a rock, "
				+ "and that was the end of him.");
	}

	@Test
	public void testRedirects() {
		this.setUp("redirects.rive");

		this.reply("hello", "Hi there!");
		this.reply("hey", "Hi there!");
		this.reply("hi there", "Hi there!");
	}

	@Test
	public void testConditionals() {
		this.setUp("conditionals.rive");

		String age_q = "What can I do?";
		this.reply(age_q, "I don't know.");

		Map<String, String> ages = new HashMap<String, String>();
		ages.put("16", "Not much of anything.");
		ages.put("18", "Vote.");
		ages.put("20", "Vote.");
		ages.put("22", "Drink.");
		ages.put("24", "Drink.");
		ages.put("25", "Rent a car for cheap.");
		ages.put("27", "Anything you want.");

		Iterator it = ages.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry pair = (Map.Entry) it.next();
			this.reply("I am " + pair.getKey() + " years old.", "OK.");
			this.reply(age_q, (String) pair.getValue());
			it.remove();
		}

		this.reply("Am I your master?", "No.");
		this.uservar("master", "true");
		this.reply("Am I your master?", "Yes.");
	}

	@Test
	public void testSetUservars() {
		this.setUp("set-uservars.rive");
		this.uservar("name", "Aiden");
		this.uservar("age", "5");

		this.reply("What is my name?", "Your name is Aiden.");
		this.reply("how old am I?", "You are 5.");
	}

	@Test
	public void testQuestionMark() {
		this.setUp("question-mark.rive");

		this.reply("google java", "<a href=\"https://www.google.com/search?q=java\">Results are here</a>");
	}
}
