package com.pjb.topicboard.global.config.security;

public class UrlRoleMapping {
    public static final String[] ADMIN_POST_URLS = {
            "/api/boards",
    };

    public static final String[] ADMIN_PUT_URLS = {
            "/api/boards/{id}"
    };

    public static final String[] ADMIN_DELETE_URLS = {
            "/api/boards/{id}"
    };

    public static final String[] PUBLIC_GET_URLS = {
            "/api/boards",
            "/api/boards/{id}",
            "/api/boards/{id}/posts",
            "/api/posts/{id}",
            "/api/posts/{id}/comments",
            "/swagger-ui/**",
            "/v3/api-docs/**"
    };
}
