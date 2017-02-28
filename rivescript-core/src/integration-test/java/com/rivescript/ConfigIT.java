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
public class ConfigIT extends BaseIT {

	@Test
	public void testConfigApi() {
		rs = new RiveScript();
		setUp(new String[] {
				"+ hello bot",
				"- Hello human."
		});

		// Global variables.
		assertGlobal("global test", null);
		rs.setGlobal("global test", "on");
		assertGlobal("global test", "on");
		rs.setGlobal("global test", null);
		assertGlobal("global test", null);

		// Bot variables.
		assertVariable("var test", null);
		rs.setVariable("var test", "on");
		assertVariable("var test", "on");
		rs.setVariable("var test", null);
		assertVariable("var test", null);

		// Substitutions.
		assertSubstitution("what's", null);
		rs.setSubstitution("what's", "what is");
		assertSubstitution("what's", "what is");
		rs.setSubstitution("what's", null);
		assertSubstitution("what's", null);

		// Person substitutions.
		assertPerson("you", null);
		rs.setPerson("you", "me");
		assertPerson("you", "me");
		rs.setPerson("you", null);
		assertPerson("you", null);
	}
}
