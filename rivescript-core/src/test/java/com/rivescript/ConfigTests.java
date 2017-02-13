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

import org.junit.Test;

import static com.rivescript.Config.DEFAULT_DEPTH;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

/**
 * Tests for {@link Config}.
 *
 * @author Marcel Overdijk
 */
public class ConfigTests {

	@Test
	public void testBasicConfig() {
		Config config = Config.basic();
		assertThat(config.isThrowExceptions(), is(equalTo(false)));
		assertThat(config.isStrict(), is(equalTo(true)));
		assertThat(config.isUtf8(), is(equalTo(false)));
		assertThat(config.getUnicodePunctuation(), is(equalTo("[.,!?;:]")));
		assertThat(config.isForceCase(), is(equalTo(false)));
		assertThat(config.getDepth(), is(equalTo(DEFAULT_DEPTH)));
		assertThat(config.getSessionManager(), is(equalTo(null)));
		assertThat(config.getErrorMessages(), is(equalTo(null)));
	}

	@Test
	public void testUtf8Config() {
		Config config = Config.utf8();
		assertThat(config.isThrowExceptions(), is(equalTo(false)));
		assertThat(config.isStrict(), is(equalTo(true)));
		assertThat(config.isUtf8(), is(equalTo(true)));
		assertThat(config.getUnicodePunctuation(), is(equalTo("[.,!?;:]")));
		assertThat(config.isForceCase(), is(equalTo(false)));
		assertThat(config.getDepth(), is(equalTo(DEFAULT_DEPTH)));
		assertThat(config.getSessionManager(), is(equalTo(null)));
		assertThat(config.getErrorMessages(), is(equalTo(null)));
	}

	@Test
	public void testEquals() {
		Config config1 = new Config();
		Config config2 = new Config();
		assertThat(config1, is(equalTo(config2)));
	}

	@Test
	public void testToBuilder() {
		Config config1 = new Config();
		Config.Builder builder = config1.toBuilder();
		Config config2 = builder.build();
		assertThat(config1, is(equalTo(config2)));
	}
}
