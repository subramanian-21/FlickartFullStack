package com.flickart.controller;

import com.flickart.dao.UserDao;
import com.flickart.model.AdminUser;
import com.flickart.model.User;
import com.flickart.util.JwtUtil;
import com.google.gson.Gson;
import io.jsonwebtoken.Claims;

import java.util.HashMap;
import java.util.Map;

public class UserController {
    public static void main(String[] args) {
        try {
            getUser("eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ7XCJ1c2VybmFtZVwiOlwic3VicmFtYW5pYW5cIixcImVtYWlsXCI6XCJtZWVuYW1hbmk5NDQ0QGdtYWlsLmNvbVwiLFwicGFzc3dvcmRcIjpcIjEyMTY5ODU3NTVcIixcInJvbGVcIjpcIlNVUEVSX0FETUlOXCJ9IiwiZXhwIjoxNzI3NzY0NTY3fQ.upgSdEeDXda3IXbVCwOLnuIcM6iTzQDS9Yus5vtnYQs");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static Map<Object, Object> login(String email, String password) throws  Exception {
        User user = UserDao.validateUser(email, password);
        Map<Object, Object> map = new HashMap<Object, Object>();
        if(user == null){
            throw new Exception("Invalid email or password");
        }
        map.put("user", user);
        map.put("accessToken", JwtUtil.createAccessToken(user));
        map.put("refreshToken", JwtUtil.createRefreshToken(user));

        return map;
    }
    public static Map<Object, Object> createAccount(User user) throws Exception{
        Map<Object, Object> map = new HashMap<>();
        if(user.getUserName() == null || user.getUserName().equals("") ){}
        if(user.getProfilePhoto() != null){
            UserDao.createUser(user.getUserName(),user.getEmail(), user.getPassword(), user.getProfilePhoto());
        }else {
            UserDao.createUser(user.getUserName(),user.getEmail(), user.getPassword());
        }

        map.put("user", user);
        map.put("accessToken", JwtUtil.createAccessToken(user));
        map.put("refreshToken", JwtUtil.createRefreshToken(user));
        return map;
    }
    public static User getUser(String accessToken){
        Gson gson = new Gson();
        return gson.fromJson(JwtUtil.validateToken(accessToken), User.class);
    }
}
