package com.pjb.topicboard.global.config.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.pjb.topicboard.global.exception.Exception401;
import com.pjb.topicboard.model.user.UserEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtProvider {
    private final Algorithm algorithm = Algorithm.HMAC512(JwtConstants.SECRET);
    private final CustomUserDetailsService customUserDetailsService;

    public String createToken(UserEntity userEntity, TokenType tokenType) {
        String token = JWT.create()
                .withExpiresAt(createExpireDate(tokenType))
                .withSubject("topic-board")
                .withClaim("id", userEntity.getId())
                .withClaim("username", userEntity.getUsername())
                .withClaim("token-type", tokenType.name())
                .sign(algorithm);
        return token;
    }

    public DecodedJWT verify(String token, TokenType tokenType) {
        DecodedJWT decodedJWT = JWT.require(algorithm).build().verify(token);

        if(!decodedJWT.getClaim("token-type").asString().equals(tokenType.name())) {
            throw new Exception401("토큰 검증 실패");
        }

        return decodedJWT;
    }

    public Authentication getAuthentication(DecodedJWT decodedJWT) {
        String username = decodedJWT.getClaim("username").asString();
        var customUserDetails = customUserDetailsService.loadUserByUsername(username);
        return new UsernamePasswordAuthenticationToken(customUserDetails, "", customUserDetails.getAuthorities());
    }


    private Date createExpireDate(TokenType tokenType) {
        switch (tokenType) {
            case ACCESS_TOKEN -> {
                return new Date(System.currentTimeMillis() + JwtConstants.ACCESS_TOKEN_EXPIRATION_TIME);
            }
            case REFRESH_TOKEN -> {
                return new Date(System.currentTimeMillis() + JwtConstants.REFRESH_TOKEN_EXPIRATION_TIME);
            }
            default -> {
                return new Date(System.currentTimeMillis());
            }
        }
    }


}
