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

import java.lang.String;
import java.util.HashMap;

/**
 * Java programming language support for RiveScript-Java.
 *
 * Note that since Java must be compiled before running, this object
 * macro language is only available at compile-time using the
 * setSubroutine() function. Inline Java code can't be parsed and
 * compiled from RiveScript source files.
 *
 * @author Noah Petherbridge
 */
public class Java implements com.rivescript.ObjectHandler {
	private com.rivescript.RiveScript master;
	private HashMap<String, com.rivescript.ObjectMacro> handlers =
		new HashMap<String, com.rivescript.ObjectMacro>();

	/**
	 * Create a Java handler.
	 *
	 * @param rivescript Instance of your RiveScript object.
	 */
	public Java (com.rivescript.RiveScript rivescript) {
		this.master = rivescript;
	}

	/**
	 * Handler for when object code is read by RiveScript.
	 *
	 * We can't dynamically evaluate Java code, so this function just
	 * logs an error.
	 */
	public boolean onLoad (String name, String[] code) {
		System.err.println("NOTICE: Can't dynamically eval Java code from an "
			+ "inline object macro! Use the setSubroutine() function instead "
			+ "to define an object at compile time.");
		return false;
	}

	/**
	 * Handler for directly setting an implementation class for a macro.
	 *
	 * This is called by the parent RiveScript object's `setSubroutine()`
	 * function.
	 */
	public void setClass (String name, com.rivescript.ObjectMacro impl) {
		this.handlers.put(name, impl);
	}

	/**
	 * Handler for when a user invokes the object.
	 *
	 * This should return the reply text from the object.
	 *
	 * @param name The name of the object being called.
	 * @param user The calling user's ID.
	 * @param args The argument list from the call tag.
	 */
	public String onCall (String name, String user, String[] args) {
		// Does the object macro exist?
		com.rivescript.ObjectMacro macro = this.handlers.get(name);
		if (macro == null) {
			return "[ERR: Object Not Found]";
		}

		// Call it!
		return macro.call(this.master, args);
	}
}
