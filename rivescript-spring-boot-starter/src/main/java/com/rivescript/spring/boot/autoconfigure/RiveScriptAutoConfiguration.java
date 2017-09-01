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

import com.rivescript.Config;
import com.rivescript.RiveScript;
import com.rivescript.lang.groovy.GroovyHandler;
import com.rivescript.lang.javascript.JavaScriptHandler;
import com.rivescript.lang.ruby.RubyHandler;
import com.rivescript.macro.ObjectHandler;
import com.rivescript.macro.Subroutine;
import com.rivescript.session.SessionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import java.io.File;
import java.io.IOException;
import java.util.Map;

/**
 * {@link EnableAutoConfiguration Auto-configuration} for RiveScript.
 *
 * @author Marcel Overdijk
 */
@Configuration
@ConditionalOnProperty(prefix = "rivescript", name = "enabled", matchIfMissing = true)
@EnableConfigurationProperties(RiveScriptProperties.class)
public class RiveScriptAutoConfiguration {

	private static Logger logger = LoggerFactory.getLogger(RiveScriptAutoConfiguration.class);

	@Configuration
	@ConditionalOnMissingBean(RiveScript.class)
	protected static class RiveScriptConfiguration {

		@Autowired(required = false)
		private SessionManager sessionManager;

		@Autowired(required = false)
		private Map<String, ObjectHandler> objectHandlers;

		@Autowired(required = false)
		private Map<String, Subroutine> subroutines;

		@Autowired
		private ResourceLoader resourceLoader = new DefaultResourceLoader();

		@Autowired
		private RiveScriptProperties properties;

		@Bean
		public RiveScript riveScript() throws IOException {
			// Create the RiveScript instance.
			Config config = Config.newBuilder()
					.throwExceptions(properties.isThrowExceptions())
					.strict(properties.isStrict())
					.utf8(properties.isUtf8())
					.unicodePunctuation(properties.getUnicodePunctuation())
					.forceCase(properties.isForceCase())
					.concat(properties.getConcat())
					.depth(properties.getDepth())
					.sessionManager(sessionManager)
					.errorMessages(properties.getErrorMessages())
					.build();
			RiveScript rs = new RiveScript(config);

			// Register object handlers via application properties.
			if (properties.getObjectHandlers() != null && properties.getObjectHandlers().trim().length() > 0) {
				for (String objectHandler : properties.getObjectHandlers().split(",")) {
					objectHandler = objectHandler.trim();
					switch (objectHandler) {
						case "groovy":
							rs.setHandler("groovy", new GroovyHandler());
							break;
						case "javascript":
							rs.setHandler("javascript", new JavaScriptHandler());
							break;
						case "ruby":
							rs.setHandler("ruby", new RubyHandler());
							break;
						default:
							logger.warn("Object handler name not supported: " + objectHandler);
					}
				}
			}

			// Register object handlers via Map<String, ObjectHandler> bean.
			if (objectHandlers != null && objectHandlers.size() > 0) {
				for (Map.Entry<String, ObjectHandler> entry : objectHandlers.entrySet()) {
					rs.setHandler(entry.getKey(), entry.getValue());
				}
			}

			// Register subroutines via Map<String, Subroutine> bean.
			if (subroutines != null && subroutines.size() > 0) {
				for (Map.Entry<String, Subroutine> entry : subroutines.entrySet()) {
					rs.setSubroutine(entry.getKey(), entry.getValue());
				}
			}

			// Load RiveScript documents.
			for (String path : properties.getSourcePath().split(",")) {
				Resource resource = this.resourceLoader.getResource(path);
				if (resource.exists()) {
					File file = resource.getFile();
					if (file.isDirectory()) {
						rs.loadDirectory(file, properties.getFileExtensions());
					} else {
						rs.loadFile(file);
					}
				} else {
					logger.warn("Cannot find source path: " + path);
				}
			}

			// Sort replies and return the instance.
			rs.sortReplies();
			return rs;
		}
	}
}
