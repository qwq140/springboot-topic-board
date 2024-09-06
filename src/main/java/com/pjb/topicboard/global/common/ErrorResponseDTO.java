package com.pjb.topicboard.global.common;

import lombok.Getter;

@Getter
public class ErrorResponseDTO<T> {
    private String errorCode;
    private String message;
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
