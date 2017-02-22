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

package com.rivescript.util;

import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

/**
 * Tests for {@link StringUtils}.
 *
 * @author Marcel Overdijk
 */
public class StringUtilTests {

	@Test
	public void testCountWords() {
		assertThat(StringUtils.countWords("Hi", true), is(equalTo(1)));
		assertThat(StringUtils.countWords("Hi there", true), is(equalTo(2)));
		assertThat(StringUtils.countWords("Hi  there", true), is(equalTo(2)));
		assertThat(StringUtils.countWords("The quick brown fox jumps over the lazy dog", true), is(equalTo(9)));
		assertThat(StringUtils.countWords("The * quick # brown _ fox | jumps over the lazy dog", true), is(equalTo(13)));
		assertThat(StringUtils.countWords("The * quick # brown _ fox | jumps over the lazy dog", false), is(equalTo(9)));
	}

	@Test
	public void testJoin() {
		assertThat(StringUtils.join(new String[] {"a"}, ","), is(equalTo("a")));
		assertThat(StringUtils.join(new String[] {"a", "b"}, ","), is(equalTo("a,b")));
		assertThat(StringUtils.join(new String[] {"a", "b", "c"}, ","), is(equalTo("a,b,c")));
		assertThat(StringUtils.join(new String[] {"foo", "bar"}, ""), is(equalTo("foobar")));
		assertThat(StringUtils.join(new String[] {}, ","), is(equalTo("")));
		assertThat(StringUtils.join(new String[] {}, ""), is(equalTo("")));
		assertThat(StringUtils.join(null, ","), is(equalTo(null)));
	}

	@Test
	public void testQuoteMetacharacters() {
		assertThat(StringUtils.quoteMetacharacters("foo"), is(equalTo("foo")));
		assertThat(StringUtils.quoteMetacharacters("foo bar"), is(equalTo("foo bar")));
		assertThat(StringUtils.quoteMetacharacters("\\"), is(equalTo("\\\\")));
		assertThat(StringUtils.quoteMetacharacters("."), is(equalTo("\\.")));
		assertThat(StringUtils.quoteMetacharacters("+"), is(equalTo("\\+")));
		assertThat(StringUtils.quoteMetacharacters("*"), is(equalTo("\\*")));
		assertThat(StringUtils.quoteMetacharacters("?"), is(equalTo("\\?")));
		assertThat(StringUtils.quoteMetacharacters("["), is(equalTo("\\[")));
		assertThat(StringUtils.quoteMetacharacters("^"), is(equalTo("\\^")));
		assertThat(StringUtils.quoteMetacharacters("]"), is(equalTo("\\]")));
		assertThat(StringUtils.quoteMetacharacters("$"), is(equalTo("\\$")));
		assertThat(StringUtils.quoteMetacharacters("("), is(equalTo("\\(")));
		assertThat(StringUtils.quoteMetacharacters(")"), is(equalTo("\\)")));
		assertThat(StringUtils.quoteMetacharacters("{"), is(equalTo("\\{")));
		assertThat(StringUtils.quoteMetacharacters("}"), is(equalTo("\\}")));
		assertThat(StringUtils.quoteMetacharacters("="), is(equalTo("\\=")));
		assertThat(StringUtils.quoteMetacharacters("!"), is(equalTo("\\!")));
		assertThat(StringUtils.quoteMetacharacters("<"), is(equalTo("\\<")));
		assertThat(StringUtils.quoteMetacharacters(">"), is(equalTo("\\>")));
		assertThat(StringUtils.quoteMetacharacters("|"), is(equalTo("\\|")));
		assertThat(StringUtils.quoteMetacharacters(":"), is(equalTo("\\:")));
		assertThat(StringUtils.quoteMetacharacters("foo bar \\.+*?[^]$(){}=!<>|:"),
				is(equalTo("foo bar \\\\\\.\\+\\*\\?\\[\\^\\]\\$\\(\\)\\{\\}\\=\\!\\<\\>\\|\\:")));
		assertThat(StringUtils.quoteMetacharacters(""), is(equalTo("")));
		assertThat(StringUtils.quoteMetacharacters(null), is(equalTo(null)));
	}

	@Test
	public void testStripNasties() {
		assertThat(StringUtils.stripNasties("foo"), is(equalTo("foo")));
		assertThat(StringUtils.stripNasties("foo bar"), is(equalTo("foo bar")));
		assertThat(StringUtils.stripNasties("foo _ bar"), is(equalTo("foo _ bar")));
		assertThat(StringUtils.stripNasties("!@#$%^&*(){}:;\"'|\\<>,.?/"), is(equalTo("")));
		assertThat(StringUtils.stripNasties(""), is(equalTo("")));
		assertThat(StringUtils.stripNasties(null), is(equalTo(null)));
	}
}
