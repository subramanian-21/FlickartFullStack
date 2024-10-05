package com.flickart.filter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.flickart.util.JsonUtil;
import com.flickart.util.JwtUtil;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebFilter("/api/*") // apply to specific endpoints
public class JwtFilter implements Filter {
    private static final String path = "/flickart_war_exploded";
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain){
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

    	try{
            String authHeader = req.getHeader("Authorization");
            String requestURI = req.getRequestURI();

            System.out.println(requestURI);
            List<String> unblockedPaths = new ArrayList<>();
            unblockedPaths.add(path+"/api/admin/login");
            unblockedPaths.add(path+"/api/user/login");
            unblockedPaths.add(path+"/api/user/signup");

            if (unblockedPaths.contains(requestURI) || requestURI.startsWith(path+"/api/user/products")) {
                chain.doFilter(request, response);
                return;
            }
            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                String token = authHeader.substring(7);
                System.out.println(requestURI+" req uri");
                if(!requestURI.contains("user")){
                    if(JwtUtil.validateTokenAdmin(token) != null) {
                        chain.doFilter(request, response);
                        return;
                    }
                }
                else {
                    if(JwtUtil.validateTokenUser(token) != null) {
                        chain.doFilter(request, response);
                        return;
                    }
                }
            }
            res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            res.getWriter().write(JsonUtil.getJsonString(false, "Invalid JWT token"));
            res.getWriter().flush();
        }catch (Exception e){
            JsonUtil.showError(res,e);
            e.printStackTrace();
        }
    	

    }
}
