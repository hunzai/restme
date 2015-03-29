package test;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.processor.JsonProcessor;

public class JsonProcessorTests {

	JsonProcessor processor = null ;
	@BeforeClass
	public void setup(){
		processor = new JsonProcessor();
	}
	
	@Test
	public void testTree(){
		processor.addAttribute();
	}
	
	
}
