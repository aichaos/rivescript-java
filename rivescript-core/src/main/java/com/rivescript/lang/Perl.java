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

import com.rivescript.RiveScript;
import com.rivescript.macro.ObjectHandler;
import com.rivescript.util.StringUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Vector;

import static java.util.Objects.requireNonNull;

/**
 * Perl programming language support for RiveScript-Java.
 *
 * @author Noah Petherbridge
 * @see ObjectHandler
 */
public class Perl implements ObjectHandler {

	private static Logger logger = LoggerFactory.getLogger(Perl.class);

	private RiveScript rs;                 // Parent RiveScript instance object
	private String rsp4j;                  // Path to the Perl script
	private HashMap<String, String> codes; // Object codes

	/**
	 * Creates a Perl {@link ObjectHandler}. Must take the path to the rsp4j script as its argument.
	 *
	 * @param rs    the RiveScript instance, not null.
	 * @param rsp4j the path to the rsp4j script (either in .pl or .exe format), not null.
	 */
	public Perl(RiveScript rs, String rsp4j) {
		this.rs = requireNonNull(rs, "'rs' must not be null");
		this.rsp4j = requireNonNull(rsp4j, "'rsp4j' must not be null");
		this.codes = new HashMap<>();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void load(String name, String[] code) {
		codes.put(name, StringUtils.join(code, "\n"));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String call(String name, String[] fields) {
		String user = rs.currentUser();
		// Prepare JSON data to send.
		try {
			JSONObject json = new JSONObject();

			// Set the flat scalars first.
			json.put("id", user);
			json.put("message", StringUtils.join(fields, " "));
			json.put("code", codes.get(name));

			// Transcode the user's data into a JSON object.
			JSONObject vars = new JSONObject();
			Map<String, String> data = rs.getUservars(user).getVariables();
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
				BufferedReader stdOut = new BufferedReader(new InputStreamReader(p.getInputStream()));

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
				incoming = StringUtils.join(result.toArray(new String[0]), "\n");
			} catch (IOException e) {
				logger.error("IOException error in " + this.getClass().getCanonicalName() + ": " + e.getMessage());
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
				rs.setUservar(user, keys[i], value);
			}

			// OK. Get the reply.
			return reply.getString("reply");

		} catch (JSONException e) {
			logger.error("JSONException in " + this.getClass().getCanonicalName() + ": " + e.getMessage());
			return "[ERR: JSONException: " + e.getMessage() + "]";
		}
	}
}
