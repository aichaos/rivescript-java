import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
	TestBegin.class,
	TestBotVariables.class,
	TestMath.class,
	TestOptions.class,
	TestReplies.class,
	TestRiveScript.class,
	TestSubstitutions.class,
	TestTopics.class,
	TestTriggers.class,
})
public class JunitTestSuite {
}
