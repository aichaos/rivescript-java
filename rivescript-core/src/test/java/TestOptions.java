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
public class TestOptions extends TestBase {

	public String replies() {
		return "options";
	}

	@Test
	public void testConcat() {
		this.setUp("options.rive");
		this.extend("options-2.rive");

		this.reply("test concat default", "Helloworld!");
		this.reply("test concat space", "Hello world!");
		this.reply("test concat none", "Helloworld!");
		this.reply("test concat newline", "Hello\nworld!");
		this.reply("test concat foobar", "Helloworld!");
		this.reply("test concat second file", "Helloworld!");
	}

	@Test
	public void testConcatNewlineWithConditionals() {
		this.setUp("condition-nl.rive");

		this.reply("test a", "First A line\nSecond A line\nThird A line");
		this.reply("test b", "First B line\nSecond B line\nThird B line");
	}

	@Test
	public void testConcatSpaceWithConditionals() {
		this.setUp("condition-space.rive");

		this.reply("test a", "First A line Second A line Third A line");
		this.reply("test b", "First B line Second B line Third B line");
	}

	@Test
	public void testConcatWithConditionals() {
		this.setUp("condition-none.rive");

		this.reply("test a", "First A lineSecond A lineThird A line");
		this.reply("test b", "First B lineSecond B lineThird B line");
	}
}
