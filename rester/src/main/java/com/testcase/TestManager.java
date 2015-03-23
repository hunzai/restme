package com.testcase;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class TestManager {

	public List<TestCase> getTestCases(String directory){
		List<TestCase> testCases  = new ArrayList<TestCase>();
		File dir = new File(directory);
		for (File file : dir.listFiles()) {
			testCases.add(getTestCase(file.getPath()));
		}
		
		return testCases;
	}
	
	public TestCase getTestCase(String jsonFile){
		TestCase testCase = null;
		ObjectMapper mapper = new ObjectMapper();

		try {
			testCase = mapper.readValue(new File(jsonFile), TestCase.class);
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return testCase;
	}
}
