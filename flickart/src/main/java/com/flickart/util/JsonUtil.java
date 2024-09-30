package com.flickart.util;
import java.util.Map;
import java.util.HashMap;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


public class JsonUtil {
	
	public static String generateJsonFromDto(Object dto) {
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
		Map<String, Object> map = new HashMap<>();
        map.put("success", true);
        map.put("response", dto); 
        return gson.toJson(map);
	}
	public static String getJsonString(boolean status, Object message) {
		Map<String, Object> map = new HashMap<>();
		map.put("success", status);
		map.put("response", message);
		
		return new Gson().toJson(map);
	}
}
