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
public class BeginIT extends BaseIT {

	@Test
	public void testNoBeginBlock() {
		rs = new RiveScript();
		setUp(new String[] {
				"+ hello bot",
				"- Hello human."
		});
		assertReply("Hello bot", "Hello human.");
	}

	@Test
	public void testSimpleBeginBlock() {
		rs = new RiveScript();
		setUp(new String[] {
				"> begin",
				"    + request",
				"    - {ok}",
				"< begin",
				"",
				"+ hello bot",
				"- Hello human."
		});
		assertReply("Hello bot", "Hello human.");
	}

	@Test
	public void testBlockedBeginBlock() {
		rs = new RiveScript();
		setUp(new String[] {
				"> begin",
				"    + request",
				"    - Nope.",
				"< begin",
				"",
				"+ hello bot",
				"- Hello human."
		});
		assertReply("Hello bot", "Nope.");
	}

	@Test
	public void testConditionalBeginBlock() {
		rs = new RiveScript();
		setUp(new String[] {
				"> begin",
				"    + request",
				"    * <get met> == undefined => <set met=true>{ok}",
				"    * <get name> != undefined => <get name>: {ok}",
				"    - {ok}",
				"< begin",
				"",
				"+ hello bot",
				"- Hello human.",
				"",
				"+ my name is *",
				"- <set name=<formal>>Hello, <get name>."
		});
		assertReply("Hello bot", "Hello human.");
		setUservar("met", "true");
		assertUservar("name", null);
		assertReply("My name is bob", "Hello, Bob.");
		assertUservar("name", "Bob");
		assertReply("Hello bot", "Bob: Hello human.");
	}

	@Test
	public void testDefinitions() {
		rs = new RiveScript();
		setUp(new String[] {
				"! global g1 = one",
				"! global g2 = two",
				"! global g2 = <undef>",
				"",
				"! var v1 = one",
				"! var v2 = two",
				"! var v2 = <undef>",
				"",
				"! sub what's = what is",
				"! sub who's = who is",
				"! sub who's = <undef>",
				"",
				"! person you = me",
				"! person me = you",
				"! person your = my",
				"! person my = your",
				"! person your = <undef>",
				"! person my = <undef>"
		});
		assertGlobal("g1", "one");
		assertGlobal("g2", null);
		assertVariable("v1", "one");
		assertVariable("v2", null);
		assertSubstitution("what's", "what is");
		assertSubstitution("who's", null);
		assertPerson("you", "me");
		assertPerson("your", null);
	}
}
