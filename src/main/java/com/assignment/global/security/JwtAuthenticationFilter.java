package com.assignment.global.security;

import com.assignment.global.security.dto.UserInfo;
import com.assignment.global.security.jwt.JwtTokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtTokenService jwtTokenService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if(noAuthentication(request.getRequestURI())) {
            filterChain.doFilter(request, response);
            return;
        }
        String token = request.getHeader("Authorization");
        if(token == null || token.isEmpty()) {
            throw new RuntimeException("jwt token not found");
        }

        if(!token.startsWith("Bearer ")) {
            throw new RuntimeException("jwt token is invalid");
        }

        token = token.substring(7);
        UserInfo userInfo = jwtTokenService.parseAccessToken(token);
        if(userInfo != null) {
            setAuthentication(userInfo);
        }
        filterChain.doFilter(request, response);
    }

    private boolean noAuthentication(String url) {
        return url.startsWith("/user/login")
                || url.equals("/user/sign-up");
    }

    private void setAuthentication(UserInfo userInfo) {
        CustomUserDetails userDetails = new CustomUserDetails(userInfo);
        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}
