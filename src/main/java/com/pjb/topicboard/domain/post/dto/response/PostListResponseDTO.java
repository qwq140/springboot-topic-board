package com.pjb.topicboard.domain.post.dto.response;

import com.pjb.topicboard.global.CustomDateUtil;
import com.pjb.topicboard.model.board.BoardEntity;
import com.pjb.topicboard.model.post.PostEntity;
import com.pjb.topicboard.model.user.UserEntity;
import lombok.Getter;

@Getter
public class PostListResponseDTO {
    private Long id;
    private String title;
    private String createdAt;
    private BoardDTO board;
    private AuthorDTO author;

    public PostListResponseDTO(PostEntity post) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.createdAt = CustomDateUtil.toStringFormat(post.getCreatedDate());
        this.board = new BoardDTO(post.getBoard());
        this.author = new AuthorDTO(post.getAuthor());
    }

    @Getter
    private class AuthorDTO {
        private Long id;
        private String username;
        private String nickname;

        public AuthorDTO(UserEntity user) {
            this.id = user.getId();
            this.username = user.getUsername();
            this.nickname = user.getNickname();
        }
    }

    @Getter
    private class BoardDTO {
        private Long id;
        private String name;

        public BoardDTO(BoardEntity board) {
            this.id = board.getId();
            this.name = board.getName();
        }
    }
}
