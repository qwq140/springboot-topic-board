package com.pjb.topicboard.domain.board.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record BoardUpdateRequestDTO(
        @NotBlank(message = "게시판 이름을 입력해주세요.")
        @Size(max = 20, message = "게시판 이름은 최대 20자까지 가능합니다.")
        String name,
        String description
) {
}
