package com.flickart.util;
import java.util.Map;
import java.util.HashMap;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import jakarta.servlet.http.HttpServletResponse;


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
	public static void showError(HttpServletResponse res, Exception e) {
		try {
			res.setStatus(400);
			res.getWriter().print(JsonUtil.getJsonString(false, e.getMessage()));
			res.getWriter().flush();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}
	public static void sendJsonResponse(int status ,HttpServletResponse res, Object json) {
		try {
			res.setStatus(status);
			res.setContentType("application/json");
			boolean success = true;
			if(status >= 400) {
				success = false;
			}
			res.getWriter().print(JsonUtil.getJsonString(success, json));
			res.getWriter().flush();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}
	public static String stringifyDto(Object object) {
		return new Gson().toJson(object);
	}
}
