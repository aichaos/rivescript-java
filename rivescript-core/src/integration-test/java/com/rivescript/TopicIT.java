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

import static com.rivescript.RiveScript.DEFAULT_REPLY_NOT_MATCHED_MESSAGE;

/**
 * @author Noah Petherbridge
 * @author Marcel Overdijk
 */
public class TopicIT extends BaseIT {

	@Test
	public void testPunishmentTopic() {
		rs = new RiveScript();
		setUp(new String[] {
				"+ hello",
				"- Hi there!",
				"",
				"+ swear word",
				"- How rude! Apologize or I won't talk to you again.{topic=sorry}",
				"",
				"+ *",
				"- Catch-all.",
				"",
				"> topic sorry",
				"    + sorry",
				"    - It's ok!{topic=random}",
				"",
				"    + *",
				"    - Say you're sorry!",
				"< topic"
		});
		assertReply("hello", "Hi there!");
		assertReply("How are you?", "Catch-all.");
		assertReply("Swear word!", "How rude! Apologize or I won't talk to you again.");
		assertReply("hello", "Say you're sorry!");
		assertReply("How are you?", "Say you're sorry!");
		assertReply("Sorry!", "It's ok!");
		assertReply("hello", "Hi there!");
		assertReply("How are you?", "Catch-all.");
	}

	@Test
	public void testTopicInheritance() {
		rs = new RiveScript();
		setUp(new String[] {
				"> topic colors",
				"+ what color is the sky",
				"- Blue.",
				"+ what color is the sun",
				"- Yellow.",
				"< topic",
				"",
				"> topic linux",
				"+ name a red hat distro",
				"- Fedora.",
				"+ name a debian distro",
				"- Ubuntu.",
				"< topic",
				"",
				"> topic stuff includes colors linux",
				"+ say stuff",
				"- \"Stuff.\"",
				"< topic",
				"",
				"> topic override inherits colors",
				"+ what color is the sun",
				"- Purple.",
				"< topic",
				"",
				"> topic morecolors includes colors",
				"+ what color is grass",
				"- Green.",
				"< topic",
				"",
				"> topic evenmore inherits morecolors",
				"+ what color is grass",
				"- Blue, sometimes.",
				"< topic"
		});
		setUservar("topic", "colors");
		assertReply("What color is the sky?", "Blue.");
		assertReply("What color is the sun?", "Yellow.");
		assertReply("What color is grass?", DEFAULT_REPLY_NOT_MATCHED_MESSAGE);
		assertReply("Name a Red Hat distro.", DEFAULT_REPLY_NOT_MATCHED_MESSAGE);
		assertReply("Name a Debian distro.", DEFAULT_REPLY_NOT_MATCHED_MESSAGE);
		assertReply("Say stuff.", DEFAULT_REPLY_NOT_MATCHED_MESSAGE);

		setUservar("topic", "linux");
		assertReply("What color is the sky?", DEFAULT_REPLY_NOT_MATCHED_MESSAGE);
		assertReply("What color is the sun?", DEFAULT_REPLY_NOT_MATCHED_MESSAGE);
		assertReply("What color is grass?", DEFAULT_REPLY_NOT_MATCHED_MESSAGE);
		assertReply("Name a Red Hat distro.", "Fedora.");
		assertReply("Name a Debian distro.", "Ubuntu.");
		assertReply("Say stuff.", DEFAULT_REPLY_NOT_MATCHED_MESSAGE);

		setUservar("topic", "stuff");
		assertReply("What color is the sky?", "Blue.");
		assertReply("What color is the sun?", "Yellow.");
		assertReply("What color is grass?", DEFAULT_REPLY_NOT_MATCHED_MESSAGE);
		assertReply("Name a Red Hat distro.", "Fedora.");
		assertReply("Name a Debian distro.", "Ubuntu.");
		assertReply("Say stuff.", "\"Stuff.\"");

		setUservar("topic", "override");
		assertReply("What color is the sky?", "Blue.");
		assertReply("What color is the sun?", "Purple.");
		assertReply("What color is grass?", DEFAULT_REPLY_NOT_MATCHED_MESSAGE);
		assertReply("Name a Red Hat distro.", DEFAULT_REPLY_NOT_MATCHED_MESSAGE);
		assertReply("Name a Debian distro.", DEFAULT_REPLY_NOT_MATCHED_MESSAGE);
		assertReply("Say stuff.", DEFAULT_REPLY_NOT_MATCHED_MESSAGE);

		setUservar("topic", "morecolors");
		assertReply("What color is the sky?", "Blue.");
		assertReply("What color is the sun?", "Yellow.");
		assertReply("What color is grass?", "Green.");
		assertReply("Name a Red Hat distro.", DEFAULT_REPLY_NOT_MATCHED_MESSAGE);
		assertReply("Name a Debian distro.", DEFAULT_REPLY_NOT_MATCHED_MESSAGE);
		assertReply("Say stuff.", DEFAULT_REPLY_NOT_MATCHED_MESSAGE);

		setUservar("topic", "evenmore");
		assertReply("What color is the sky?", "Blue.");
		assertReply("What color is the sun?", "Yellow.");
		assertReply("What color is grass?", "Blue, sometimes.");
		assertReply("Name a Red Hat distro.", DEFAULT_REPLY_NOT_MATCHED_MESSAGE);
		assertReply("Name a Debian distro.", DEFAULT_REPLY_NOT_MATCHED_MESSAGE);
		assertReply("Say stuff.", DEFAULT_REPLY_NOT_MATCHED_MESSAGE);
	}
}
