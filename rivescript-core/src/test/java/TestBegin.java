import org.junit.Test;
import com.rivescript.RiveScript;

public class TestBegin extends TestBase {
	public String replies() {
		return "begin";
	}

	@Test
	public void testNoBeginBlock() {
		this.setUp("no_begin_block.rive");
		this.reply("hello bot", "Hello human.");
	}

	@Test
	public void testSimpleBeginBlock() {
		this.setUp("simple_begin_block.rive");
		this.reply("Hello bot.", "Hello human.");
	}

	@Test
	public void testBlockedBeginBlock() {
		this.setUp("blocked_begin_block.rive");
		this.reply("Hello bot.", "Nope.");
	}

	@Test
	public void testConditionalBeginBlock() {
		this.setUp("conditional_begin_block.rive");
		this.reply("Hello bot.", "Hello human.");
		this.uservar("met", "true");
		this.uservar("name", "undefined");
		this.reply("My name is bob", "Hello, Bob.");
		this.uservar("name", "Bob");
		this.reply("Hello Bot", "Bob: Hello human.");
	}
}
