package com.utils;

import static com.jayway.jsonpath.JsonPath.using;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.List;

import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.Option;
import com.jayway.restassured.RestAssured;
import com.jayway.restassured.config.LogConfig;
import com.jayway.restassured.config.RestAssuredConfig;
import com.jayway.restassured.specification.RequestSpecification;
import com.testcase.Request;
import com.testcase.TestCase;

public class MyRester {

	public void assertJsonValues(TestCase testCase){
		List<String> expectedJson = getXPaths(testCase.getResponse().getContent().toString());

		String expected = testCase.getResponse().getContent().toString();
		String actual = getActualResponse(testCase.getRequest());

		for (String string : expectedJson) {
			Object actualValue = com.jayway.jsonpath.JsonPath.read(actual,
					string);
			if (actualValue instanceof String) {
				Object expectedValue = com.jayway.jsonpath.JsonPath.read(
						expected, string);
				assertThat(actualValue.toString(), equalTo(expectedValue.toString()));
			}
		}
	}
	
	public RequestSpecification givenThat() {
		PrintStream requestLogFile = null;
		try {
			requestLogFile = new PrintStream("log.txt");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		LogConfig requestLogConfig = new LogConfig(requestLogFile, true);
		RestAssured.config = RestAssuredConfig.newConfig().logConfig(requestLogConfig);
		return RestAssured.given().response().log().all().request().log().all();
	}
	
	public List<String> getXPaths(String json) {
		Configuration conf = Configuration.builder()
				.options(Option.AS_PATH_LIST).build();
		return using(conf).parse(json).read("$..*");
	}
	
	public String getActualResponse(Request request) {

		if (request.getMethod().equals("get")) {
			return RestAssured.get(request.getUrl()).asString();
		} else if (request.getMethod().equals("post")) {
			return givenThat().post(request.getUrl()).asString();
		} else {
			return givenThat().get(request.getUrl()).asString();
		}
	}
}
