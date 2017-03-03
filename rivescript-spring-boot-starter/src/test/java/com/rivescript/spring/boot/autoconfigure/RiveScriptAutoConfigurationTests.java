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

package com.rivescript.spring.boot.autoconfigure;

import com.rivescript.RiveScript;
import com.rivescript.lang.groovy.GroovyHandler;
import com.rivescript.lang.javascript.JavaScriptHandler;
import com.rivescript.lang.ruby.RubyHandler;
import com.rivescript.macro.ObjectHandler;
import com.rivescript.macro.Subroutine;
import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.test.util.EnvironmentTestUtils;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

import static com.rivescript.Config.DEFAULT_DEPTH;
import static com.rivescript.RiveScript.CANNOT_DIVIDE_BY_ZERO_KEY;
import static com.rivescript.RiveScript.CANNOT_MATH_VALUE_KEY;
import static com.rivescript.RiveScript.CANNOT_MATH_VARIABLE_KEY;
import static com.rivescript.RiveScript.DEEP_RECURSION_KEY;
import static com.rivescript.RiveScript.DEFAULT_CANNOT_DIVIDE_BY_ZERO_MESSAGE;
import static com.rivescript.RiveScript.DEFAULT_CANNOT_MATH_VALUE_MESSAGE;
import static com.rivescript.RiveScript.DEFAULT_CANNOT_MATH_VARIABLE_MESSAGE;
import static com.rivescript.RiveScript.DEFAULT_DEEP_RECURSION_MESSAGE;
import static com.rivescript.RiveScript.DEFAULT_DEFAULT_TOPIC_NOT_FOUND_MESSAGE;
import static com.rivescript.RiveScript.DEFAULT_FILE_EXTENSIONS;
import static com.rivescript.RiveScript.DEFAULT_OBJECT_NOT_FOUND_MESSAGE;
import static com.rivescript.RiveScript.DEFAULT_REPLIES_NOT_SORTED_MESSAGE;
import static com.rivescript.RiveScript.DEFAULT_REPLY_NOT_FOUND_MESSAGE;
import static com.rivescript.RiveScript.DEFAULT_REPLY_NOT_MATCHED_MESSAGE;
import static com.rivescript.RiveScript.DEFAULT_TOPIC_NOT_FOUND_KEY;
import static com.rivescript.RiveScript.OBJECT_NOT_FOUND_KEY;
import static com.rivescript.RiveScript.REPLIES_NOT_SORTED_KEY;
import static com.rivescript.RiveScript.REPLY_NOT_FOUND_KEY;
import static com.rivescript.RiveScript.REPLY_NOT_MATCHED_KEY;
import static com.rivescript.spring.boot.autoconfigure.RiveScriptProperties.DEFAULT_SOURCE_PATH;
import static org.hamcrest.Matchers.arrayWithSize;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasEntry;
import static org.hamcrest.Matchers.hasItemInArray;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

/**
 * Tests for {@link RiveScriptAutoConfiguration}.
 *
 * @author Marcel Overdijk
 */
public class RiveScriptAutoConfigurationTests {

	private AnnotationConfigApplicationContext context;

	@Before
	public void setUp() {
		this.context = new AnnotationConfigApplicationContext();
	}

