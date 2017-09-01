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

import com.rivescript.ConcatMode;

/**
 * User-configurable properties of the RiveScript {@link Parser}.
 *
 * @author Noah Petherbridge
 * @author Marcel Overdijk
 */
public class ParserConfig {

	private boolean strict;
	private boolean utf8;
	private boolean forceCase;
	private ConcatMode concat;

	protected ParserConfig() {
	}

	/**
	 * Returns whether strict syntax checking is enabled.
	 *
	 * @return whether strict syntax checking is enabled
	 */
	public boolean isStrict() {
		return strict;
	}

	/**
	 * Returns whether UTF-8 mode is enabled for user messages and triggers.
	 *
	 * @return whether UTF-8 mode is enabled for user messages and triggers
	 */
	public boolean isUtf8() {
		return utf8;
	}

	/**
	 * Returns whether forcing triggers to lowercase is enabled.
	 *
	 * @return whether forcing triggers to lowercase is enabled
	 */
	public boolean isForceCase() {
		return forceCase;
	}

	/**
	 * Returns the concat mode.
	 *
	 * @return the concat mode
	 */
	public ConcatMode getConcat() {
		return concat;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		ParserConfig that = (ParserConfig) o;
		if (strict != that.strict) {
			return false;
		}
		if (utf8 != that.utf8) {
			return false;
		}
		if (forceCase != that.forceCase) {
			return false;
		}
		return concat == that.concat;
	}

	@Override
	public int hashCode() {
		int result = (strict ? 1 : 0);
		result = 31 * result + (utf8 ? 1 : 0);
		result = 31 * result + (forceCase ? 1 : 0);
		result = 31 * result + (concat != null ? concat.hashCode() : 0);
		return result;
	}

	@Override
	public String toString() {
		return "ParserConfig{" +
				"strict=" + strict +
				", utf8=" + utf8 +
				", forceCase=" + forceCase +
				", concat=" + concat +
				'}';
	}

	/**
	 * Converts this {@link ParserConfig} instance to a {@link Builder}.
	 *
	 * @return the builder
	 */
	public Builder toBuilder() {
		return newBuilder()
				.strict(this.strict)
				.utf8(this.utf8)
				.forceCase(this.forceCase)
				.concat(this.concat);
	}

	/**
	 * Creates a new {@link Builder}.
	 *
	 * @return the builder
	 */
	public static Builder newBuilder() {
		return new Builder();
	}

	/**
	 * Builder for {@link ParserConfig}.
	 */
	public static final class Builder {

		private boolean strict;
		private boolean utf8;
		private boolean forceCase;
		private ConcatMode concat;

		private Builder() {
		}

		/**
		 * Sets whether strict syntax checking is enabled.
		 *
		 * @param strict whether strict syntax checking is enabled
		 * @return this builder
		 */
		public Builder strict(boolean strict) {
			this.strict = strict;
			return this;
		}

		/**
		 * Sets whether UTF-8 mode is enabled.
		 *
		 * @param utf8 whether UTF-8 is enabled
		 * @return this builder
		 */
		public Builder utf8(boolean utf8) {
			this.utf8 = utf8;
			return this;
		}

		/**
		 * Sets whether forcing triggers to lowercase is enabled.
		 *
		 * @param forceCase whether forcing triggers to lowercase is enabled
		 * @return this builder
		 */
		public Builder forceCase(boolean forceCase) {
			this.forceCase = forceCase;
			return this;
		}

		/**
		 * Sets the concat mode
		 *
		 * @param concat the concat mode
		 * @return this builder
		 */
		public Builder concat(ConcatMode concat) {
			this.concat = concat;
			return this;
		}

		/**
		 * Builds the parser config.
		 *
		 * @return the parser config
		 */
		public ParserConfig build() {
			ParserConfig config = new ParserConfig();
			config.strict = this.strict;
			config.utf8 = this.utf8;
			config.forceCase = this.forceCase;
			config.concat = this.concat;
			return config;
		}
	}
}
