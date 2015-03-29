package com;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;
import java.util.logging.Logger;

import com.MyRester;
import com.google.gson.stream.JsonReader;
import com.testcase.*;

public class App {
	
	public static Logger logger = Logger.getAnonymousLogger();

	public static void main(String[] args) {
		TestManager manager = new TestManager();
		String testsFolderPath = "jsons";
		List<TestCase> tests = manager.getTestCases(testsFolderPath);
		for (TestCase testCase : tests) {
			MyRester
			.getActualResponse(testCase.getRequest());	
			
		}	
	}

	public static JsonReader getJsonFromFile(String filePath)
			throws FileNotFoundException {
		return new JsonReader(new FileReader(filePath));
	}

}
