package com.flickart.servlets.user;

import com.flickart.controller.ProductController;
import com.flickart.model.Review;
import com.flickart.util.JsonUtil;
import com.google.gson.Gson;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/api/user/product/review")
public class ReviewServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) {
        try{
            String authHeader = req.getHeader("Authorization");
            String accessToken = authHeader.substring(7);
            Gson gson = new Gson();
            Review review = gson.fromJson(req.getReader(), Review.class);
            ProductController.addProductReview(accessToken, review);
            JsonUtil.sendJsonResponse(200, res, "Review added successfully");
        }catch (Exception e){
            JsonUtil.showError(res, e);
        }
    }
}
