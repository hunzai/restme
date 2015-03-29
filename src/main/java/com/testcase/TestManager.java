package com.testcase;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class TestManager {

	public List<TestCase> getTestCases(String directory) {
		List<TestCase> testCases = new ArrayList<TestCase>();
		File dir = new File(directory);
		for (File file : dir.listFiles()) {
			testCases.add(getTestCase(file.getPath()));
		}

		return testCases;
	}

	public TestCase getTestCase(String jsonFile) {
		String cotent = readFile(jsonFile);
		return new Gson().fromJson(cotent, TestCase.class);
	}

	public String readFile(String path) {
		String content = new String();
		
		try {
			File fileDir = new File(path);

			BufferedReader in = new BufferedReader(new InputStreamReader(
					new FileInputStream(fileDir), "UTF8"));
			String str;

			while ((str = in.readLine()) != null) {
				content += str;
			}

			in.close();
		} catch (UnsupportedEncodingException e) {
			System.out.println(e.getMessage());
		} catch (IOException e) {
			System.out.println(e.getMessage());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		return content;
	}
}
