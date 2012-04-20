import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.lang.String;
import com.rivescript.RiveScript;

public class RSBot {
	public static void main (String[] args) {
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

		// Create a handler for Perl objects.
		rs.setHandler("perl", new com.rivescript.lang.Perl(rs, "./lang/rsp4j.pl"));

		// Load and sort replies
		System.out.println(":: Loading replies");
		rs.loadDirectory("./Aiden");
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
			else {
				String reply = rs.reply("localuser", message);
				System.out.println("Bot> " + reply);
			}
		}
	}
}
