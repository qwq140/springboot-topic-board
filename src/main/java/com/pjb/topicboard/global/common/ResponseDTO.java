package com.pjb.topicboard.global.common;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Schema(description = "공통 응답 DTO")
@Getter
public class ResponseDTO<T> {
    @Schema(description = "응답 성공 여부")
    private final Integer code;
    @Schema(description = "응답 메시지")
    private final String message;
    @Schema(description = "응답 데이터")
    private final T data;

    public ResponseDTO(Integer code, String message, T date) {
        this.code = code;
        this.message = message;
        this.data = date;
    }

    public ResponseDTO(Integer code, String message) {
        this.code = code;
        this.message = message;
        this.data = null;
    }
}
