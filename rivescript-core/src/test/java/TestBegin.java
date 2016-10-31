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
public class TestBegin extends TestBase {

	public String replies() {
		return "begin";
	}

	@Test
	public void testNoBeginBlock() {
		this.setUp("no_begin_block.rive");
		this.reply("hello bot", "Hello human.");
	}

	@Test
	public void testSimpleBeginBlock() {
		this.setUp("simple_begin_block.rive");
		this.reply("Hello bot.", "Hello human.");
	}

	@Test
	public void testBlockedBeginBlock() {
		this.setUp("blocked_begin_block.rive");
		this.reply("Hello bot.", "Nope.");
	}

	@Test
	public void testConditionalBeginBlock() {
		this.setUp("conditional_begin_block.rive");
		this.reply("Hello bot.", "Hello human.");
		this.uservar("met", "true");
		this.uservar("name", "undefined");
		this.reply("My name is bob", "Hello, Bob.");
		this.uservar("name", "Bob");
		this.reply("Hello Bot", "Bob: Hello human.");
	}
}
