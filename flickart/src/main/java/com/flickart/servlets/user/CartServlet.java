package com.flickart.servlets.user;

import com.flickart.dao.CartDao;
import com.flickart.util.JsonUtil;
import com.google.gson.Gson;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/api/user/cart/*")
public class CartServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) {
        try {
            String pathInfo = req.getPathInfo();
            if(pathInfo.equals("/add")) {
                String productId = req.getParameter("productId");
                String userId = req.getParameter("userId");
                CartDao.addToCart(userId, productId);
            }else {
                throw  new ServletException("Invalid path");
            }
            JsonUtil.sendJsonResponse(401, res, "Added successfully");
        }catch (Exception e) {
            JsonUtil.showError(res, e);
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) {
        String pathInfo = req.getPathInfo();
        try {
            if(pathInfo.equals("/add")) {
                String quantity = req.getParameter("quantity");
                String productId = req.getParameter("productId");
                String cartId = req.getParameter("cartId");
                CartDao.updateProductCount(cartId, Integer.parseInt(quantity), productId);
            }else  {
                throw  new ServletException("Invalid path");
            }
            JsonUtil.sendJsonResponse(200, resp, "Product updated successfully");
        }catch (Exception e) {
            JsonUtil.showError(resp, e);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp)  {
        try {
            String pathInfo = req.getPathInfo();
            String quantity = req.getParameter("quantity");
            String productId = req.getParameter("productId");
            String cartId = req.getParameter("cartId");
            CartDao.removeFromCart(cartId, productId, Integer.parseInt(quantity));
            JsonUtil.sendJsonResponse(200, resp, "Deleted successfully");
        }catch (Exception e) {
            JsonUtil.showError(resp, e);
        }
    }
}
