package com.flickart.servlets.admin;

import java.io.IOException;

import com.flickart.controller.ProductController;
import com.flickart.dao.ProductDao;
import com.flickart.model.Product;
import com.flickart.util.JsonUtil;
import com.google.gson.Gson;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

@WebServlet("/api/admin/products/*")
public class ProductServlet extends HttpServlet{
	
		public void doPost(HttpServletRequest req, HttpServletResponse res) {
		String path = req.getPathInfo();
		Gson gson = new Gson();
		try {
			int rowCount = 0;
			if(path.equals("/add")) {
				JsonObject jsonObject = gson.fromJson(req.getReader(), JsonObject.class);
				JsonObject productJson = jsonObject.getAsJsonObject("product");
				Product product = gson.fromJson(productJson, Product.class);
		        rowCount = ProductController.addProduct(product);
			}else if(path.equals("/addAll")){
				  JsonObject jsonObject = gson.fromJson(req.getReader(), JsonObject.class);
			      JsonArray jsonArray = jsonObject.getAsJsonArray("products");
			         
			      rowCount = ProductController.addAllProduct(jsonArray);
			}else {
				throw new Exception("Path not found");
			}
			JsonUtil.sendJsonResponse(200, res, rowCount+" products added");
	         
		}catch (Exception e) {
			JsonUtil.showError(res, e);
		}
	}
		@Override
		protected void doPut(HttpServletRequest req, HttpServletResponse res) {
			String path = req.getPathInfo();
			try {
				String productId = path.substring(1);
				Gson gson = new Gson();
				Product product = gson.fromJson(req.getReader(), Product.class);
				ProductDao.updateProduct(productId, product);
				JsonUtil.sendJsonResponse(200, res, "updated "+productId);

			} catch (Exception e) {
				JsonUtil.showError(res, e);
			}
		}
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res) {
		String path = req.getPathInfo();

			try {
				if(path.equals("/getAll")) {
					int limit = Integer.parseInt(req.getParameter("limit"));
					int offset = Integer.parseInt(req.getParameter("offset"));
					String search = req.getParameter("search");
					JsonUtil.sendJsonResponse(200, res, ProductController.getAllProductsAdmin(limit, offset, search));
				}else {
					String productId = path.substring(1);
					JsonUtil.sendJsonResponse(200, res, ProductDao.getProductAdmin(productId));
				}
			} catch (Exception e) {
				JsonUtil.showError(res, e);
			}
	}

	@Override
	protected void doDelete(HttpServletRequest req, HttpServletResponse res) {
		try{
			String path = req.getPathInfo();
			String productId = path.substring(1);
			ProductDao.deleteProduct(productId);
			JsonUtil.sendJsonResponse(200, res, "deleted "+productId);
		}catch(Exception e) {
			JsonUtil.showError(res, e);
		}
	}

}
