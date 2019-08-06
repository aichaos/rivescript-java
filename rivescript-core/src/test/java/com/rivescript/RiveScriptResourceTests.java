/*
 * Copyright (c) 2016-2017 the original author or authors.
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

package com.rivescript;

import jdk.internal.jline.internal.TestAccessible;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * Tests for the RiveScript classpath load methods.
 */
public class RiveScriptResourceTests {
	/**
	 * This method tests the loadInputStream() method of RiveScript. This implies
	 * usage of the loadReader() method as well, as it delegates internally.
	 *
	 * @throws IOException This implies that the resource could not be read.
	 */
	@Test
	public void testLoadFromInputStream() throws IOException {
		RiveScript engine=new RiveScript();
		try(InputStream is=RiveScript.class.getResourceAsStream("/test.rive")) {
			engine.loadInputStream(is);
			engine.sortReplies();
			assertEquals(engine.reply("anyone", "hello"), "Hi there!");
		}
	}

	/**
	 * This method tests the loadInputStream() when passed a nonexistent resource.
	 * Internally, it catches the NullPointerException and ignores it, throwing
	 * exceptions in the test for any other outcome.
	 *
	 * @throws IOException This should logically never happen, as the resource is
	 * acquired via getResourceAsStream(), which will return null rather than an IOException.
	 */
	@Test
	public void testLoadFromNull() throws IOException {
		RiveScript engine=new RiveScript();
		try(InputStream is=RiveScript.class.getResourceAsStream("/notthere.rive")) {
			assertNull(is);
			engine.loadInputStream(is);
			throw new AssertionError("Should have received error from loadInputStream with null input");
		} catch (NullPointerException e) {
			// this is expected output from loadInputStream()
		}
	}
}
