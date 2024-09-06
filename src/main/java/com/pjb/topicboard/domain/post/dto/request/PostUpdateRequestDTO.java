package com.pjb.topicboard.domain.post.dto.request;

import com.pjb.topicboard.model.board.BoardEntity;
import com.pjb.topicboard.model.post.PostEntity;
import com.pjb.topicboard.model.user.UserEntity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record PostUpdateRequestDTO(
        @NotBlank(message = "게시글 제목을 입력해주세요.")
        @Size(max = 100, message = "게시글 제목은 최대 100자까지 가능합니다.")
        String title,
        @NotBlank(message = "게시글 내용을 입력해주세요.")
        String content
) {
    public PostEntity toEntity(BoardEntity board, UserEntity user) {
        return PostEntity.builder()
                .title(title)
                .content(content)
                .board(board)
                .author(user)
                .build();
    }
}
