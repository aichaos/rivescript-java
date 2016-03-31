import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import com.rivescript.RiveScript;

public class TestBase {
	protected RiveScript rs;

	public String replies() {
		return "undefined";
	}

	public void setUp(String file) {
		this.rs = new RiveScript();
		this.rs.loadFile("./fixtures/" + this.replies() + "/" + file);
		this.rs.sortReplies();
	}

	public void setUp(String file, boolean debug) {
		this.rs = new RiveScript(debug);
		this.rs.loadFile("./fixtures/" + this.replies() + "/" + file);
		this.rs.sortReplies();
	}

	public void extend(String file) {
		this.rs.loadFile("./fixtures/" + this.replies() + "/" + file);
		this.rs.sortReplies();
	}

	public void uservar(String key, String value) {
		this.rs.setUservar("localuser", key, value);
	}

	public void reply(String input, String expect) {
		String reply = this.rs.reply("localuser", input);
		// System.out.println(input + ": " + expect + " <-> " + reply);
		assertEquals(expect, reply);
	}

	public void reply(String input, String[] expected) {
		String reply = this.rs.reply("localuser", input);
		for (String expect : expected) {
			if (reply.equals(expect)) {
				assertTrue(true);
				return;
			}
		}

		fail();
	}

	@Test
	public void testJunit() {
		String str = "Junit is working fine";
		assertEquals("Junit is working fine", str);
	}
}
