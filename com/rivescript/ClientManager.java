/*
    com.rivescript.RiveScript - The Official Java RiveScript Interpreter
    Copyright (C) 2010  Noah Petherbridge

    This program is free software; you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation; either version 2 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License along
    with this program; if not, write to the Free Software Foundation, Inc.,
    51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
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
		return com.rivescript.Util.v2s(result);
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
