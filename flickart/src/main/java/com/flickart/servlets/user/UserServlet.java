package com.flickart.servlets.user;

import com.flickart.controller.AdminUserController;
import com.flickart.controller.UserController;
import com.flickart.model.AdminUser;
import com.flickart.model.User;
import com.flickart.util.JsonUtil;
import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Map;

@WebServlet("/api/user/*")
public class UserServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        String path = req.getPathInfo();
        Gson gson = new Gson();
        Map<Object, Object> map = null;
        try{
            if(path.equals("/login")){
                User user = gson.fromJson(req.getReader(), User.class);
                map = UserController.login(user.getEmail(),user.getPassword());
            }else if(path.equals("/signup")){
                 User user = gson.fromJson(req.getReader(), User.class);
                map = UserController.createAccount(user);
            }else if(path.startsWith("/validate")){

            }else {
                throw new ServletException("Invalid path");
            }
            JsonUtil.sendJsonResponse(200, resp, map);
        }catch (Exception e){
            JsonUtil.showError(resp, e);
        }
    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        try {
            String authHeader = req.getHeader("Authorization");
            String accessToken = authHeader.substring(7);
            AdminUserController.getUser(accessToken);
            JsonUtil.sendJsonResponse(200, resp, UserController.getUser(accessToken));
        }catch (Exception e) {
            JsonUtil.showError(resp, e);
        }
    }
}
