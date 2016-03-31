import org.junit.Test;
import com.rivescript.RiveScript;

public class TestBotVariables extends TestBase {
	public String replies() {
		return "bot-variables";
	}

	@Test
	public void testVariables() {
		this.setUp("bot-variables.rive");
		this.reply("What is your name?", "My name is Aiden.");
		this.reply("What are you?", "I'm undefined.");
		this.reply("Happy birthday!", "Thanks!");
		this.reply("How old are you?", "I am 6.");
	}

	@Test
	public void testGlobalVariables() {
		this.setUp("global-variables.rive");
		this.reply("Debug mode.", "Debug mode is: false");
		this.reply("Set debug mode true", "Switched to true.");
		this.reply("Debug mode?", "Debug mode is: true");
	}
}
