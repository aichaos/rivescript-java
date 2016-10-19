import java.io.File;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.lang.String;
import com.rivescript.RiveScript;

public class RSBot {
	public static void main (String[] args) {
		// Print a fancy banner.
		System.out.println(""
			+ "      .   .       \n"
			+ "     .:...::      RiveScript Java // RSBot\n"
			+ "    .::   ::.     Version: " + com.rivescript.RiveScript.VERSION + "\n"
			+ " ..:;;. ' .;;:..  \n"
			+ "    .  '''  .     Type '/quit' to quit.\n"
			+ "     :;,:,;:      Type '/help' for more options.\n"
			+ "     :     :      \n"
		);

		// Let the user specify debug mode!
		boolean debug = false;
		for (int i = 0; i < args.length; i++) {
			if (args[i].equals("--debug") || args[i].equals("-d")) {
				debug = true;
			}
		}

		// Create a new RiveScript interpreter.
		System.out.println(":: Creating RS Object");
		RiveScript rs = new RiveScript(debug);

		// Create a handler for Perl as an object macro language.
		File rsp4jFile = new File(RSBot.class.getClassLoader().getResource("lang/rsp4j.pl").getFile());
		rs.setHandler("perl", new com.rivescript.lang.Perl(rs, rsp4jFile.getAbsolutePath()));

		// Define an object macro in Java.
		rs.setSubroutine("javatest", new ExampleMacro());

		// Load and sort replies
		System.out.println(":: Loading replies");

		File aidenDir = new File(RSBot.class.getClassLoader().getResource("Aiden").getFile());
		rs.loadDirectory(aidenDir.getAbsolutePath());
		rs.sortReplies();

		// Enter the main loop.
		InputStreamReader converter = new InputStreamReader(System.in);
		BufferedReader stdin = new BufferedReader(converter);
		while (true) {
			System.out.print("You> ");
			String message = "";
			try {
				message = stdin.readLine();
			}
			catch (IOException e) {
				System.err.println("Read error!");
			}

			// Quitting?
			if (message.equals("/quit")) {
				System.exit(0);
			}
			else if (message.equals("/dump topics")) {
				rs.dumpTopics();
			}
			else if (message.equals("/dump sorted")) {
				rs.dumpSorted();
			}
			else if (message.equals("/last")) {
				System.out.println("You last matched: "
					+ rs.lastMatch("localuser"));
			}
			else if (message.equals("/help")) {
				System.out.println("Available commands:\n"
					+ "  /last           Print the last matched trigger.\n"
					+ "  /dump topics    Pretty-print the topic structure.\n"
					+ "  /dump sorted    Pretty-print the sorted trigger structure.\n"
					+ "  /help           Show this message.\n"
					+ "  /quit           Exit the program.\n");
			}
			else {
				String reply = rs.reply("localuser", message);
				System.out.println("Bot> " + reply);
			}
		}
	}
}
