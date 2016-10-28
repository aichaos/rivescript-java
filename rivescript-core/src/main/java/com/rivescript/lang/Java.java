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

package com.rivescript.lang;

import com.rivescript.ObjectHandler;
import com.rivescript.ObjectMacro;
import com.rivescript.RiveScript;

import java.util.HashMap;

import static java.util.Objects.requireNonNull;

/**
 * Java programming language support for RiveScript-Java.
 * <p>
 * Note that since Java must be compiled before running, this object macro language is only
 * available at compile-time using the {@link RiveScript#setSubroutine(String, ObjectMacro)} method.
 * Inline Java code can't be parsed and compiled from RiveScript source files.
 *
 * @author Noah Petherbridge
 * @see ObjectHandler
 */
public class Java implements ObjectHandler {

	private RiveScript parent;
	private HashMap<String, ObjectMacro> handlers = new HashMap<>();

	/**
	 * Constructs a Java {@link ObjectHandler}.
	 *
	 * @param rivescript The RiveScript instance, not null.
	 */
	public Java(RiveScript rivescript) {
		this.parent = requireNonNull(rivescript, "'rivescript' must not be null");
	}

	/**
	 * Handler for when object code is read (loaded) by RiveScript. Should return {@code true} for
	 * success or {@code false} to indicate error.
	 * <p>
	 * We can't dynamically evaluate Java code, so this function just logs an error.
	 *
	 * @param name The name of the object.
	 * @param code The source code inside the object.
	 */
	@Override
	public boolean onLoad(String name, String[] code) {
		System.err.println("NOTICE: Can't dynamically eval Java code from an "
				+ "inline object macro! Use the setSubroutine() function instead "
				+ "to define an object at compile time.");
		return false;
	}

	/**
	 * Handler for when a user invokes the object. Should return the text reply from the object.
	 *
	 * @param name The name of the object being called.
	 * @param user The user's id.
	 * @param args The argument list from the tag.
	 */
	@Override
	public String onCall(String name, String user, String[] args) {
		// Does the object macro exist?
		ObjectMacro macro = this.handlers.get(name);
		if (macro == null) {
			return "[ERR: Object Not Found]";
		}

		// Call it!
		return macro.call(this.parent, args);
	}

	/**
	 * Sets a Java class to handle the {@link ObjectMacro} directly.
	 * <p>
	 * This is called by the parent RiveScript object's
	 * {@link RiveScript#setSubroutine(String, ObjectMacro)} method.
	 *
	 * @param name The name of the object macro.
	 * @param impl The {@link ObjectMacro} implementation.
	 */
	@Override
	public void setClass(String name, ObjectMacro impl) {
		this.handlers.put(name, impl);
	}
}
