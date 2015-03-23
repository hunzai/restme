package com;
import java.io.FileNotFoundException;
import java.io.FileReader;

import com.google.gson.stream.JsonReader;
import com.testcase.*;


public class App {

	public static void main(String[] args) {
		TestManager manager = new TestManager();
		System.out.println(manager.getTestCases("jsons").get(0).getRequest().getUrl());
	}

	public static JsonReader getJsonFromFile(String filePath)
			throws FileNotFoundException {
		return new JsonReader(new FileReader(filePath));
	}

}
