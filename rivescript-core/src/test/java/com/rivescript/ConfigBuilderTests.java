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

import com.rivescript.session.ConcurrentHashMapSessionManager;
import com.rivescript.session.SessionManager;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static com.rivescript.Config.DEFAULT_DEPTH;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

/**
 * Tests for {@link Config.Builder}.
 *
 * @author Marcel Overdijk
 */
public class ConfigBuilderTests {

	@Test
	public void testBasicBuilder() {
		Config config = Config.Builder.basic().build();
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
	public void testUtf8Builder() {
		Config config = Config.Builder.utf8().build();
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
	public void testBuildWithThrowExceptionsIsFalse() {
		Config config = Config.newBuilder().throwExceptions(false).build();
		assertThat(config.isThrowExceptions(), is(equalTo(false)));
	}

	@Test
	public void testBuildWithThrowExceptionsIstrue() {
		Config config = Config.newBuilder().throwExceptions(true).build();
		assertThat(config.isThrowExceptions(), is(equalTo(true)));
	}

	@Test
	public void testBuildWithStrictIsFalse() {
		Config config = Config.newBuilder().strict(false).build();
		assertThat(config.isStrict(), is(equalTo(false)));
	}

	@Test
	public void testBuildWithStrictIstrue() {
		Config config = Config.newBuilder().strict(true).build();
		assertThat(config.isStrict(), is(equalTo(true)));
	}

	@Test
	public void testBuildWithUtf8IsFalse() {
		Config config = Config.newBuilder().utf8(false).build();
		assertThat(config.isUtf8(), is(equalTo(false)));
	}

	@Test
	public void testBuildWithUtf8Istrue() {
		Config config = Config.newBuilder().utf8(true).build();
		assertThat(config.isUtf8(), is(equalTo(true)));
	}

	@Test
	public void testBuildWithUnicodePunctuation() {
		Config config = Config.newBuilder().unicodePunctuation("foobar").build();
		assertThat(config.getUnicodePunctuation(), is(equalTo("foobar")));
	}

	@Test
	public void testBuildWithForceCaseIsFalse() {
		Config config = Config.newBuilder().forceCase(false).build();
		assertThat(config.isForceCase(), is(equalTo(false)));
	}

	@Test
	public void testBuildWithForceCaseIstrue() {
		Config config = Config.newBuilder().forceCase(true).build();
		assertThat(config.isForceCase(), is(equalTo(true)));
	}

	@Test
	public void testBuildWithDepth() {
		Config config = Config.newBuilder().depth(10).build();
		assertThat(config.getDepth(), is(equalTo(10)));
	}

	@Test
	public void testBuildWithSessionManagerIsNull() {
		Config config = Config.newBuilder().sessionManager(null).build();
		assertThat(config.getSessionManager(), is(equalTo(null)));
	}

	@Test
	public void testBuildWithConcurrentHashMapSessionManager() {
		SessionManager sessionManager = new ConcurrentHashMapSessionManager();
		Config config = Config.newBuilder().sessionManager(sessionManager).build();
		assertThat(config.getSessionManager(), is(equalTo(sessionManager)));
	}

	@Test
	public void testBuildWithErrorsIsNull() {
		Config config = Config.newBuilder().errorMessages(null).build();
		assertThat(config.getErrorMessages(), is(equalTo(null)));
	}

	@Test
	public void testBuildWithCustomErrors() {
		Map<String, String> errorsMessages = new HashMap<>();
		errorsMessages.put("foo", "bar");
		Config config = Config.newBuilder().errorMessages(errorsMessages).build();
		assertThat(config.getErrorMessages(), is(equalTo(errorsMessages)));
	}

	@Test
	public void testBuildWithAddCustomError() {
		Map<String, String> errorsMessages = new HashMap<>();
		errorsMessages.put("foo", "bar");
		Config config = Config.newBuilder().addErrorMessage("foo", "bar").build();
		assertThat(config.getErrorMessages(), is(equalTo(errorsMessages)));
	}
}
