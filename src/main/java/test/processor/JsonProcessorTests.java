package test.processor;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.google.gson.JsonObject;
import com.jayway.restassured.path.json.JsonPath;
import com.processor.JsonProcessor;

public class JsonProcessorTests {

	JsonProcessor processor = null ;
	@BeforeClass
	public void setup(){
		processor = new JsonProcessor();
	}
	
	@Test
	public void testTree(){
		Object content = processor.addTestDataToTestCaseContent("jsons/test_case.json");
		System.out.println(content);
	}
	
	
}
