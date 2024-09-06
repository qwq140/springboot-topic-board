package com.pjb.topicboard.global.exception.handler;

import com.pjb.topicboard.global.common.ResponseDTO;
import com.pjb.topicboard.global.exception.CustomValidationException;
import com.pjb.topicboard.global.exception.Exception400;
import com.pjb.topicboard.global.exception.Exception401;
import com.pjb.topicboard.global.exception.Exception404;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(Exception400.class)
    protected ResponseEntity<?> exception400(Exception400 e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDTO<>(-1, e.getMessage()));
    }

    @ExceptionHandler(Exception401.class)
    protected ResponseEntity<?> exception401(Exception401 e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ResponseDTO<>(-1, e.getMessage()));
    }

    @ExceptionHandler(Exception404.class)
    protected ResponseEntity<?> exception404(Exception404 e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseDTO<>(-1, e.getMessage()));
    }

    @ExceptionHandler(CustomValidationException.class)
    protected ResponseEntity<?> validationException(CustomValidationException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDTO<>(-1, e.getMessage(), e.getErrorMap()));
    }
}
