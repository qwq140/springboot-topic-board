package com.pjb.topicboard.global.config.security;

public final class JwtConstants {
    public static final String SECRET = "secret";
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER = "Authorization";
    public static final Long ACCESS_TOKEN_EXPIRATION_TIME = 1000L * 60 * 60 * 3; // 3시간
    public static final Long REFRESH_TOKEN_EXPIRATION_TIME = 1000L * 60 * 60 * 24 * 7; // 7일

    private JwtConstants(){}
}
