package com.likelion.welcomekit.Utils;

import com.likelion.welcomekit.Service.UserService;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtTokenFilter extends OncePerRequestFilter {

    private final UserService userService;
    private final SecretKey secretKey = JwtTokenProvider.secretKey;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        final String authentication = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (authentication == null || !authentication.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = authentication.split(" ")[1];

        if (JwtTokenProvider.isExpired(token)){
            filterChain.doFilter(request,response);
            return;
        }

        Claims claims = JwtTokenProvider.extractClaims(token);
        String userName = claims.get("userName",String.class);
        String role = claims.get("role", String.class);
        log.info(userName);
        log.info(role);

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(
                        userName,
                        null,
                        // role 부여
                        List.of(new SimpleGrantedAuthority(role)));

        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        filterChain.doFilter(request, response);

    }
}