	@Test
	public void testDefaultAutoConfiguration() {
		load(new Class[] {RiveScriptAutoConfiguration.class});

		RiveScriptProperties rsPRoperties = this.context.getBean(RiveScriptProperties.class);
		RiveScript rs = this.context.getBean(RiveScript.class);

		assertThat(rsPRoperties.getSourcePath(), is(equalTo(DEFAULT_SOURCE_PATH)));
		assertThat(rsPRoperties.getFileExtensions(), is(equalTo(DEFAULT_FILE_EXTENSIONS)));

		assertThat(rs, is(not(nullValue())));
		assertThat(rs.isThrowExceptions(), is(false));
		assertThat(rs.isStrict(), is(true));
		assertThat(rs.isUtf8(), is(false));
		assertThat(rs.getUnicodePunctuation(), is(equalTo("[.,!?;:]")));
		assertThat(rs.isForceCase(), is(false));
		assertThat(rs.getDepth(), is(equalTo(DEFAULT_DEPTH)));
		assertThat(rs.getErrorMessages(), hasEntry(DEEP_RECURSION_KEY, DEFAULT_DEEP_RECURSION_MESSAGE));
		assertThat(rs.getErrorMessages(), hasEntry(REPLIES_NOT_SORTED_KEY, DEFAULT_REPLIES_NOT_SORTED_MESSAGE));
		assertThat(rs.getErrorMessages(), hasEntry(DEFAULT_TOPIC_NOT_FOUND_KEY, DEFAULT_DEFAULT_TOPIC_NOT_FOUND_MESSAGE));
		assertThat(rs.getErrorMessages(), hasEntry(REPLY_NOT_MATCHED_KEY, DEFAULT_REPLY_NOT_MATCHED_MESSAGE));
		assertThat(rs.getErrorMessages(), hasEntry(REPLY_NOT_FOUND_KEY, DEFAULT_REPLY_NOT_FOUND_MESSAGE));
		assertThat(rs.getErrorMessages(), hasEntry(OBJECT_NOT_FOUND_KEY, DEFAULT_OBJECT_NOT_FOUND_MESSAGE));
		assertThat(rs.getErrorMessages(), hasEntry(CANNOT_DIVIDE_BY_ZERO_KEY, DEFAULT_CANNOT_DIVIDE_BY_ZERO_MESSAGE));
		assertThat(rs.getErrorMessages(), hasEntry(CANNOT_MATH_VARIABLE_KEY, DEFAULT_CANNOT_MATH_VARIABLE_MESSAGE));
		assertThat(rs.getErrorMessages(), hasEntry(CANNOT_MATH_VALUE_KEY, DEFAULT_CANNOT_MATH_VALUE_MESSAGE));
		assertThat(rs.getHandlers().size(), is(equalTo(0)));
		assertThat(rs.getSubroutines().size(), is(equalTo(0)));

		assertThat(rs.reply("local-user", "hello"), is(equalTo("Hi there!")));
	}

	@Test
	public void testDisabled() {
		load(new Class[] {RiveScriptAutoConfiguration.class},
				"rivescript.enabled: false");

		assertThat(this.context.getBeansOfType(RiveScript.class).size(), is(0));
	}

	@Test
	public void testCustomSourcePath() {
		load(new Class[] {RiveScriptAutoConfiguration.class},
				"rivescript.source-path: classpath:/custom-path/");

		RiveScriptProperties rsPRoperties = this.context.getBean(RiveScriptProperties.class);

		assertThat(rsPRoperties.getSourcePath(), is(equalTo("classpath:/custom-path/")));
	}

	@Test
	public void testCustomFileExtension() {
		load(new Class[] {RiveScriptAutoConfiguration.class},
				"rivescript.file-extensions: .customrs");

		RiveScriptProperties rsPRoperties = this.context.getBean(RiveScriptProperties.class);

		assertThat(rsPRoperties.getFileExtensions(), arrayWithSize(1));
		assertThat(rsPRoperties.getFileExtensions(), hasItemInArray(equalTo(".customrs")));
	}

	@Test
	public void testCustomFileExtensions() {
		load(new Class[] {RiveScriptAutoConfiguration.class},
				"rivescript.file-extensions: .customrs1, .customrs2 ,  .customrs3");

		RiveScriptProperties rsPRoperties = this.context.getBean(RiveScriptProperties.class);

		assertThat(rsPRoperties.getFileExtensions(), arrayWithSize(3));
		assertThat(rsPRoperties.getFileExtensions(), hasItemInArray(equalTo(".customrs1")));
		assertThat(rsPRoperties.getFileExtensions(), hasItemInArray(equalTo(".customrs2")));
		assertThat(rsPRoperties.getFileExtensions(), hasItemInArray(equalTo(".customrs3")));
	}

