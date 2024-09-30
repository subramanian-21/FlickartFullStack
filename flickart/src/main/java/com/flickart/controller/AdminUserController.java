package com.flickart.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import com.flickart.dao.AdminUserDao;
import com.flickart.model.AdminUser;
import com.flickart.util.JwtUtil;

public class AdminUserController {
	
	public static Map<Object, Object> login(String username, String password) throws Exception {	
			AdminUser user = AdminUserDao.validateUser(username, password);
			if(user == null) {
				throw new Exception("Invalid email or password");
			}
			Map<Object, Object> map = new HashMap<>();
			map.put("user", user);
			map.put("accessToken", JwtUtil.createAccessToken(user.getEmail()));
			map.put("refreshToken", JwtUtil.createRefreshToken(user.getEmail()));
			
			return map;
	}
}
