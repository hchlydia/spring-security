package com.example.MySpringSecurity.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Date;

@Slf4j
public class Filter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String uri = request.getRequestURI();

        if ("/userLogin".equals(uri)) {
            String userAgent = request.getHeader("User-Agent");
            Date now = new Date();

            log.info("使用者於" + now + "嘗試從" + userAgent + "登入");
        }

        filterChain.doFilter(request, response);
    }
}
