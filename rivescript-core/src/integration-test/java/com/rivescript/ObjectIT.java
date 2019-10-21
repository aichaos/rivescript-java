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



import com.rivescript.macro.ObjectHandler;
import com.rivescript.util.StringUtils;
import org.junit.Test;
import java.util.HashMap;
import java.util.Map;

import static com.rivescript.RiveScript.DEFAULT_OBJECT_NOT_FOUND_MESSAGE;


/**
 * @author Noah Petherbridge
 * @author Marcel Overdijk
 */
public class ObjectIT extends BaseIT {

	@Test
	public void testMacroParsing() {
		rs = new RiveScript();
		rs.setHandler("text", new MockHandler());
		setUp(new String[] {
				"> object hello text",
				"    Hello world!",
				"< object",
				"",
				"> object goodbye javascript",
				"    return \"Goodbye\";",
				"< object",
				"",
				"+ hello",
				"- <call>hello</call>",
				"",
				"+ goodbye",
				"- <call>goodbye</call>"
		});
		assertReply("Hello", "    Hello world!");
		assertReply("goodbye", DEFAULT_OBJECT_NOT_FOUND_MESSAGE);
	}

	private static class MockHandler implements ObjectHandler {

		private Map<String, String> codes = new HashMap<>();

		@Override
		public void load(RiveScript rs, String name, String[] code) {
			codes.put(name, StringUtils.join(code, "\n"));
		}

		@Override
		public String call(RiveScript rs, String name, String[] fields) {
			return codes.get(name);
		}
	}
}
