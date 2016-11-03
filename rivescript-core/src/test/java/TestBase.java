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

import com.rivescript.RiveScript;

import java.io.File;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * @author Noah Petherbridge
 */
public class TestBase {

	protected RiveScript rs;

	public String replies() {
		return "undefined";
	}

	public void setUp(String file) {
		this.rs = new RiveScript();
		this.rs.loadFile(getAbsolutePath("fixtures/" + this.replies() + "/" + file));
		this.rs.sortReplies();
	}

	public void setUp(String file, boolean debug) {
		this.rs = new RiveScript(debug);
		this.rs.loadFile(getAbsolutePath("fixtures/" + this.replies() + "/" + file));
		this.rs.sortReplies();
	}

	public void extend(String file) {
		this.rs.loadFile(getAbsolutePath("fixtures/" + this.replies() + "/" + file));
		this.rs.sortReplies();
	}

	public void uservar(String key, String value) {
		this.rs.setUservar("localuser", key, value);
	}

	public void reply(String input, String expect) {
		String reply = this.rs.reply("localuser", input);
		// System.out.println(input + ": " + expect + " <-> " + reply);
		assertEquals(expect, reply);
	}

	public void reply(String input, String[] expected) {
		String reply = this.rs.reply("localuser", input);
		for (String expect : expected) {
			if (reply.equals(expect)) {
				assertTrue(true);
				return;
			}
		}

		fail();
	}

	private String getAbsolutePath(String fileName) {
		ClassLoader classLoader = getClass().getClassLoader();
		File file = new File(classLoader.getResource(fileName).getFile());
		return file.getAbsolutePath();
	}
}
