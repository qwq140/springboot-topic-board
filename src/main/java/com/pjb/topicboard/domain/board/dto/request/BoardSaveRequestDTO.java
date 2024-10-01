package com.pjb.topicboard.domain.board.dto.request;

import com.pjb.topicboard.model.board.BoardEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(description = "게시판 생성 요청 DTO")
public record BoardSaveRequestDTO(
        @Schema(description = "최대 20자 사용")
        @NotBlank(message = "게시판 이름을 입력해주세요.")
        @Size(max = 20, message = "게시판 이름은 최대 20자까지 가능합니다.")
        String name,
        String description
) {
    public BoardEntity toEntity() {
        return BoardEntity.builder()
                .name(name)
                .description(description)
                .build();
    }
}
