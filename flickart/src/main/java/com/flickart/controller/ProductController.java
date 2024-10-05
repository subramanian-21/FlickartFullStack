package com.flickart.controller;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.flickart.dao.CategoriesDao;
import com.flickart.dao.ProductDao;
import com.flickart.dao.ReviewDao;
import com.flickart.dao.UserDao;
import com.flickart.model.Product;
import com.flickart.model.Review;
import com.flickart.model.User;
import com.flickart.util.UniqueId;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

public class ProductController {
	public static int addAllProduct(JsonArray productJsonArray) throws ClassNotFoundException, SQLException {
		Gson gson = new Gson();
		 for(JsonElement jsonEle : productJsonArray) {
        	 Product product = gson.fromJson(jsonEle, Product.class);
        	 ProductDao.addProduct(product);
         }
		 
		 return productJsonArray.size();
	}
	public static int addProduct(Product product) throws ClassNotFoundException, SQLException {
		ProductDao.addProduct(product);
		return 1;
	}
	public static void updateProduct(String productId, Product product) {
		
	}
	public static Map<Object, Object> getAllProducts(int limit, int offset, String searchString) throws SQLException, ClassNotFoundException {
		Map<Object, Object> map = ProductDao.getAllProducts(limit, offset, searchString);
		map.put("categories", CategoriesDao.searchByCategory(searchString));
		return map;
	}

	public static Map<Object, Object> getAllProductsByCategory(int limit, int offset, String category) throws SQLException, ClassNotFoundException {
		Map<Object, Object> map = ProductDao.getProductsByCategory(limit, offset, category);
		map.put("categories", CategoriesDao.getAllCategories());
		return map;
	}


	public static Map<Object, Object> getAllProductsAdmin(int limit, int offset, String searchString) throws SQLException, ClassNotFoundException {
		int totalProductCount = ProductDao.getProductsCount(searchString);

		Map<Object, Object> map = new HashMap<>();
		map.put("limit", limit);
		map.put("offset", offset);
		map.put("totalCount", totalProductCount);
		boolean hasNext = false;
		if(offset + limit < totalProductCount) {
			hasNext = true;
		}
		map.put("hasNext", hasNext);
		map.put("products", ProductDao.getAllProductsAdmin(limit, offset, searchString));
		map.put("categories", CategoriesDao.searchByCategory(searchString));

		return map;
	}
	public static Review addProductReview(String accessToken,Review review) throws Exception {
		User user = UserController.getUser(accessToken);
		String uniqueId  = UniqueId.getUniqueId();
		review.setUserId(user.getUserId());
		review.setReviewId(uniqueId);
		ReviewDao.addReview(review);
		return review;
	}

}
