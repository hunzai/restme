package com.processor;

import java.util.Iterator;
import java.util.Map.Entry;

import com.MyRester;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.testcase.TestManager;

public class JsonProcessor {

	TestManager manager = new TestManager();

	public Object addTestJsonElement(String jsonFile) {
		JsonObject testCase = this.manager.getTestCase(jsonFile)
				.getResponse().getContent();
		
		JsonObject content = getContentObject(testCase);

		Iterator<Entry<String, JsonElement>> keys = content.entrySet().iterator();
		while (keys.hasNext()) {
			Entry<String, JsonElement> attr = keys.next();
			if (!attr.getValue().isJsonObject()) {
				JsonObject mm = new JsonObject();
				mm.add("value", attr.getValue());
				mm.add("tester", makeTestJsonElement());
				attr.setValue(mm);
			}
		}
		testCase.add("content", content);
		return testCase;
	}
	
	public JsonObject getContentObject(JsonObject json){
		String root = MyRester.getXPaths(json.toString()).get(0);
		Object content = MyRester.getElement(json.toString(), root);
		
		Gson gson = new Gson();
		String contentStr = gson.toJson(content);
		JsonObject contentObject = gson.fromJson(contentStr, JsonObject.class);
		
		return contentObject;
	}
	
	public void addAnalyzeTest(String jsonFile) {

	}
	
	
	public JsonElement makeTestJsonElement(){
		JsonObject tester = new JsonObject();
		tester.addProperty("dynamic", true);
		return tester;
	}

}
