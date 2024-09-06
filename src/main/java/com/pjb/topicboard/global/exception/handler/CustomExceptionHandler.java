package com.pjb.topicboard.global.exception.handler;

import com.pjb.topicboard.global.common.ErrorEnum;
import com.pjb.topicboard.global.common.ErrorResponseDTO;
import com.pjb.topicboard.global.common.ResponseDTO;
import com.pjb.topicboard.global.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CustomExceptionHandler {
    @ExceptionHandler(CustomCommonException.class)
    protected ResponseEntity<?> commonException(CustomCommonException e) {
        return ResponseEntity.status(e.getErrorEnum().getStatus()).body(new ErrorResponseDTO<>(e.getErrorEnum()));
    }

    @ExceptionHandler(CustomValidationException.class)
    protected ResponseEntity<?> validationException(CustomValidationException e) {
        ErrorEnum error = ErrorEnum.VALIDATION_ERROR;
        return ResponseEntity.status(error.getStatus().value()).body(new ErrorResponseDTO<>(error, e.getErrorMap()));
    }
}
