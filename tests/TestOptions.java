import org.junit.Test;
import com.rivescript.RiveScript;

public class TestOptions extends TestBase {
	public String replies() {
		return "options";
	}

	@Test
	public void testConcat() {
		this.setUp("options.rive");
		this.extend("options-2.rive");

		this.reply("test concat default", "Helloworld!");
		this.reply("test concat space", "Hello world!");
		this.reply("test concat none", "Helloworld!");
		this.reply("test concat newline", "Hello\nworld!");
		this.reply("test concat foobar", "Helloworld!");
		this.reply("test concat second file", "Helloworld!");
	}

	@Test
	public void testConcatNewlineWithConditionals() {
		this.setUp("condition-nl.rive");

		this.reply("test a", "First A line\nSecond A line\nThird A line");
		this.reply("test b", "First B line\nSecond B line\nThird B line");
	}

	@Test
	public void testConcatSpaceWithConditionals() {
		this.setUp("condition-space.rive");

		this.reply("test a", "First A line Second A line Third A line");
		this.reply("test b", "First B line Second B line Third B line");
	}

	@Test
	public void testConcatWithConditionals() {
		this.setUp("condition-none.rive");

		this.reply("test a", "First A lineSecond A lineThird A line");
		this.reply("test b", "First B lineSecond B lineThird B line");
	}
}
