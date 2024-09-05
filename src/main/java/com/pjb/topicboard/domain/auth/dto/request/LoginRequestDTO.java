package com.pjb.topicboard.domain.auth.dto.request;

import jakarta.validation.constraints.NotBlank;

public record LoginRequestDTO(
        @NotBlank(message = "아이디를 입력해주세요.")
        String username,
        @NotBlank(message = "비밀번호를 입력해주세요.")
        String password
) { }
