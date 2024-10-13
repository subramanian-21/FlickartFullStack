package com.flickart.filter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
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
            Enumeration<String> headerNames = req.getHeaderNames();
            while (headerNames.hasMoreElements()) {
                String headerName = headerNames.nextElement();
                System.out.println(headerName + ": " + req.getHeader(headerName));
            }
            System.out.println();
            System.out.println();
            String authHeader = req.getHeader("authorization");
            String requestURI = req.getRequestURI();

            System.out.println(requestURI);
            List<String> unblockedPaths = new ArrayList<>();
            unblockedPaths.add(path+"/api/admin/login");
            unblockedPaths.add(path+"/api/user/login");
            unblockedPaths.add(path+"/api/user/signup");

            if (unblockedPaths.contains(requestURI) || requestURI.startsWith(path+"/api/user/products") || ((HttpServletRequest) request).getMethod().equalsIgnoreCase("OPTIONS")) {
                chain.doFilter(request, response);
                return;
            }
            System.out.println(((HttpServletRequest) request).getHeader("Authorization"));
            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                String token = authHeader.substring(7);
                System.out.println(requestURI+" req uri");
                System.out.println("validating admin");
                System.out.println("requestURI.contains(\"user\") "+requestURI.contains("user"));
                if(!requestURI.contains("user")){
                    if(JwtUtil.validateTokenAdmin(token) != null) {
                        System.out.println("validating admin");
                        chain.doFilter(request, response);
                        return;
                    }
                }
                else {
                    System.out.println("validating admin");
                    if(JwtUtil.validateTokenUser(token) != null) {
                        System.out.println("validating user");
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
