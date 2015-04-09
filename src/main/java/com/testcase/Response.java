package com.testcase;

import com.google.gson.JsonObject;

public class Response {

	private String status;
	private JsonObject content;

	public void setStatus(String status) {
		this.status = status;
	}

	public void setContent(JsonObject content) {
		this.content = content;
	}

	public String getStatus() {
		return status;
	}

	public JsonObject getContent() {
		return content;
	}

}
