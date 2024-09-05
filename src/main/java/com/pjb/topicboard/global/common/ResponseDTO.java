package com.pjb.topicboard.global.common;

import lombok.Getter;

@Getter
public class ResponseDTO<T> {
    private final Integer code;
    private final String message;
    private final T date;

    public ResponseDTO(Integer code, String message, T date) {
        this.code = code;
        this.message = message;
        this.date = date;
    }

    public ResponseDTO(Integer code, String message) {
        this.code = code;
        this.message = message;
        this.date = null;
    }
}
