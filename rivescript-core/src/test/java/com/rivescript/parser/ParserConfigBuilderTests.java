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

package com.rivescript.parser;

import org.junit.Test;

import static com.rivescript.ConcatMode.NEWLINE;
import static com.rivescript.ConcatMode.NONE;
import static com.rivescript.ConcatMode.SPACE;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

/**
 * Tests for {@link ParserConfig.Builder}.
 *
 * @author Marcel Overdijk
 */
public class ParserConfigBuilderTests {

	@Test
	public void testBuildWithStrictIsFalse() {
		ParserConfig config = ParserConfig.newBuilder().strict(false).build();
		assertThat(config.isStrict(), is(equalTo(false)));
	}

	@Test
	public void testBuildWithStrictIsTrue() {
		ParserConfig config = ParserConfig.newBuilder().strict(true).build();
		assertThat(config.isStrict(), is(equalTo(true)));
	}

	@Test
	public void testBuildWithUtf8IsFalse() {
		ParserConfig config = ParserConfig.newBuilder().utf8(false).build();
		assertThat(config.isUtf8(), is(equalTo(false)));
	}

	@Test
	public void testBuildWithUtf8IsTrue() {
		ParserConfig config = ParserConfig.newBuilder().utf8(true).build();
		assertThat(config.isUtf8(), is(equalTo(true)));
	}

	@Test
	public void testBuildWithForceCaseIsFalse() {
		ParserConfig config = ParserConfig.newBuilder().forceCase(false).build();
		assertThat(config.isForceCase(), is(equalTo(false)));
	}

	@Test
	public void testBuildWithForceCaseIsTrue() {
		ParserConfig config = ParserConfig.newBuilder().forceCase(true).build();
		assertThat(config.isForceCase(), is(equalTo(true)));
	}

	@Test
	public void testBuildWithConcatIsNone() {
		ParserConfig config = ParserConfig.newBuilder().concat(NONE).build();
		assertThat(config.getConcat(), is(equalTo(NONE)));
	}

	@Test
	public void testBuildWithConcatIsNewline() {
		ParserConfig config = ParserConfig.newBuilder().concat(NEWLINE).build();
		assertThat(config.getConcat(), is(equalTo(NEWLINE)));
	}

	@Test
	public void testBuildWithConcatIsSpace() {
		ParserConfig config = ParserConfig.newBuilder().concat(SPACE).build();
		assertThat(config.getConcat(), is(equalTo(SPACE)));
	}
}
