package com.pjb.topicboard.domain.comment.dto.response;

import com.pjb.topicboard.global.CustomDateUtil;
import com.pjb.topicboard.model.comment.CommentEntity;
import com.pjb.topicboard.model.user.UserEntity;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class CommentListResponseDTO {

    private List<CommentDTO> comments;

    public CommentListResponseDTO(List<CommentEntity> comments) {
        this.comments = comments.stream().map(CommentDTO::new).collect(Collectors.toList());
    }

    @Getter
    private class CommentDTO {
        private Long id;
        private String content;
        private String createdDate;
        private AuthorDTO author;

        private CommentDTO(CommentEntity comment) {
            this.id = comment.getId();
            this.content = comment.getContent();
            this.createdDate = CustomDateUtil.toStringFormat(comment.getCreatedDate());
            this.author = new AuthorDTO(comment.getAuthor());
        }

        @Getter
        private class AuthorDTO {
            private Long id;
            private String username;
            private String nickname;

            public AuthorDTO(UserEntity author) {
                this.id = author.getId();
                this.username = author.getUsername();
                this.nickname = author.getNickname();
            }
        }
    }
}
