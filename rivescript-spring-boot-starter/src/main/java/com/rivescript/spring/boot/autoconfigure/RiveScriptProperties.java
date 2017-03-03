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

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;

import static com.rivescript.Config.DEFAULT_DEPTH;
import static com.rivescript.Config.DEFAULT_UNICODE_PUNCTUATION_PATTERN;
import static com.rivescript.RiveScript.DEFAULT_FILE_EXTENSIONS;

/**
 * {@link ConfigurationProperties Configuration properties} for RiveScript.
 *
 * @author Marcel Overdijk
 */
@ConfigurationProperties(prefix = "rivescript")
public class RiveScriptProperties {

	// TODO subroutines + handlers

	public static final String DEFAULT_SOURCE_PATH = "classpath:/rivescript/";

	/**
	 * Enable RiveScript for the application.
	 */
	private boolean enabled = true;

	/**
	 * The comma-separated list of RiveScript source files and/or directories.
	 */
	private String sourcePath = DEFAULT_SOURCE_PATH;

	/**
	 * The comma-separated list of RiveScript file extensions to load.
	 */
	private String[] fileExtensions = DEFAULT_FILE_EXTENSIONS;

	/**
	 * Enable throw exceptions.
	 */
	private boolean throwExceptions = false;

	/**
	 * Enable strict syntax checking.
	 */
	private boolean strict = true;

	/**
	 * Enable UTF-8 mode.
	 */
	private boolean utf8 = false;

	/**
	 * The unicode punctuation pattern (only used when UTF-8 mode is enabled).
	 */
	private String unicodePunctuation = DEFAULT_UNICODE_PUNCTUATION_PATTERN;

	/**
	 * Enable forcing triggers to lowercase.
	 */
	private boolean forceCase = false;

	/**
	 * The recursion depth limit.
	 */
	private int depth = DEFAULT_DEPTH;

	/**
	 * The custom error message overrides. For instance "rivescript.error-messages.deepRecursion=Custom Deep Recursion Detected Message"
	 */
	private Map<String, String> errorMessages;

	/**
	 * The comma-separated list of object handler names to register (currently supported: "groovy", "javascript", "ruby").
	 */
	private String objectHandlers;

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public String getSourcePath() {
		return sourcePath;
	}

	public void setSourcePath(String sourcePath) {
		this.sourcePath = sourcePath;
	}

	public String[] getFileExtensions() {
		return fileExtensions;
	}

	public void setFileExtensions(String[] fileExtensions) {
		this.fileExtensions = fileExtensions;
	}

	public boolean isThrowExceptions() {
		return throwExceptions;
	}

	public void setThrowExceptions(boolean throwExceptions) {
		this.throwExceptions = throwExceptions;
	}

	public boolean isStrict() {
		return strict;
	}

	public void setStrict(boolean strict) {
		this.strict = strict;
	}

	public boolean isUtf8() {
		return utf8;
	}

	public void setUtf8(boolean utf8) {
		this.utf8 = utf8;
	}

	public String getUnicodePunctuation() {
		return unicodePunctuation;
	}

	public void setUnicodePunctuation(String unicodePunctuation) {
		this.unicodePunctuation = unicodePunctuation;
	}

	public boolean isForceCase() {
		return forceCase;
	}

	public void setForceCase(boolean forceCase) {
		this.forceCase = forceCase;
	}

	public int getDepth() {
		return depth;
	}

	public void setDepth(int depth) {
		this.depth = depth;
	}

	public Map<String, String> getErrorMessages() {
		return errorMessages;
	}

	public void setErrorMessages(Map<String, String> errorMessages) {
		this.errorMessages = errorMessages;
	}

	public String getObjectHandlers() {
		return objectHandlers;
	}

	public void setObjectHandlers(String objectHandlers) {
		this.objectHandlers = objectHandlers;
	}
}
