package com.pjb.topicboard.domain.comment.dto.response;

import com.pjb.topicboard.global.CustomDateUtil;
import com.pjb.topicboard.model.comment.CommentEntity;
import com.pjb.topicboard.model.user.UserEntity;
import lombok.Getter;

@Getter
public class CommentSaveResponseDTO {
    private Long id;
    private String content;
    private String createdDate;
    private UserDTO author;

    public CommentSaveResponseDTO(CommentEntity comment) {
        this.id = comment.getId();
        this.content = comment.getContent();
        this.createdDate = CustomDateUtil.toStringFormat(comment.getCreatedDate());
        this.author = new UserDTO(comment.getAuthor());
    }

    @Getter
    private class UserDTO {
        private Long id;
        private String username;
        private String nickname;

        public UserDTO(UserEntity user) {
            this.id = user.getId();
            this.username = user.getUsername();
            this.nickname = user.getNickname();
        }
    }
}
