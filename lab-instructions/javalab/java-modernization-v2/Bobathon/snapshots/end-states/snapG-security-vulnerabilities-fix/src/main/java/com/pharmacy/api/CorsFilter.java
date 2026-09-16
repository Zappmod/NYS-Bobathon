package com.pharmacy.api;

import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerResponseContext;
import jakarta.ws.rs.container.ContainerResponseFilter;
import jakarta.ws.rs.ext.Provider;
import java.io.IOException;

@Provider
public class CorsFilter implements ContainerResponseFilter {
    
    // Security Fix: Restrict CORS to specific origins instead of wildcard
    private static final String ALLOWED_ORIGIN = "http://localhost:3000";
    
    @Override
    public void filter(ContainerRequestContext requestContext,
                      ContainerResponseContext responseContext) throws IOException {
        String origin = requestContext.getHeaderString("Origin");
        
        // Only allow requests from the specified origin
        if (origin != null && origin.equals(ALLOWED_ORIGIN)) {
            responseContext.getHeaders().add("Access-Control-Allow-Origin", origin);
            responseContext.getHeaders().add("Access-Control-Allow-Credentials", "true");
        }
        
        responseContext.getHeaders().add("Access-Control-Allow-Methods",
            "GET, POST, PUT, DELETE, OPTIONS");
        responseContext.getHeaders().add("Access-Control-Allow-Headers",
            "Content-Type, Authorization");
        responseContext.getHeaders().add("Access-Control-Max-Age", "3600");
    }
}