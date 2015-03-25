package test;

import org.testng.annotations.Test;
import com.testcase.TestCase;
import com.testcase.TestManager;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class TestManagerTest extends BaseTest{
	
	String jsonFile = "json/authentications.json";
	
	@Test
	public void makeTestCase(){
		TestManager testManager = mock(TestManager.class);
		verify(testManager);

		TestCase testCase = testManager.getTestCase(jsonFile);
		when(testCase.getRequest().getUrl()).thenReturn("");
		

	}
}
