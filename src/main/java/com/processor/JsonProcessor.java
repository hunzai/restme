package com.processor;

import java.util.Iterator;
import java.util.Map.Entry;

import com.MyRester;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.jayway.restassured.path.json.JsonPath;
import com.testcase.TestManager;

import static com.jayway.jsonpath.JsonPath.parse;

public class JsonProcessor {

	TestManager manager = new TestManager();

	public void addTestJsonElement(String jsonFile) {
		JsonObject json = this.manager.getTestCase(jsonFile)
				.getResponse().getContent();
		
		String ele  = new Gson().toJson(json);
		
		String root = MyRester.getXPaths(json.toString()).get(0);
		String jjson = parse(ele).read(root).toString();
		
		Iterator<Entry<String, JsonElement>> keys = json.entrySet().iterator();
		while (keys.hasNext()) {
			Entry<String, JsonElement> attr = keys.next();
			if (!attr.getValue().isJsonObject()) {
				JsonObject mm = new JsonObject();
				mm.add("value", attr.getValue());
				mm.add("tester", makeTestJsonElement());
				attr.setValue(mm);
			}
		}
		System.out.println(jjson);
	}
	
	
	public void addAnalyzeTest(String jsonFile) {

	}
	
	
	public JsonElement makeTestJsonElement(){
		JsonObject tester = new JsonObject();
		tester.addProperty("dynamic", true);
		return tester;
	}

}
