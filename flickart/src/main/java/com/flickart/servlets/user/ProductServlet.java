package com.flickart.servlets.user;

import com.flickart.controller.ProductController;
import com.flickart.dao.ProductDao;
import com.flickart.util.JsonUtil;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/api/user/products/*")
public class ProductServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) {
        String path = req.getPathInfo();

        try {
            if(path.equals("/getAll")) {
                int limit = Integer.parseInt(req.getParameter("limit"));
                int offset = Integer.parseInt(req.getParameter("offset"));
                String search = req.getParameter("search");
                String category = req.getParameter("category");
                if(category != null && !category.isEmpty()) {
                    JsonUtil.sendJsonResponse(200, res, ProductController.getAllProductsByCategory(limit, offset, category));
                }else {
                    JsonUtil.sendJsonResponse(200, res, ProductController.getAllProducts(limit, offset, search));
                }
            }
            else {
                String productId = path.substring(1);
                JsonUtil.sendJsonResponse(200, res,  ProductDao.getProduct(productId));
            }
        } catch (Exception e) {
            JsonUtil.showError(res, e);
        }
    }

}
