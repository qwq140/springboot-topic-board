package com.pjb.topicboard.domain.post.dto.response;

import com.pjb.topicboard.global.CustomDateUtil;
import com.pjb.topicboard.model.board.BoardEntity;
import com.pjb.topicboard.model.post.PostEntity;
import com.pjb.topicboard.model.user.UserEntity;
import lombok.Getter;

@Getter
public class PostSaveResponseDTO {
    private Long id;
    private String title;
    private String content;
    private AuthorDTO author;
    private BoardDTO board;
    private String createdAt;

    public PostSaveResponseDTO(PostEntity post) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.author = new AuthorDTO(post.getAuthor());
        this.board = new BoardDTO(post.getBoard());
        this.createdAt = CustomDateUtil.toStringFormat(post.getCreatedDate());
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

    @Getter
    private class BoardDTO {
        private Long id;
        private String name;
        private String description;

        public BoardDTO(BoardEntity board) {
            this.id = board.getId();
            this.name = board.getName();
            this.description = board.getDescription();
        }
    }
}