	@Test
	public void testThrowExceptionsFalse() {
		load(new Class[] {RiveScriptAutoConfiguration.class},
				"rivescript.throw-exceptions: false");

		RiveScript rs = this.context.getBean(RiveScript.class);

		assertThat(rs.isThrowExceptions(), is(false));
	}

	@Test
	public void testThrowExceptionsTrue() {
		load(new Class[] {RiveScriptAutoConfiguration.class},
				"rivescript.throw-exceptions: true");

		RiveScript rs = this.context.getBean(RiveScript.class);

		assertThat(rs.isThrowExceptions(), is(true));
	}

	@Test
	public void testStrictFalse() {
		load(new Class[] {RiveScriptAutoConfiguration.class},
				"rivescript.strict: false");

		RiveScript rs = this.context.getBean(RiveScript.class);

		assertThat(rs.isStrict(), is(false));
	}

	@Test
	public void testStrictTrue() {
		load(new Class[] {RiveScriptAutoConfiguration.class},
				"rivescript.strict: true");

		RiveScript rs = this.context.getBean(RiveScript.class);

		assertThat(rs.isStrict(), is(true));
	}

	@Test
	public void testUtf8False() {
		load(new Class[] {RiveScriptAutoConfiguration.class},
				"rivescript.utf8: false");

		RiveScript rs = this.context.getBean(RiveScript.class);

		assertThat(rs.isUtf8(), is(false));
	}

	@Test
	public void testUtf8True() {
		load(new Class[] {RiveScriptAutoConfiguration.class},
				"rivescript.utf8: true");

		RiveScript rs = this.context.getBean(RiveScript.class);

		assertThat(rs.isUtf8(), is(true));
	}

	@Test
	public void testCustomUnicodePunctuation() {
		load(new Class[] {RiveScriptAutoConfiguration.class},
				"rivescript.unicode-punctuation: foobar");

		RiveScript rs = this.context.getBean(RiveScript.class);

		assertThat(rs.getUnicodePunctuation(), is(equalTo("foobar")));
	}

	@Test
	public void testForceCaseFalse() {
		load(new Class[] {RiveScriptAutoConfiguration.class},
				"rivescript.force-case: false");

		RiveScript rs = this.context.getBean(RiveScript.class);

		assertThat(rs.isForceCase(), is(false));
	}

	@Test
	public void testForceCaseTrue() {
		load(new Class[] {RiveScriptAutoConfiguration.class},
				"rivescript.force-case: true");

		RiveScript rs = this.context.getBean(RiveScript.class);

		assertThat(rs.isForceCase(), is(true));
	}

	@Test
	public void testCustomDepth() {
		load(new Class[] {RiveScriptAutoConfiguration.class},
				"rivescript.depth: 10");

		RiveScript rs = this.context.getBean(RiveScript.class);

		assertThat(rs.getDepth(), is(equalTo(10)));
	}

	@Test
	public void testCustomErrorMessag() {
		load(new Class[] {RiveScriptAutoConfiguration.class},
				"rivescript.error-messages.deepRecursion: Custom ERR: Deep Recursion Detected");

		RiveScript rs = this.context.getBean(RiveScript.class);

		assertThat(rs.getErrorMessages(), hasEntry(DEEP_RECURSION_KEY, "Custom ERR: Deep Recursion Detected"));
		assertThat(rs.getErrorMessages(), hasEntry(REPLIES_NOT_SORTED_KEY, DEFAULT_REPLIES_NOT_SORTED_MESSAGE));
		assertThat(rs.getErrorMessages(), hasEntry(DEFAULT_TOPIC_NOT_FOUND_KEY, DEFAULT_DEFAULT_TOPIC_NOT_FOUND_MESSAGE));
		assertThat(rs.getErrorMessages(), hasEntry(REPLY_NOT_MATCHED_KEY, DEFAULT_REPLY_NOT_MATCHED_MESSAGE));
		assertThat(rs.getErrorMessages(), hasEntry(REPLY_NOT_FOUND_KEY, DEFAULT_REPLY_NOT_FOUND_MESSAGE));
		assertThat(rs.getErrorMessages(), hasEntry(OBJECT_NOT_FOUND_KEY, DEFAULT_OBJECT_NOT_FOUND_MESSAGE));
		assertThat(rs.getErrorMessages(), hasEntry(CANNOT_DIVIDE_BY_ZERO_KEY, DEFAULT_CANNOT_DIVIDE_BY_ZERO_MESSAGE));
		assertThat(rs.getErrorMessages(), hasEntry(CANNOT_MATH_VARIABLE_KEY, DEFAULT_CANNOT_MATH_VARIABLE_MESSAGE));
		assertThat(rs.getErrorMessages(), hasEntry(CANNOT_MATH_VALUE_KEY, DEFAULT_CANNOT_MATH_VALUE_MESSAGE));
	}

