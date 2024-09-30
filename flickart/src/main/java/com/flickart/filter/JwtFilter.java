package com.flickart.filter;
import java.io.IOException;
import com.flickart.util.JsonUtil;
import com.flickart.util.JwtUtil;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebFilter("/api/*") // apply to specific endpoints
public class JwtFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
    	
    	
        HttpServletRequest req = (HttpServletRequest) request;
        String authHeader = req.getHeader("Authorization");
 String requestURI = req.getRequestURI();
        System.out.println(requestURI);
        
        if (requestURI.equals("/Flickart/api/admin/login")) {
            chain.doFilter(request, response); 
            return;
        }

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            if(JwtUtil.validateToken(token)) {
            	chain.doFilter(request, response);
            	return;
            }
        }

        HttpServletResponse res = (HttpServletResponse) response;
        res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        res.getWriter().write(JsonUtil.getJsonString(false, "Invalid JWT token"));
        res.flushBuffer();
    }
}
