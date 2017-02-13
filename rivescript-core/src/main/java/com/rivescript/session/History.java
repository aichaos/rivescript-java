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

package com.rivescript.session;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static com.rivescript.session.SessionManager.HISTORY_SIZE;

/**
 * Container to keep track of recent input and reply history.
 *
 * @author Noah Petherbridge
 * @author Marcel Overdijk
 */
public class History implements Serializable {

	private static final long serialVersionUID = 3383496053211557826L;

	private List<String> input;
	private List<String> reply;

	public History() {
		this.input = new ArrayList<>();
		this.reply = new ArrayList<>();
		for (int i = 0; i < HISTORY_SIZE; i++) {
			this.input.add("undefined");
			this.reply.add("undefined");
		}
	}

	public List<String> getInput() {
		return input;
	}

	public String getInput(int index) {
		return input.get(index);
	}

	public List<String> getReply() {
		return reply;
	}

	public String getReply(int index) {
		return reply.get(index);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		History that = (History) o;
		if (input != null ? !input.equals(that.input) : that.input != null) {
			return false;
		}
		return reply != null ? reply.equals(that.reply) : that.reply == null;
	}

	@Override
	public int hashCode() {
		int result = input != null ? input.hashCode() : 0;
		result = 31 * result + (reply != null ? reply.hashCode() : 0);
		return result;
	}

	@Override
	public String toString() {
		return "History{" +
				"input=" + input +
				", reply=" + reply +
				'}';
	}
}
