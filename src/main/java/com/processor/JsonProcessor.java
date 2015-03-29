package com.processor;

import java.util.Iterator;
import java.util.Map.Entry;

import org.json.JSONObject;

import com.MyRester;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.testcase.TestManager;

import static com.jayway.restassured.path.json.JsonPath.from;

public class JsonProcessor {

	TestManager manager = new TestManager();

	public void addAttribute() {
		JsonObject json = this.manager.getTestCase("jsons/test_case.json")
				.getResponse().getContent();

		// System.out.println(json);

		// List<String> xpaths = MyRester.getXPaths(json.toString());
		// for (String string : xpaths) {
		// System.out.println();
		// }

		// JSONObject obj = new JSONObject(json);

		//
		Iterator<Entry<String, JsonElement>> keys = json.entrySet().iterator();
		while (keys.hasNext()) {
			Entry<String, JsonElement> attr = keys.next();
			if (!attr.getValue().isJsonObject()) {
				JsonObject mm = new JsonObject();
				mm.add("value", attr.getValue());
				
				JsonObject tester = new JsonObject();
				tester.addProperty("dynamic", true);
				mm.add("tester", tester);
				
				attr.setValue(mm);
			}
		}
		System.out.println(json);

		// String newPath = string.replaceAll("\\[", ".").replaceAll("\\]",
		// "").replaceAll("'", "");

		//
		// if(!(value instanceof LinkedHashMap)){
		// }
	}

}
