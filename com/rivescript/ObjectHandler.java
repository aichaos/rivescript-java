package com.rivescript;

/**
 * Interface for object handlers.
 */

public interface ObjectHandler {
	/**
	 * Handler for when object code is read (loaded) by RiveScript.
	 * Should return true for success or false to indicate error.
	 *
	 * @param name The name of the object.
	 * @param code The source code inside the object.
	 */
	public boolean onLoad (String name, String[] code);

	/**
	 * Handler for when a user invokes the object. Should return the text
	 * reply from the object.
	 *
	 * @param name The name of the object being called.
	 * @param user The user's ID.
	 * @param args The argument list from the call tag.
	 */
	public String onCall (String name, String user, String[] args);
}
