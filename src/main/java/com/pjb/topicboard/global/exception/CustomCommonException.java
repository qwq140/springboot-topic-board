package com.pjb.topicboard.global.exception;

import com.pjb.topicboard.global.common.ErrorEnum;
import lombok.Getter;

@Getter
public class CustomCommonException extends RuntimeException{

    private ErrorEnum errorEnum;

    public CustomCommonException(ErrorEnum errorEnum) {
        super(errorEnum.getMessage());
        this.errorEnum = errorEnum;
    }
}
