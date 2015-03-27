package test;

import java.util.List;

import org.testng.annotations.Test;

import com.MyRester;
import com.testcase.TestCase;
import com.testcase.TestManager;

public class UtilsTest extends BaseTest{
		
	@Test
	public void testLogging(){
		TestManager manager = new TestManager();
		String testsFolderPath = "jsons";
		List<TestCase> tests = manager.getTestCases(testsFolderPath);
		MyRester.getActualResponse(tests.get(0).getRequest());
	}

}