	@Test
	public void testCustomErrorMessage() {
		load(new Class[] {RiveScriptAutoConfiguration.class},
				"rivescript.error-messages.deepRecursion: Custom ERR: Deep Recursion Detected",
				"rivescript.error-messages.repliesNotSorted: Custom ERR: Replies Not Sorted",
				"rivescript.error-messages.defaultTopicNotFound: Custom ERR: No default topic 'random' was found",
				"rivescript.error-messages.replyNotMatched: Custom ERR: No Reply Matched",
				"rivescript.error-messages.replyNotFound: Custom ERR: No Reply Matched",
				"rivescript.error-messages.objectNotFound: Custom [ERR: Object Not Found]",
				"rivescript.error-messages.cannotDivideByZero: Custom [ERR: Can't Divide By Zero]",
				"rivescript.error-messages.cannotMathVariable: Custom [ERR: Can't perform math operation on non-numeric variable]",
				"rivescript.error-messages.cannotMathValue: Custom [ERR: Can't perform math operation on non-numeric value]");

		RiveScript rs = this.context.getBean(RiveScript.class);

		assertThat(rs.getErrorMessages(), hasEntry(DEEP_RECURSION_KEY, "Custom ERR: Deep Recursion Detected"));
		assertThat(rs.getErrorMessages(), hasEntry(REPLIES_NOT_SORTED_KEY, "Custom ERR: Replies Not Sorted"));
		assertThat(rs.getErrorMessages(), hasEntry(DEFAULT_TOPIC_NOT_FOUND_KEY, "Custom ERR: No default topic 'random' was found"));
		assertThat(rs.getErrorMessages(), hasEntry(REPLY_NOT_MATCHED_KEY, "Custom ERR: No Reply Matched"));
		assertThat(rs.getErrorMessages(), hasEntry(REPLY_NOT_FOUND_KEY, "Custom ERR: No Reply Matched"));
		assertThat(rs.getErrorMessages(), hasEntry(OBJECT_NOT_FOUND_KEY, "Custom [ERR: Object Not Found]"));
		assertThat(rs.getErrorMessages(), hasEntry(CANNOT_DIVIDE_BY_ZERO_KEY, "Custom [ERR: Can't Divide By Zero]"));
		assertThat(rs.getErrorMessages(),
				hasEntry(CANNOT_MATH_VARIABLE_KEY, "Custom [ERR: Can't perform math operation on non-numeric variable]"));
		assertThat(rs.getErrorMessages(),
				hasEntry(CANNOT_MATH_VALUE_KEY, "Custom [ERR: Can't perform math operation on non-numeric value]"));
	}

	@Test
	public void testObjectHandler() {
		load(new Class[] {RiveScriptAutoConfiguration.class},
				"rivescript.object-handlers: javascript");

		RiveScript rs = this.context.getBean(RiveScript.class);

		assertThat(rs.getHandlers().size(), is(equalTo(1)));
		assertThat(rs.getHandlers(), hasEntry(equalTo("javascript"), instanceOf(JavaScriptHandler.class)));
	}

