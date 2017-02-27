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

package integration.lang.groovy;

import com.rivescript.RiveScript;
import com.rivescript.lang.groovy.GroovyHandler;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

/**
 * Tests for {@link GroovyHandler}.
 *
 * @author Marcel Overdijk
 */
public class GroovyHandlerTests {

	private RiveScript rs;

	@Before
	public void setUp() {
		rs = new RiveScript();
		rs.setHandler("groovy", new GroovyHandler(rs));
		rs.stream(new String[] {
				"> object reverse groovy",
				"    def msg = args.join(' ')",
				"    return msg.reverse()",
				"< object",
				"> object setname groovy",
				"    def username = rs.currentUser()",
				"    rs.setUservar(username, 'name', args[0])",
				"< object",
				"+ reverse *",
				"- <call>reverse <star></call>",
				"+ my name is *",
				"- I will remember that.<call>setname \"<formal>\"</call>",
				"+ what is my name",
				"- You are <get name>."
		});
		rs.sortReplies();
	}

	@Test
	public void testReverse() {
		assertThat(rs.reply("local-user", "reverse hello world"), is(equalTo("dlrow olleh")));
	}

	@Test
	public void testSetName() {
		assertThat(rs.reply("local-user", "my name is john doe"), is(equalTo("I will remember that.")));
		assertThat(rs.reply("local-user", "what is my name"), is(equalTo("You are John Doe.")));
	}

	@Test
	public void testNoGroovyHandler() {
		rs.removeHandler("groovy");
		assertThat(rs.reply("local-user", "reverse hello world"), is(equalTo("[ERR: Object Not Found]")));
	}
}
