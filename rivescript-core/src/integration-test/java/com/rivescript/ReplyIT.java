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

import java.util.HashMap;
import java.util.Map;

import static com.rivescript.RiveScript.DEFAULT_DEEP_RECURSION_MESSAGE;

/**
 * @author Noah Petherbridge
 * @author Marcel Overdijk
 */
public class ReplyIT extends BaseIT {

	@Test
	public void testPrevious() {
		rs = new RiveScript();
		setUp(new String[] {
				"! sub who's  = who is",
				"! sub it's   = it is",
				"! sub didn't = did not",
				"",
				"+ knock knock",
				"- Who's there?",
				"",
				"+ *",
				"% who is there",
				"- <sentence> who?",
				"",
				"+ *",
				"% * who",
				"- Haha! <sentence>!",
				"",
				"+ *",
				"- I don't know."
		});
		assertReply("knock knock", "Who's there?");
		assertReply("Canoe", "Canoe who?");
		assertReply("Canoe help me with my homework?", "Haha! Canoe help me with my homework!");
		assertReply("hello", "I don't know.");
	}

	@Test
	public void testRandom() {
		rs = new RiveScript();
		setUp(new String[] {
				"+ test random response",
				"- One.",
				"- Two.",
				"",
				"+ test random tag",
				"- This sentence has a random {random}word|bit{/random}."

		});
		assertReply("test random response", "One.", "Two.");
		assertReply("test random tag", "This sentence has a random word.", "This sentence has a random bit.");
	}

	@Test
	public void testContinuations() {
		rs = new RiveScript();
		setUp(new String[] {
				"+ tell me a poem",
				"- There once was a man named Tim,\\s",
				"^ who never quite learned how to swim.\\s",
				"^ He fell off a dock, and sank like a rock,\\s",
				"^ and that was the end of him."
		});
		assertReply("Tell me a poem.",
				"There once was a man named Tim, who never quite learned how to swim. He fell off a dock, and sank like a rock, and that was the end of him.");
	}

	// Test matching a large number of stars, greater than <star1>-<star9>
	@Test
	public void testManyStars() {
		rs = new RiveScript();
		setUp(new String[] {
				"// 10 stars.",
				"+ * * * * * * * * * *",
				"- That's a long one. 1=<star1>; 5=<star5>; 9=<star9>; 10=<star10>;",
				"",
				"// 16 stars!",
				"+ * * * * * * * * * * * * * * * *",
				"- Wow! 1=<star1>; 3=<star3>; 7=<star7>; 14=<star14>; 15=<star15>; 16=<star16>;"
		});
		assertReply("one two three four five six seven eight nine ten eleven", "That's a long one. 1=one; 5=five; 9=nine; 10=ten eleven;");
		assertReply("0 1 2 3 4 5 6 7 8 9 A B C D E F G H I J K", "Wow! 1=0; 3=2; 7=6; 14=d; 15=e; 16=f g h i j k;");
	}

	@Test
	public void testRedirects() {
		rs = new RiveScript();
		setUp(new String[] {
				"+ hello",
				"- Hi there!",
				"",
				"+ hey",
				"@ hello",
				"",
				"+ hi there",
				"- {@hello}",
				"",
				"// Infinite recursion between these two.",
				"+ one",
				"@ two",
				"+ two",
				"@ one",
				"",
				"// Variables can throw off redirects with their capitalizations,",
				"// so make sure redirects handle this properly.",
				"! var master = Kirsle",
				"+ my name is (<bot master>)",
				"- <set name=<formal>>That's my botmaster's name too.",
				"",
				"+ call me <bot master>",
				"@ my name is <bot master>"
		});
		assertReply("hello", "Hi there!");
		assertReply("hey", "Hi there!");
		assertReply("hi there", "Hi there!");
		assertReply("my name is Kirsle", "That's my botmaster's name too.");
		assertReply("call me kirsle", "That's my botmaster's name too.");
		assertReply("one", DEFAULT_DEEP_RECURSION_MESSAGE);
	}

	@Test
	public void testConditionals() {
		rs = new RiveScript();
		setUp(new String[] {
				"+ i am # years old",
				"- <set age=<star>>OK.",
				"",
				"+ what can i do",
				"* <get age> == undefined => I don't know.",
				"* <get age> >  25 => Anything you want.",
				"* <get age> == 25 => Rent a car for cheap.",
				"* <get age> >= 21 => Drink.",
				"* <get age> >= 18 => Vote.",
				"* <get age> <  18 => Not much of anything.",
				"",
				"+ am i your master",
				"* <get master> == true => Yes.",
				"- No."
		});
		String ageQ = "What can I do?";
		assertReply(ageQ, "I don't know.");

		Map<String, String> ages = new HashMap<>();
		ages.put("16", "Not much of anything.");
		ages.put("18", "Vote.");
		ages.put("20", "Vote.");
		ages.put("22", "Drink.");
		ages.put("24", "Drink.");
		ages.put("25", "Rent a car for cheap.");
		ages.put("27", "Anything you want.");
		for (Map.Entry<String, String> entry : ages.entrySet()) {
			String age = entry.getKey();
			String expected = entry.getValue();
			assertReply(String.format("I am %s years old.", age), "OK.");
			assertReply(ageQ, expected);
		}

		assertReply("Am I your master?", "No.");
		setUservar("master", "true");
		assertReply("Am I your master?", "Yes.");
	}

	@Test
	public void testEmbeddedTags() {
		rs = new RiveScript();
		setUp(new String[] {
				"+ my name is *",
				"* <get name> != undefined => <set oldname=<get name>>I thought\\s",
				"^ your name was <get oldname>?",
				"^ <set name=<formal>>",
				"- <set name=<formal>>OK.",
				"",
				"+ what is my name",
				"- Your name is <get name>, right?",
				"",
				"+ html test",
				"- <set name=<b>Name</b>>This has some non-RS <em>tags</em> in it."
		});
		assertReply("What is my name?", "Your name is undefined, right?");
		assertReply("My name is Alice.", "OK.");
		assertReply("My name is Bob.", "I thought your name was Alice?");
		assertReply("What is my name?", "Your name is Bob, right?");
		assertReply("HTML Test", "This has some non-RS <em>tags</em> in it.");
	}

	@Test
	public void testSetUservars() {
		rs = new RiveScript();
		setUp(new String[] {
				"+ what is my name",
				"- Your name is <get name>.",
				"",
				"+ how old am i",
				"- You are <get age>."
		});
		setUservar("name", "Aiden");
		setUservar("age", "5");
		assertReply("What is my name?", "Your name is Aiden.");
		assertReply("How old am I?", "You are 5.");
	}

	@Test
	public void testQuestionmark() {
		rs = new RiveScript();
		setUp(new String[] {
				"+ google *",
				"- <a href=\"https://www.google.com/search?q=<star>\">Results are here</a>"
		});
		assertReply("google golang", "<a href=\"https://www.google.com/search?q=golang\">Results are here</a>");
	}
}
