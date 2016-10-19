/**
 * An example object macro written in Java.
 *
 * To define a Java object macro, you must implement the interface
 * com.rivescript.ObjectMacro and register it using setSubroutine().
 *
 * This macro does two things: returns their message reversed, and sets
 * a user variable named `java`.
 *
 * This implements the `reverse` object macro used in Aiden/obj-java.rive
 *
 * See RSBot.java for more details.
 */

import com.rivescript.ObjectMacro;
import java.lang.String;
import java.lang.StringBuilder;

public class ExampleMacro implements com.rivescript.ObjectMacro {
	public String call (com.rivescript.RiveScript rs, String[] args) {
		String message = String.join(" ", args);

		// To get/set user variables for the user, you can use currentUser
		// to find their ID and then use the usual methods.
		String user = rs.currentUser();
		rs.setUservar(user, "java", "This variable was set by Java "
			+ "when you said 'reverse " + message + "'");

		// Reverse their message and return it.
		return new StringBuilder(message).reverse().toString();
	}
}
