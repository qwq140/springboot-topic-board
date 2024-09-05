package com.pjb.topicboard.global.common;

public record TokenResponseDTO(
        String accessToken,
        String refreshToken
) {
}
