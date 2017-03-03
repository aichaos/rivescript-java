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

package com.rivescript.lang.javascript;

import com.rivescript.lang.jsr223.Jsr223ScriptingHandler;
import com.rivescript.macro.ObjectHandler;

/**
 * Provides JavaScript programming language support for object macros in RiveScript.
 * <p>
 * Example:
 * <p>
 * <pre>
 * <code>
 * import com.rivescript.Config;
 * import com.rivescript.RiveScript;
 * import com.rivescript.lang.javascript.JavaScriptHandler;
 *
 * RiveScript bot = new RiveScript();
 * bot.setHandler("javascript", new JavaScriptHandler(rs));
 *
 * // and go on as normal
 * </code>
 * </pre>
 * <p>
 * And in your RiveScript code, you can load and run JavaScript objects:
 * <p>
 * <pre>
 * <code>
 * > object reverse javascript
 *     var msg = args.join(' ');
 *     return msg.split('').reverse().join('');
 * < object
 *
 * > object setname javascript
 *     var username = rs.currentUser();
 *     rs.setUservar(username, 'name', args[0]);
 * < object
 *
 * + reverse *
 * - &lt;call&gt;reverse &lt;star&gt;&lt;/call&gt;
 *
 * + my name is *
 * - I will remember that.&lt;call&gt;setname "&lt;formal&gt;"&lt;/call&gt;
 *
 * + what is my name
 * - You are &lt;get name&gt;
 * </code>
 * </pre>
 *
 * @author Marcel Overdijk
 * @see ObjectHandler
 * @see Jsr223ScriptingHandler
 */
public class JavaScriptHandler extends Jsr223ScriptingHandler {

	/**
	 * Constructs a JavaScript {@link ObjectHandler}.
	 */
	public JavaScriptHandler() {
		super("javascript", ""
				+ "function %s(rs, args) {\n"
				+ "    args = Java.from(args);"
				+ "    %s\n"
				+ "}");
	}
}
