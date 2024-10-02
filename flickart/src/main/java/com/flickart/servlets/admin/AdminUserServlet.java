package com.flickart.servlets.admin;

import java.io.IOException;
import java.util.Map;

import com.flickart.controller.AdminUserController;
import com.flickart.model.AdminUser;
import com.flickart.util.JsonUtil;
import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@jakarta.servlet.annotation.WebServlet("/api/admin/*")
public class AdminUserServlet extends HttpServlet {
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
		String path = req.getPathInfo();
		try {
			if(path.equals("/login")){
				Gson gson = new Gson();
				AdminUser user = gson.fromJson(req.getReader(), AdminUser.class);

				Map<Object, Object> map = AdminUserController.login(user.getEmail(),user.getPassword());

				resp.setStatus(200);
				resp.getWriter().print(JsonUtil.getJsonString(true, map));
				resp.flushBuffer();
			}else {
				throw new ServletException("Invalid request");
			}

		} catch (Exception e) {
			JsonUtil.showError(resp, e);
		}
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
		try {
			String authHeader = req.getHeader("Authorization");
			String accessToken = authHeader.substring(7);
			AdminUserController.getUser(accessToken);
			resp.setStatus(200);
			resp.getWriter().print(JsonUtil.getJsonString(true, AdminUserController.getUser(accessToken)));
			resp.flushBuffer();
		}catch (Exception e) {
			JsonUtil.showError(resp, e);
		}
	}
}