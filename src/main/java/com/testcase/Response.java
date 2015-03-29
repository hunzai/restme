package com.testcase;


import com.google.gson.JsonObject;

public class Response {
	private String status;
	private JsonObject content;
	
	public String getStatus() {
		return status;
	}
	
	public JsonObject getContent() {
		return content;
	}

}
