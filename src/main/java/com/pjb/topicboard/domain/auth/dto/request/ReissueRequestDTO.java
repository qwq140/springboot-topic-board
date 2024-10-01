package com.pjb.topicboard.domain.auth.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "토큰 재발급 요청 DTO")
public record ReissueRequestDTO(
        String refreshToken
) {
}
