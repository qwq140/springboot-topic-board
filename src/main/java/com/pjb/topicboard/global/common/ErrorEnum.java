package com.pjb.topicboard.global.common;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorEnum {
    /* COMMON */
    VALIDATION_ERROR(HttpStatus.BAD_REQUEST, "VALIDATION-001", "유효성 검사 실패"),

    /* AUTH */
    INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "AUTH-001", "올바르지 않은 토큰입니다."),
    UNAUTHORIZED_USER(HttpStatus.UNAUTHORIZED, "AUTH-002", "인증되지 않은 사용자입니다."),
    FORBIDDEN_USER(HttpStatus.FORBIDDEN, "AUTH-003", "접근 권한이 없습니다."),

    /* USER */
    INVALID_CREDENTIALS(HttpStatus.NOT_FOUND, "USER-001","아이디 또는 비밀번호가 틀렸습니다."),
    USERNAME_ALREADY_EXIST(HttpStatus.BAD_REQUEST, "USER-002", "이미 존재하는 아이디 입니다."),
    NICKNAME_ALREADY_EXIST(HttpStatus.BAD_REQUEST, "USER-003", "이미 존재하는 닉네임 입니다."),

    /* BOARD */
    BOARD_ALREADY_EXIST(HttpStatus.BAD_REQUEST, "BOARD-001", "이미 존재하는 게시판 입니다."),
    BOARD_NOT_FOUND(HttpStatus.NOT_FOUND, "BOARD-002", "존재 하지 않은 게시판 입니다.");

    private final HttpStatus status;
    private final String errorCode;
    private final String message;
}
