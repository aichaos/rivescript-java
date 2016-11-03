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
import com.rivescript.Util;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;

import static java.util.Objects.requireNonNull;

/**
 * Perl programming language support for RiveScript-Java.
 *
 * @author Noah Petherbridge
 * @see ObjectHandler
 */
public class Perl implements ObjectHandler {

	private String rsp4j; // Path to the Perl script
	private RiveScript parent; // Parent RS object
	private HashMap<String, String> codes = new HashMap<>(); // Object codes

	/**
	 * Creates a Perl {@link ObjectHandler}. Must take the path to the rsp4j script as its argument.
	 *
	 * @param rivescript The RiveScript instance, not null.
	 * @param rsp4j      The path to the rsp4j script (either in .pl or .exe format), not null.
	 */
	public Perl(RiveScript rivescript, String rsp4j) {
		this.parent = requireNonNull(rivescript, "'rivescript' must not be null");
		this.rsp4j = requireNonNull(rsp4j, "'rsp4j' must not be null");
		;
	}

	/**
	 * Handler for when object code is read (loaded) by RiveScript. Should return {@code true} for
	 * success or {@code false} to indicate error.
	 *
	 * @param name The name of the object.
	 * @param code The source code inside the object.
	 */
	@Override
	public boolean onLoad(String name, String[] code) {
		codes.put(name, Util.join(code, "\n"));
		return true;
	}

	/**
	 * Handler for when a user invokes the object. Should return the text reply from the object.
	 *
	 * @param name The name of the object being called.
	 * @param user The user's id.
	 * @param args The argument list from the call tag.
	 */
	@Override
	public String onCall(String name, String user, String[] args) {
		// Prepare JSON data to send.
		try {
			JSONObject json = new JSONObject();

			// Set the flat scalars first.
			json.put("id", user);
			json.put("message", Util.join(args, " "));
			json.put("code", codes.get(name));

			// Transcode the user's data into a JSON object.
			JSONObject vars = new JSONObject();
			HashMap<String, String> data = parent.getUservars(user);
			Iterator it = data.keySet().iterator();
			while (it.hasNext()) {
				String key = it.next().toString();
				vars.put(key, data.get(key));
			}

			// Add it to the JSON.
			json.put("vars", vars);

			// Stringify.
			String outgoing = json.toString(); // Query
			String incoming; // Response

			// Run the Perl RiveScript handler.
			try {
				Process p = Runtime.getRuntime().exec(this.rsp4j + " --java");
				OutputStream stdIn = p.getOutputStream();
				BufferedReader stdOut =
						new BufferedReader(new InputStreamReader(p.getInputStream()));

				// Send it the JSON-in.
				stdIn.write(outgoing.getBytes());
				stdIn.flush();
				stdIn.close();

				// Read the results back.
				Vector<String> result = new Vector<>();
				String line;
				while ((line = stdOut.readLine()) != null) {
					result.add(line);
				}
				incoming = Util.join(Util.Sv2s(result), "\n");
			} catch (java.io.IOException e) {
				System.err.println("IOException error in " + this.getClass().getCanonicalName()
						+ ": " + e.getMessage());
				return "[ERR: IOException: " + e.getMessage() + "]";
			}

			// Process the response.
			JSONObject reply = new JSONObject(incoming);

			// OK, or error?
			if (reply.getString("status").equals("error")) {
				return "[ERR: " + reply.getString("message");
			}

			// Send back any new user vars.
			JSONObject newVars = reply.getJSONObject("vars");
			String[] keys = reply.getNames(newVars);
			for (int i = 0; i < keys.length; i++) {
				String value = newVars.getString(keys[i]);
				parent.setUservar(user, keys[i], value);
			}

			// OK. Get the reply.
			return reply.getString("reply");

		} catch (org.json.JSONException e) {
			System.err.println("JSONException in " + this.getClass().getCanonicalName() + ": "
					+ e.getMessage());
			return "[ERR: JSONException: " + e.getMessage() + "]";
		}
	}

	@Override
	public void setClass(String name, ObjectMacro impl) {
	}
}
