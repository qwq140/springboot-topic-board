package com.pjb.topicboard.global.config.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.pjb.topicboard.global.exception.Exception401;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
public class JwtFilter extends OncePerRequestFilter {

    final JwtProvider provider;

    public JwtFilter(JwtProvider provider) {
        this.provider = provider;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        log.debug("JwtFilter 동작");
        String jwtHeader = request.getHeader(JwtConstants.HEADER);

        // header에 토큰이 없으면
        if(jwtHeader == null || jwtHeader.startsWith(JwtConstants.TOKEN_PREFIX)) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            String jwt = jwtHeader.replace(JwtConstants.TOKEN_PREFIX, "");
            DecodedJWT decodedJWT = provider.verify(jwt, TokenType.ACCESS_TOKEN);
            SecurityContextHolder.getContext().setAuthentication(provider.getAuthentication(decodedJWT));
        } catch (Exception401 e) {
            request.setAttribute("exception", e.getMessage());
        } catch (JWTVerificationException e) {
            request.setAttribute("exception", "토큰 검증 실패");
        } finally {
            filterChain.doFilter(request, response);
        }
    }
}
