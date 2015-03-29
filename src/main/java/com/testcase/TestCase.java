package com.testcase;

public class TestCase {
	private String testrailid;
	private String name;
	private String description;

	private Request request;
	private Response response;

	public Response getResponse() {
		return response;
	}

	public String getTestrailid() {
		return testrailid;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public Request getRequest() {
		return request;
	}

}
