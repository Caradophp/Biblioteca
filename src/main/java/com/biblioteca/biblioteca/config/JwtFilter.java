package com.biblioteca.biblioteca.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;
import java.util.Collections;

public class JwtFilter extends GenericFilterBean {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        String requestPath = httpRequest.getRequestURI();
        System.out.println("REQUISIÇÂO" + requestPath);
        if (requestPath.startsWith("/usuarios/login") || requestPath.startsWith("/public")) {
            chain.doFilter(request, response);
            return;
        }

        if (requestPath.startsWith("/error")) {
            httpResponse.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "ERRO NA REQUISIÇÂO");
            return;
        }

        String authorizationHeader = httpRequest.getHeader("Authorization");

        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token JWT ausente ou inválido!");
            return;
        }

        String token = authorizationHeader.substring(7);

        if (!JwtUtil.isTokenValid(token)) {
            httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token inválido ou expirado!");
            return;
        }

        String username = JwtUtil.extractUsername(token);
        String role = JwtUtil.extractRole(token);

        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                username,
                null,
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + role))
        );

        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpRequest));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        chain.doFilter(request, response);
    }
}
