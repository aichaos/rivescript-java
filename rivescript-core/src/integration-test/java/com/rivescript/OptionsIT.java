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
public class OptionsIT extends BaseIT {

	@Test
	public void testBotVariables() {
		rs = new RiveScript();
		setUp(new String[] {
				"// Default concat mode = none",
				"+ test concat default",
				"- Hello",
				"^ world!",
				"",
				"! local concat = space",
				"+ test concat space",
				"- Hello",
				"^ world!",
				"",
				"! local concat = none",
				"+ test concat none",
				"- Hello",
				"^ world!",
				"",
				"! local concat = newline",
				"+ test concat newline",
				"- Hello",
				"^ world!",
				"",
				"// invalid concat setting is equivalent to 'none'",
				"! local concat = foobar",
				"+ test concat foobar",
				"- Hello",
				"^ world!",
				"",
				"// the option is file scoped so it can be left at",
				"// any setting and won't affect subsequent parses",
				"! local concat = newline"
		});
		setUp(new String[] {
				"// concat mode should be restored to the default in a",
				"// separate file/stream parse",
				"+ test concat second file",
				"- Hello",
				"^ world!"
		});
		assertReply("test concat default", "Helloworld!");
		assertReply("test concat space", "Hello world!");
		assertReply("test concat none", "Helloworld!");
		assertReply("test concat newline", "Hello\nworld!");
		assertReply("test concat foobar", "Helloworld!");
		assertReply("test concat second file", "Helloworld!");
	}
}
