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

package com.rivescript;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;

/**
 * A manager for all the bot's users.
 *
 * @author Noah Petherbridge
 */
public class ClientManager {

	private HashMap<String, Client> clients = new HashMap<>(); // List of users

	/**
	 * Creates a client manager. Only one needed per bot.
	 */
	public ClientManager() {
		// Nothing to construct here.
	}

	/**
	 * Gets a {@link Client} object for a given user id.
	 *
	 * @param userId The user id.
	 */
	public Client client(String userId) {
		// Is this a new topic?
		if (clients.containsKey(userId) == false) {
			// Create it.
			clients.put(userId, new Client(userId));
		}

		return clients.get(userId);
	}

	/**
	 * Gets a list of the {@link Client}s managed.
	 */
	public String[] listClients() {
		Vector<String> result = new Vector<>();
		Iterator it = clients.keySet().iterator();
		while (it.hasNext()) {
			result.add(it.next().toString());
		}
		return Util.Sv2s(result);
	}

	/**
	 * Queries whether a client is known or not.
	 *
	 * @param userId The user id.
	 */
	public boolean clientExists(String userId) {
		if (clients.containsKey(userId)) {
			return true;
		}
		return false;
	}
}
