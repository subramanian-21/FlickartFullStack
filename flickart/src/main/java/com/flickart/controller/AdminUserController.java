package com.flickart.controller;
import java.util.HashMap;
import java.util.Map;

import com.flickart.dao.AdminUserDao;
import com.flickart.model.AdminUser;
import com.flickart.model.User;
import com.flickart.util.JwtUtil;
import com.google.gson.Gson;
import io.jsonwebtoken.Claims;

public class AdminUserController {

	public static Map<Object, Object> login(String username, String password) throws Exception {	
			AdminUser user = AdminUserDao.validateUser(username, password);
			if(user == null) {
				throw new Exception("Invalid email or password");
			}
			Map<Object, Object> map = new HashMap<>();
			map.put("user", user);
			map.put("accessToken", JwtUtil.createAccessTokenAdmin(user));
			map.put("refreshToken", JwtUtil.createRefreshTokenAdmin(user));
			return map;
	}
	public static AdminUser getUser(String accessToken) throws Exception{
		Gson gson = new Gson();

		AdminUser user = null;
		try {
			user = gson.fromJson(JwtUtil.validateTokenAdmin(accessToken), AdminUser.class);
		}catch (Exception e){
			e.printStackTrace();
		}
		return user;
	}
}
