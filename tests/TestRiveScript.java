import org.junit.Test;
import com.rivescript.RiveScript;

public class TestRiveScript extends TestBase {
	public String replies() {
		return "rivescript";
	}

	@Test
	public void testRedirectWithUndefinedInput() {
		this.setUp("empty-star-redirect.rive");

		this.reply("test", "hello");
		this.reply("?", "Wildcard \"\"!");
	}

	@Test
	public void testEmptyVariableRedirect() {
		this.setUp("empty-vars-redirect.rive");

		// No variable set, should go through wildcard
		this.reply("test", "Wildcard \"undefined\"!");
		this.reply("test without redirect", "undefined");

		// Variable set, should respond with text
		this.reply("set test name test", "hello test!");

		// Different variable set, should get wildcard
		this.reply("set test name newtest", "Wildcard \"newtest\"!");

		// Test redirects using bot variable.
		this.reply("get global test", "hello test!");
		this.reply("get bad global test", "Wildcard \"undefined\"!");
	}
}
