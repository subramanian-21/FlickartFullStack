package com.flickart.controller;

import com.flickart.dao.UserDao;
import com.flickart.model.AdminUser;
import com.flickart.model.User;
import com.flickart.util.JwtUtil;
import com.google.gson.Gson;
import io.jsonwebtoken.Claims;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class UserController {

    public static Map<Object, Object> login(String email, String password) throws  SQLException , ClassNotFoundException{
        User user = UserDao.validateUser(email, password);
        if(user == null){
            throw new SQLException("Invalid email or password");
        }
        Map<Object, Object> map = new HashMap<Object, Object>();

        map.put("user", user);
        map.put("accessToken", JwtUtil.createAccessToken(email));
        map.put("refreshToken", JwtUtil.createRefreshToken(email));

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
        map.put("accessToken", JwtUtil.createAccessToken(user.getEmail()));
        map.put("refreshToken", JwtUtil.createRefreshToken(user.getEmail()));
        return map;
    }
    public static User getUser(String accessToken) throws SQLException, ClassNotFoundException {
        Gson gson = new Gson();
        String userEmail = JwtUtil.validateToken(accessToken);
        return UserDao.getUserByEmail(userEmail);
    }

}
