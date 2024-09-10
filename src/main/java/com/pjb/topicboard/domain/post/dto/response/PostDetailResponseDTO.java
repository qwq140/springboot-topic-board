package com.pjb.topicboard.domain.post.dto.response;

import com.pjb.topicboard.global.CustomDateUtil;
import com.pjb.topicboard.model.board.BoardEntity;
import com.pjb.topicboard.model.post.PostEntity;
import com.pjb.topicboard.model.user.UserEntity;
import lombok.Getter;

@Getter
public class PostDetailResponseDTO {
    private Long id;
    private String title;
    private String content;
    private AuthorDTO author;
    private BoardDTO board;
    private String createdDate;
    private String updatedDate;

    public PostDetailResponseDTO(PostEntity post) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.author = new AuthorDTO(post.getAuthor());
        this.board = new BoardDTO(post.getBoard());
        this.createdDate = CustomDateUtil.toStringFormat(post.getCreatedDate());
        this.updatedDate = CustomDateUtil.toStringFormat(post.getUpdatedDate());
    }

    @Getter
    private class AuthorDTO {
        private Long id;
        private String username;
        private String nickname;

        private AuthorDTO(UserEntity user) {
            this.id = user.getId();
            this.username = user.getUsername();
            this.nickname = user.getNickname();
        }
    }

    @Getter
    private class BoardDTO {
        private Long id;
        private String name;
        private String description;

        private BoardDTO(BoardEntity board) {
            this.id = board.getId();
            this.name = board.getName();
            this.description = board.getDescription();
        }
    }

}
