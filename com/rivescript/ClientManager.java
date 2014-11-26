/*
	com.rivescript.RiveScript - The Official Java RiveScript Interpreter

	Copyright (c) 2014 Noah Petherbridge

	Permission is hereby granted, free of charge, to any person obtaining a copy
	of this software and associated documentation files (the "Software"), to deal
	in the Software without restriction, including without limitation the rights
	to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
	copies of the Software, and to permit persons to whom the Software is
	furnished to do so, subject to the following conditions:

	The above copyright notice and this permission notice shall be included in all
	copies or substantial portions of the Software.

	THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
	IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
	FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
	AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
	LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
	OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
	SOFTWARE.
*/

package com.rivescript;

import java.lang.String;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;

/**
 * A manager for all the bot's users.
 */

public class ClientManager {
	private HashMap<String, com.rivescript.Client> clients =
		new HashMap<String, com.rivescript.Client>(); // List of users

	/**
	 * Create a client manager. Only one needed per bot.
	 */
	public ClientManager () {
		// Nothing to construct here.
	}

	/**
	 * Get a Client object for a given user ID.
	 *
	 * @param username The user ID you want to work with.
	 */
	public com.rivescript.Client client (String username) {
		// Is this a new topic?
		if (clients.containsKey(username) == false) {
			// Create it.
			clients.put(username, new com.rivescript.Client(username));
		}

		return clients.get(username);
	}

	/**
	 * Get a list of the clients managed.
	 */
	public String[] listClients () {
		Vector<String> result = new Vector<String>();
		Iterator it = clients.keySet().iterator();
		while (it.hasNext()) {
			result.add(it.next().toString());
		}
		return com.rivescript.Util.Sv2s(result);
	}

	/**
	 * Query whether a client is known or not.
	 *
	 * @param user The user ID.
	 */
	public boolean clientExists (String user) {
		if (clients.containsKey(user)) {
			return true;
		}
		return false;
	}
}
