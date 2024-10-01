package com.pjb.topicboard.global.common;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Schema(description = "공통 실패 응답 DTO")
@Getter
public class ErrorResponseDTO<T> {
    @Schema(description = "실패 응답 코드")
    private String errorCode;
    @Schema(description = "실패 응답 메시지")
    private String message;
    @Schema(description = "실패 응답 데이터 : 유효성 검사 실패 데이터")
    private T data;

    public ErrorResponseDTO(ErrorEnum errorEnum, T data) {
        this.errorCode = errorEnum.getErrorCode();
        this.message = errorEnum.getMessage();
        this.data = data;
    }

    public ErrorResponseDTO(ErrorEnum errorEnum) {
        this.errorCode = errorEnum.getErrorCode();
        this.message = errorEnum.getMessage();
        this.data = null;
    }
}
