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

import static com.rivescript.RiveScript.DEFAULT_REPLIES_NOT_SORTED_MESSAGE;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

/**
 * @author Noah Petherbridge
 * @author Marcel Overdijk
 */
public class RiveScriptIT extends BaseIT {

	@Test
	public void testNoSorting() {
		rs = new RiveScript();
		assertReply("hello bot", DEFAULT_REPLIES_NOT_SORTED_MESSAGE);
	}

	@Test
	public void testFailedLoading() {
		rs = new RiveScript();
		try {
			rs.loadFile("/root/notexist345613123098");
			fail("I tried to load an obviously missing file, but I succeeded unexpectedly");
		} catch (RiveScriptException e) {
			assertThat(e.getMessage(), is(equalTo("File '/root/notexist345613123098' not found")));
		}

		try {
			rs.loadDirectory("/root/notexist412901890281");
			fail("I tried to load an obviously missing directory, but I succeeded unexpectedly");
		} catch (RiveScriptException e) {
			assertThat(e.getMessage(), is(equalTo("Directory '/root/notexist412901890281' not found")));
		}
	}
}
