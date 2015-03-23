package com.testcase;

import com.fasterxml.jackson.databind.JsonNode;

public class Response {
	private String status;
	private JsonNode content;
	
	public String getStatus() {
		return status;
	}
	
	public JsonNode getContent() {
		return content;
	}

}
