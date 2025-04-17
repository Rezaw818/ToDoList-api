package com.example.app.filter;


import com.example.common.exceptions.NotFoundException;
import com.example.dataacces.entity.User;
import com.example.dto.UserDto;
import com.example.services.UserService;
import com.example.utils.JwtUtil;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class JwtFilter implements Filter {

    private final UserService userService;
    private final JwtUtil jwtUtil;
    public static final String CURRENT_USER = "CURRENT_USER";

    @Autowired
    public JwtFilter(UserService userService, JwtUtil jwtUtil) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;

    }


    @Override
    @SneakyThrows
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain){
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        String token = extractTokenFromHeader(httpRequest);
        if (!token.isEmpty() && jwtUtil.validateToken(token)){
            String username = jwtUtil.getUsernameFromToken(token);


            httpRequest.setAttribute(CURRENT_USER, userService.getUserByUsername(username));

            filterChain.doFilter(request, response);
        }else {
            httpResponse.getWriter().write("Access Denied");
        }
    }

    private String extractTokenFromHeader(HttpServletRequest httpRequest) {
        String bearerToken = httpRequest.getHeader("Authorization");
        String prefix = "Bearer ";
        String token = "";
        if (bearerToken != null && bearerToken.startsWith(prefix)){
            token = bearerToken.substring(prefix.length());
        }
        return token;
    }
}
