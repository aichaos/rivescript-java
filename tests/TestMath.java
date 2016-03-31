import org.junit.Test;
import com.rivescript.RiveScript;

public class TestMath extends TestBase {
	public String replies() {
		return "math";
	}

	@Test
	public void testMath() {
		this.setUp("math.rive");

		this.reply("test counter", "counter set");
		this.reply("show", "counter = 0");

		this.reply("add", "adding");
		this.reply("show", "counter = 1");

		this.reply("sub", "subbing");
		this.reply("show", "counter = 0");

		this.reply("div", "divving");
		this.reply("show", "counter = 5");

		this.reply("mult", "multing");
		this.reply("show", "counter = 20");
	}
}
