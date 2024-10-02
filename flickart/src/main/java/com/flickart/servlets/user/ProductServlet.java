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

                res.setStatus(200);
                res.getWriter().print(JsonUtil.getJsonString(true, ProductController.getAllProducts(limit, offset)));
                res.flushBuffer();
            }else {
                String productId = path.substring(1);
                res.setStatus(200);
                res.getWriter().print(JsonUtil.getJsonString(true, ProductDao.getProduct(productId)));
                res.flushBuffer();
            }
        } catch (Exception e) {
            JsonUtil.showError(res, e);
        }
    }

}
