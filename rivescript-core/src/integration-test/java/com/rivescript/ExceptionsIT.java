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

import com.rivescript.exception.DeepRecursionException;
import com.rivescript.exception.RepliesNotSortedException;
import com.rivescript.exception.ReplyNotFoundException;
import com.rivescript.exception.ReplyNotMatchedException;
import org.junit.Test;

/**
 * @author Noah Petherbridge
 * @author Marcel Overdijk
 */
public class ExceptionsIT extends BaseIT {

	@Test
	public void testThrowExceptions() {
		rs = new RiveScript(Config.Builder.basic().throwExceptions(true).build());
		assertReplyThrows("hello bot", RepliesNotSortedException.class);

		setUp(new String[] {
				"+ hello bot",
				"- Hello human.",
				"",
				"// Infinite recursion between these two.",
				"+ one",
				"@ two",
				"+ two",
				"@ one",
				"",
				"// Empty reply.",
				"+ test not found",
				"- "
		});
		assertReply("hello bot", "Hello human.");
		assertReplyThrows("one", DeepRecursionException.class);
		assertReplyThrows("test not matched", ReplyNotMatchedException.class);
		assertReplyThrows("test not found", ReplyNotFoundException.class);
	}
}
