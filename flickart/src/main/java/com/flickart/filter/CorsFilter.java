package com.flickart.filter;

import com.flickart.util.JsonUtil;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter("/api/*")
public class CorsFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // Initialize any filter-specific resources here if necessary
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        try {
            // Set CORS headers
            httpResponse.setHeader("Access-Control-Allow-Origin", "http://localhost:3000"); // Restrict to the required domain
            httpResponse.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
            httpResponse.setHeader("Access-Control-Allow-Headers", "Authorization, Content-Type, Accept, Origin");
            httpResponse.setHeader("Access-Control-Allow-Credentials", "true");
            httpResponse.setHeader("Access-Control-Max-Age", "3600"); // Cache the preflight response for 1 hour

            // Handle preflight (OPTIONS) requests
            if ("OPTIONS".equalsIgnoreCase(httpRequest.getMethod())) {
                httpResponse.setStatus(HttpServletResponse.SC_OK);


                return;
            }

            // Continue processing other requests
            chain.doFilter(httpRequest, httpResponse);
        } catch (Exception e) {
            e.printStackTrace();
            JsonUtil.showError(httpResponse, e);
        }
    }

    @Override
    public void destroy() {
        // Clean up any resources here if necessary
    }
}
