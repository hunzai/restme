package com.processor;

import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import com.MyRester;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.testcase.Response;
import com.testcase.TestManager;

public class JsonProcessor {

	final static String ORIGINAL_DATA_ELEMENT_NAME = "originalData";
	final static String TEST_DATA_ELEMENT_NAME = "testData";

	final static String TEST_DATA_ATTRIBUTE_IS_DYNAMIC = "isDynamic";
	final static String TEST_DATA_ATTRIBUTE_WHEN_LAST_MODIFIED = "lastModified";
	final static String TEST_DATA_ATTRIBUTE_CHANGE_COUNTER = "changeCounter";

	final static String JSON_RESPONSE_NAME = "response";
	final static String JSON_CONTENT_NAME = "content";

	TestManager manager = new TestManager();

	public static void log(String msg) {
		System.out.println(msg);
	}

	public Object addTestDataToTestCaseContent(String jsonFile) {

		String content = this.manager.readFile(jsonFile);
		MyRester.createAndWrite("expected.json", buildExpectedJson(content)
				.toString());

		String actualJson = this.manager.readFile(jsonFile);
		MyRester.createAndWrite("actual.json", buildActualJson(actualJson)
				.toString());

		// List<String> actualXpath = MyRester.getXPaths("test_case_1.json");

		return "";
	}

	private JsonElement buildActualJson(String content) {
		JsonObject actualJsonObject = this.manager.getJsonObject(content);
		String actualContent = MyRester.getActualResponse(this.manager
				.getTestCase("jsons/test_case.json").getRequest());
		JsonObject actualContentJsonObject = this.manager
				.getJsonObject(actualContent);
		addTestData(actualContentJsonObject);
		Response response = new Response();
		response.setStatus("200");
		response.setContent(actualContentJsonObject);
		addResponse(response, actualJsonObject);
		return actualJsonObject;
	}

	private void addResponse(Response response, JsonObject jsonObject) {
		jsonObject.remove("response");
		// JsonObject newContent = new JsonObject();
		// newContent.add(JSON_RESPONSE_NAME, response.getContent());

		JsonObject newResponse = new JsonObject();
		newResponse.addProperty("status", response.getStatus());
		newResponse.add(JSON_CONTENT_NAME, response.getContent());

		jsonObject.add(JSON_RESPONSE_NAME, newResponse);
	}

	private JsonElement buildExpectedJson(String content) {
		JsonElement expectedJsonElement = this.manager.getJsonObject(content);
		JsonElement expectedContent = getContent(expectedJsonElement);
		addTestData(expectedContent);
		// expectedJsonElement.getAsJsonObject().add(JSON_CONTENT_NAME,
		// addTestData(expectedContent) );
		return expectedJsonElement;
	}

	private JsonElement getContent(JsonElement json) {
		return (json.getAsJsonObject().get(JSON_RESPONSE_NAME)
				.getAsJsonObject().get(JSON_CONTENT_NAME));
	}

	private JsonObject addTestData(JsonElement element) {
		JsonObject content = element.getAsJsonObject();
		Iterator<Entry<String, JsonElement>> keys = content.entrySet()
				.iterator();
		while (keys.hasNext()) {
			Entry<String, JsonElement> entry = keys.next();
			JsonElement json = entry.getValue();
			if (json.isJsonPrimitive()) {
				JsonObject newJson = buildTestData(entry);
				entry.setValue(newJson);
			} else if (json.isJsonObject()) {
				addTestData(json.getAsJsonObject());
			} else if (json.isJsonArray()) {
				for (JsonElement item : json.getAsJsonArray()) {
					addTestData(item.getAsJsonObject());
				}
			}
		}
		return content;
	}

	private JsonObject buildTestData(Entry<String, JsonElement> entry) {
		JsonObject element = new JsonObject();
		element.add(ORIGINAL_DATA_ELEMENT_NAME, entry.getValue());
		element.add(TEST_DATA_ELEMENT_NAME, buildTestElement());
		return element;
	}

	public JsonObject getTestContent(JsonObject json) {
		String root = MyRester.getXPaths(json.toString()).get(0);
		Object content = MyRester.getElement(json.toString(), root);
		Gson gson = new Gson();
		String contentStr = gson.toJson(content);
		JsonObject contentObject = gson.fromJson(contentStr, JsonObject.class);
		return contentObject;
	}

	public JsonElement buildTestElement() {
		JsonObject tester = new JsonObject();
		tester.addProperty(TEST_DATA_ATTRIBUTE_IS_DYNAMIC, false);
		tester.addProperty(TEST_DATA_ATTRIBUTE_WHEN_LAST_MODIFIED, "");
		tester.addProperty(TEST_DATA_ATTRIBUTE_CHANGE_COUNTER, 0);
		return tester;
	}

}
