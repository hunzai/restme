package com;

import static com.jayway.jsonpath.JsonPath.using;
import static com.jayway.jsonpath.JsonPath.read;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.Option;
import com.jayway.restassured.RestAssured;
import com.jayway.restassured.filter.Filter;
import com.jayway.restassured.filter.log.ResponseLoggingFilter;
import com.jayway.restassured.specification.RequestSpecification;
import com.testcase.Request;
import com.testcase.TestCase;
import com.utils.MyRequestLoggerFilter;

public class MyRester {

	private static String logDirectoryPath = "logs";

	public MyRester() {

	}

	public static void assertJsonValues(TestCase testCase) {
		List<String> expectedJson = getXPaths(testCase.getResponse()
				.getContent().toString());

		String expected = testCase.getResponse().getContent().toString();
		String actual = getActualResponse(testCase.getRequest());

		for (String string : expectedJson) {
			Object actualValue = com.jayway.jsonpath.JsonPath.read(actual,
					string);
			if (actualValue instanceof String) {
				Object expectedValue = com.jayway.jsonpath.JsonPath.read(
						expected, string);
				assertThat(actualValue.toString(),
						equalTo(expectedValue.toString()));
			}
		}
	}

	public static RequestSpecification givenThat(String name) {
		PrintStream requestStream = null;
		PrintStream responseStream = null;
		File dir = new File(logDirectoryPath);
		if (!dir.exists()) {
			dir.mkdir();
		}

		try {
			requestStream = new PrintStream(logDirectoryPath + "/" + "request_"
					+ name + ".txt");
			responseStream = new PrintStream(logDirectoryPath + "/"
					+ "response_" + name + ".txt");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		MyRequestLoggerFilter requestFilter = new MyRequestLoggerFilter(
				requestStream);
		ResponseLoggingFilter responseFilter = new ResponseLoggingFilter(
				responseStream);

		List<Filter> filters = new ArrayList<Filter>();
		filters.add(requestFilter);
		filters.add(responseFilter);
		RequestSpecification requestSpecification = RestAssured.with()
				.filters(filters).log().all();
		return requestSpecification;
	}

	public static List<String> getXPaths(String json) {
		Configuration conf = Configuration.builder()
				.options(Option.AS_PATH_LIST).build();
		return using(conf).parse(json).read("$..*");
	}

	public static Object getElement(String json, String xpath){
		Object document = Configuration.defaultConfiguration().jsonProvider().parse(json);
		return read(document, xpath);
	}

	public static String getActualResponse(Request request) {
		String method = request.getMethod();
		if (request.getMethod().equals("get")) {
			return givenThat(method).get(request.getUrl()).asString();
		} else if (request.getMethod().equals("post")) {
			return givenThat(method).post(request.getUrl()).asString();
		} else {
			return givenThat(method).get(request.getUrl()).asString();
		}
	}
}
