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
public class UnicodeIT extends BaseIT {

	@Test
	public void testUnicode() {
		rs = new RiveScript(Config.utf8());
		setUp(new String[] {
				"! sub who's = who is",
				"+ äh",
				"- What's the matter?",
				"",
				"+ ブラッキー",
				"- エーフィ",
				"",
				"// Make sure %Previous continues working in UTF-8 mode.",
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
				"// And with UTF-8.",
				"+ tëll më ä pöëm",
				"- Thërë öncë wäs ä män nämëd Tïm",
				"",
				"+ more",
				"% thërë öncë wäs ä män nämëd tïm",
				"- Whö nëvër qüïtë lëärnëd höw tö swïm",
				"",
				"+ more",
				"% whö nëvër qüïtë lëärnëd höw tö swïm",
				"- Hë fëll öff ä döck, änd sänk lïkë ä röck",
				"",
				"+ more",
				"% hë fëll öff ä döck änd sänk lïkë ä röck",
				"- Änd thät wäs thë ënd öf hïm."
		});
		assertReply("äh", "What's the matter?");
		assertReply("ブラッキー", "エーフィ");
		assertReply("knock knock", "Who's there?");
		assertReply("Orange", "Orange who?");
		assertReply("banana", "Haha! Banana!");
		assertReply("tëll më ä pöëm", "Thërë öncë wäs ä män nämëd Tïm");
		assertReply("more", "Whö nëvër qüïtë lëärnëd höw tö swïm");
		assertReply("more", "Hë fëll öff ä döck, änd sänk lïkë ä röck");
		assertReply("more", "Änd thät wäs thë ënd öf hïm.");
	}

	@Test
	public void testPunctuation() {
		rs = new RiveScript(Config.utf8());
		setUp(new String[] {
				"+ hello bot",
				"- Hello human!"
		});
		assertReply("Hello bot", "Hello human!");
		assertReply("Hello, bot!", "Hello human!");
		assertReply("Hello: Bot", "Hello human!");
		assertReply("Hello... bot?", "Hello human!");
	}

	@Test
	public void testUnicodePunctuation() {
		rs = new RiveScript(Config.Builder.utf8().unicodePunctuation("xxx").build());
		setUp(new String[] {
				"+ hello bot",
				"- Hello human!"
		});
		assertReply("Hello bot", "Hello human!");
		assertReply("Hello, bot!", ERR_NO_REPLY_MATCHED);
	}
}
