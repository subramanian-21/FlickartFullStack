package com.flickart.servlets.user;

import com.flickart.dao.CartDao;
import com.flickart.util.JsonUtil;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/api/user/cart/*")
public class CartServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) {
        try {
            Gson gson = new Gson();
            String pathInfo = req.getPathInfo();
            if(pathInfo.equals("/add")) {
                JsonObject jsonObject = gson.fromJson(req.getReader(), JsonObject.class);
                JsonElement productId = jsonObject.get("productId");
                JsonElement cartId = jsonObject.get("cartId");
                CartDao.addToCart(cartId.getAsString(), productId.getAsString());
            }else {
                throw  new ServletException("Invalid path");
            }
            JsonUtil.sendJsonResponse(200, res, "Added successfully");
        }catch (Exception e) {
            JsonUtil.showError(res, e);
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) {
        String pathInfo = req.getPathInfo();
        try {
            if(pathInfo.equals("/update")) {
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