	@Test
	public void testObjectHandlers() {
		load(new Class[] {RiveScriptAutoConfiguration.class},
				"rivescript.object-handlers: groovy, javascript ,  ruby");

		RiveScript rs = this.context.getBean(RiveScript.class);

		assertThat(rs.getHandlers().size(), is(equalTo(3)));
		assertThat(rs.getHandlers(), hasEntry(equalTo("groovy"), instanceOf(GroovyHandler.class)));
		assertThat(rs.getHandlers(), hasEntry(equalTo("javascript"), instanceOf(JavaScriptHandler.class)));
		assertThat(rs.getHandlers(), hasEntry(equalTo("ruby"), instanceOf(RubyHandler.class)));
	}

	@Test
	public void testCustomObjectHandlers() {
		load(new Class[] {RiveScriptAutoConfiguration.class, CustomObjectHandlersConfig.class});

		RiveScript rs = this.context.getBean(RiveScript.class);

		assertThat(rs.getHandlers().size(), is(equalTo(2)));
		assertThat(rs.getHandlers(), hasEntry(equalTo("handler1"), instanceOf(ObjectHandler1.class)));
		assertThat(rs.getHandlers(), hasEntry(equalTo("handler2"), instanceOf(ObjectHandler2.class)));
	}

	@Test
	public void testMixedObjectHandlers() {
		load(new Class[] {RiveScriptAutoConfiguration.class, CustomObjectHandlersConfig.class},
				"rivescript.object-handlers: groovy, javascript");

		RiveScript rs = this.context.getBean(RiveScript.class);

		assertThat(rs.getHandlers().size(), is(equalTo(4)));
		assertThat(rs.getHandlers(), hasEntry(equalTo("groovy"), instanceOf(GroovyHandler.class)));
		assertThat(rs.getHandlers(), hasEntry(equalTo("javascript"), instanceOf(JavaScriptHandler.class)));
		assertThat(rs.getHandlers(), hasEntry(equalTo("handler1"), instanceOf(ObjectHandler1.class)));
		assertThat(rs.getHandlers(), hasEntry(equalTo("handler2"), instanceOf(ObjectHandler2.class)));
	}

	@Test
	public void testSubroutines() {
		load(new Class[] {RiveScriptAutoConfiguration.class, SubroutinesConfig.class});

		RiveScript rs = this.context.getBean(RiveScript.class);

		assertThat(rs.getSubroutines().size(), is(equalTo(2)));
		assertThat(rs.getSubroutines(), hasEntry(equalTo("subroutine1"), instanceOf(Subroutine1.class)));
		assertThat(rs.getSubroutines(), hasEntry(equalTo("subroutine2"), instanceOf(Subroutine2.class)));
	}

	private void load(Class<?>[] configs, String... environment) {
		this.context.register(configs);
		EnvironmentTestUtils.addEnvironment(this.context, environment);
		this.context.refresh();
	}

	@Configuration
	protected static class CustomObjectHandlersConfig {

		@Bean
		public Map<String, ObjectHandler> objectHandlers() {
			Map<String, ObjectHandler> objectHandlers = new HashMap<>();
			objectHandlers.put("handler1", new ObjectHandler1());
			objectHandlers.put("handler2", new ObjectHandler2());
			return objectHandlers;
		}
	}

	private static class ObjectHandler1 implements ObjectHandler {

		@Override
		public void load(RiveScript rs, String name, String[] code) {
		}

		@Override
		public String call(RiveScript rs, String name, String[] fields) {
			return null;
		}
	}

	private static class ObjectHandler2 implements ObjectHandler {

		@Override
		public void load(RiveScript rs, String name, String[] code) {
		}

		@Override
		public String call(RiveScript rs, String name, String[] fields) {
			return null;
		}
	}

	@Configuration
	protected static class SubroutinesConfig {

		@Bean
		public Map<String, Subroutine> subroutines() {
			Map<String, Subroutine> subroutines = new HashMap<>();
			subroutines.put("subroutine1", new Subroutine1());
			subroutines.put("subroutine2", new Subroutine2());
			return subroutines;
		}
	}

	private static class Subroutine1 implements Subroutine {

		@Override
		public String call(RiveScript rs, String[] args) {
			return null;
		}
	}

	private static class Subroutine2 implements Subroutine {

		@Override
		public String call(RiveScript rs, String[] args) {
			return null;
		}
	}
}
