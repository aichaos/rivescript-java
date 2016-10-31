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

/**
 * @author Noah Petherbridge
 */
public class TestSubstitutions extends TestBase {

	public String replies() {
		return "substitutions";
	}

	@Test
	public void testSubstitutions() {
		this.setUp("no-subs.rive");

		this.reply("whats up", "nm.");
		this.reply("what's up?", "nm.");
		this.reply("what is up?", "Not much.");

		this.extend("subs.rive");

		this.reply("whats up?", "Not much.");
		this.reply("what's up?", "Not much.");
		this.reply("What is up?", "Not much.");
	}

	@Test
	public void testPersonSubstitutions() {
		this.setUp("no-subs.rive");

		this.reply("say I am cool", "i am cool");
		this.reply("say You are dumb", "you are dumb");

		this.extend("person-subs.rive");

		this.reply("say I am cool", "you are cool");
		this.reply("say You are dumb", "I am dumb");
	}
}
