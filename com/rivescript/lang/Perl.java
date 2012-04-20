package com.rivescript.lang;

import java.lang.String;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;
import org.json.JSONObject;

/**
 * Perl programming language support for RiveScript-Java.
 */

public class Perl implements com.rivescript.ObjectHandler {
	private String rsp4j;                      // Path to the Perl script
	private com.rivescript.RiveScript parent;  // Parent RS object
	private HashMap<String, String> codes =
		new HashMap<String, String>();       // Object codes

	/**
	 * Create a Perl handler. Must take the path to the rsp4j script as
	 * its argument.
	 *
	 * @param rivescript Instance of your RiveScript object.
	 * @param rsp4j      Path to the rsp4j script (either in .pl or .exe format).
	 */
	public Perl (com.rivescript.RiveScript rivescript, String rsp4j) {
		this.parent = rivescript;
		this.rsp4j  = rsp4j;
	}

	/**
	 * Handler for when object code is read (loaded) by RiveScript.
	 * Should return true for success or false to indicate error.
	 *
	 * @param name The name of the object.
	 * @param code The source code inside the object.
	 */
	public boolean onLoad (String name, String[] code) {
		codes.put(name, com.rivescript.Util.join(code,"\n"));
		return true;
	}

	/**
	 * Handler for when a user invokes the object. Should return the text
	 * reply from the object.
	 *
	 * @param name The name of the object being called.
	 * @param user The user's ID.
	 * @param args The argument list from the call tag.
	 */
	public String onCall (String name, String user, String[] args) {
		// Prepare JSON data to send.
		try {
			JSONObject json = new JSONObject();

			// Set the flat scalars first.
			json.put("id", user);
			json.put("message", com.rivescript.Util.join(args, " "));
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
			String incoming = "";              // Response

			// Run the Perl RiveScript handler.
			try {
				Process p = Runtime.getRuntime().exec(this.rsp4j + " --java");
				OutputStream   stdIn  = p.getOutputStream();
				BufferedReader stdOut = new BufferedReader(new InputStreamReader(p.getInputStream()));
				BufferedReader stdErr = new BufferedReader(new InputStreamReader(p.getErrorStream()));

				// Send it the JSON-in.
				stdIn.write(outgoing.getBytes());
				stdIn.flush();
				stdIn.close();

				// Read the results back.
				Vector<String> result = new Vector<String>();
				String line;
				while ((line = stdOut.readLine()) != null) {
					result.add(line);
				}
				incoming = com.rivescript.Util.join( com.rivescript.Util.v2s(result), "\n");
			} catch (java.io.IOException e) {
				System.err.println("IOException error in com.rivescript.lang.Perl: " + e.getMessage());
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
			System.err.println("JSONException in com.rivescript.lang.Perl: " + e.getMessage());
			return "[ERR: JSONException: " + e.getMessage() + "]";
		}
	}
}
