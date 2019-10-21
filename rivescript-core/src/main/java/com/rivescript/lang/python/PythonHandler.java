package com.rivescript.lang.python;

import com.rivescript.lang.jsr223.Jsr223ScriptingHandler;
import com.rivescript.macro.ObjectHandler;

public class PythonHandler extends Jsr223ScriptingHandler {

	/**
	 * Constructs a Python {@link ObjectHandler}.
	 */
	public PythonHandler() {
		super("python", ""
				+ "def %s(rs, args):\n"
				+ "%s\n"
				+ "");
		System.setProperty("python.cachedir.skip", "true");
		System.setProperty("python.import.site", "false");
	}

}
