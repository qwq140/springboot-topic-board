package com.pjb.topicboard.domain.comment.dto.request;

import com.pjb.topicboard.model.board.BoardEntity;
import com.pjb.topicboard.model.comment.CommentEntity;
import com.pjb.topicboard.model.post.PostEntity;
import com.pjb.topicboard.model.user.UserEntity;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

public record CommentSaveRequestDTO(
        @NotBlank(message = "댓글 내용을 입력해주세요.")
        String content
) {

    public CommentEntity toEntity(PostEntity post, UserEntity author) {
        return CommentEntity.builder()
                .content(content)
                .post(post)
                .author(author)
                .build();
    }
}
