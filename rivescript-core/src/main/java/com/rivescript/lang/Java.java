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
