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
public class TestTopics extends TestBase {

	public String replies() {
		return "topics";
	}

	@Test
	public void testPunishmentTopic() {
		this.setUp("punishment.rive");

		this.reply("hello", "Hi there!");
		this.reply("How are you?", "Catch-all.");
		this.reply("Swear word!", "How rude! Apologize or I won't talk to you again.");
		this.reply("hello", "Say you're sorry!");
		this.reply("How are you?", "Say you're sorry!");
		this.reply("Sorry!", "It's ok!");
		this.reply("hello", "Hi there!");
		this.reply("How are you?", "Catch-all.");
	}

	@Test
	public void testInheritance() {
		this.setUp("inheritance.rive");

		String RS_ERR_MATCH = "ERR: No Reply Matched";

		this.uservar("topic", "colors");
		this.reply("What color is the sky?", "Blue.");
		this.reply("What color is the sun?", "Yellow.");
		this.reply("What color is grass?", RS_ERR_MATCH);
		this.reply("Name a Red Hat distro.", RS_ERR_MATCH);
		this.reply("Name a Debian distro.", RS_ERR_MATCH);
		this.reply("Say stuff.", RS_ERR_MATCH);

		this.uservar("topic", "linux");
		this.reply("What color is the sky?", RS_ERR_MATCH);
		this.reply("What color is the sun?", RS_ERR_MATCH);
		this.reply("What color is grass?", RS_ERR_MATCH);
		this.reply("Name a Red Hat distro.", "Fedora.");
		this.reply("Name a Debian distro.", "Ubuntu.");
		this.reply("Say stuff.", RS_ERR_MATCH);

		this.uservar("topic", "stuff");
		this.reply("What color is the sky?", "Blue.");
		this.reply("What color is the sun?", "Yellow.");
		this.reply("What color is grass?", RS_ERR_MATCH);
		this.reply("Name a Red Hat distro.", "Fedora.");
		this.reply("Name a Debian distro.", "Ubuntu.");
		this.reply("Say stuff.", "\"Stuff.\"");

		this.uservar("topic", "override");
		this.reply("What color is the sky?", "Blue.");
		this.reply("What color is the sun?", "Purple.");
		this.reply("What color is grass?", RS_ERR_MATCH);
		this.reply("Name a Red Hat distro.", RS_ERR_MATCH);
		this.reply("Name a Debian distro.", RS_ERR_MATCH);
		this.reply("Say stuff.", RS_ERR_MATCH);

		this.uservar("topic", "morecolors");
		this.reply("What color is the sky?", "Blue.");
		this.reply("What color is the sun?", "Yellow.");
		this.reply("What color is grass?", "Green.");
		this.reply("Name a Red Hat distro.", RS_ERR_MATCH);
		this.reply("Name a Debian distro.", RS_ERR_MATCH);
		this.reply("Say stuff.", RS_ERR_MATCH);

		this.uservar("topic", "evenmore");
		this.reply("What color is the sky?", "Blue.");
		this.reply("What color is the sun?", "Yellow.");
		this.reply("What color is grass?", "Blue, sometimes.");
		this.reply("Name a Red Hat distro.", RS_ERR_MATCH);
		this.reply("Name a Debian distro.", RS_ERR_MATCH);
		this.reply("Say stuff.", RS_ERR_MATCH);
	}
}
