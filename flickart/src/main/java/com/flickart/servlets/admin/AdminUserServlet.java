package com.flickart.servlets.admin;

import java.io.IOException;
import java.util.Map;

import com.flickart.controller.AdminUserController;
import com.flickart.model.AdminUser;
import com.flickart.util.JsonUtil;
import com.google.gson.Gson;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@jakarta.servlet.annotation.WebServlet("/api/admin/login")
public class AdminUserServlet extends HttpServlet {
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
		try {
			Gson gson = new Gson();
			AdminUser user = gson.fromJson(req.getReader(), AdminUser.class);
			
			Map<Object, Object> map = AdminUserController.login(user.getEmail(),user.getPassword());
			
			resp.setStatus(200);
			resp.getWriter().print(JsonUtil.getJsonString(true, map));
			resp.flushBuffer();
		} catch (Exception e) {
			try {
				resp.setStatus(400);
				resp.getWriter().print(JsonUtil.getJsonString(false, e.getMessage()));
				resp.flushBuffer();
			} catch (IOException e1) {
				
				e1.printStackTrace();
			}
		}
		
	}
}