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

package com.rivescript.macro;

/**
 * Interface for RiveScript object handlers.
 *
 * @author Noah Petherbridge
 * @author Marcel Overdijk
 */
public interface ObjectHandler {

	/**
	 * Handler for when object code is read (loaded) by RiveScript.
	 *
	 * @param name the name of the object
	 * @param code the source code inside the object
	 */
	void load(String name, String[] code);

	/**
	 * Handler for when a user invokes the object. Should return the {@link String} result from the object.
	 * <p>
	 * This code is executed when a {@code <call>} tag in a RiveScript reply wants to call your object macro.
	 *
	 * @param name   the name of the object being called
	 * @param fields the argument list from the call tag
	 * @return the result
	 */
	String call(String name, String[] fields);
}
