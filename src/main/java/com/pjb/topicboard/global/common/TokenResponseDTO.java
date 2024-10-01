package com.pjb.topicboard.global.common;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "토큰 DTO")
public record TokenResponseDTO(
        @Schema(description = "access token")
        String accessToken,
        @Schema(description = "refresh token")
        String refreshToken
) {
}
