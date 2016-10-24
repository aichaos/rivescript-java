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

import com.rivescript.ObjectMacro;
import java.lang.String;
import java.lang.StringBuilder;

/**
 * An example object macro written in Java.
 *
 * To define a Java object macro, you must implement the interface
 * com.rivescript.ObjectMacro and register it using setSubroutine().
 *
 * This macro does two things: returns their message reversed, and sets
 * a user variable named `java`.
 *
 * This implements the `reverse` object macro used in Aiden/obj-java.rive
 *
 * See RSBot.java for more details.
 *
 * @author Noah Petherbridge
 */
public class ExampleMacro implements ObjectMacro {
	public String call (com.rivescript.RiveScript rs, String[] args) {
		String message = String.join(" ", args);

		// To get/set user variables for the user, you can use currentUser
		// to find their ID and then use the usual methods.
		String user = rs.currentUser();
		rs.setUservar(user, "java", "This variable was set by Java "
			+ "when you said 'reverse " + message + "'");

		// Reverse their message and return it.
		return new StringBuilder(message).reverse().toString();
	}
}
