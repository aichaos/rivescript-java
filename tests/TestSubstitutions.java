import org.junit.Test;
import com.rivescript.RiveScript;

public class TestSubstitutions extends TestBase {
	public String replies() {
		return "substitutions";
	}

	@Test
	public void testSubstitutions() {
		this.setUp("no-subs.rive");

		this.reply("whats up", "nm.");
		this.reply("what's up?", "nm.");
		this.reply("what is up?", "Not much.");

		this.extend("subs.rive");

		this.reply("whats up?", "Not much.");
		this.reply("what's up?", "Not much.");
		this.reply("What is up?", "Not much.");
	}

	@Test
	public void testPersonSubstitutions() {
		this.setUp("no-subs.rive");

		this.reply("say I am cool", "i am cool");
		this.reply("say You are dumb", "you are dumb");

		this.extend("person-subs.rive");

		this.reply("say I am cool", "you are cool");
		this.reply("say You are dumb", "I am dumb");
	}
}
