package com.example.Bookstore.security;

import jakarta.servlet.ServletException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

//    @Override
//    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
//        // This is invoked when a user tries to access a secured REST resource without providing credentials
//        // We should just send a 401 Unauthorized response because there is no 'login page' to redirect to
//        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
//    }


    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        PrintWriter writer = response.getWriter();
        writer.println("HTTP Status 401 - Access Denied !! " + authException.getMessage());
    }
}
