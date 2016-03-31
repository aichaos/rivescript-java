import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

public class RunTests {
	public static void main(String[] args) {
		Result result = JUnitCore.runClasses(JunitTestSuite.class);
		for (Failure failure : result.getFailures()) {
			System.out.println(failure.toString());
		}
		System.out.println(result.getRunCount() + " tests were executed in " + result.getRunTime() + "ms.");
		if (result.wasSuccessful()) {
			System.out.println("All tests passed successfully.");
		}
		else {
			System.out.println(result.getFailureCount() + " test(s) failed!");
		}
	}
}
