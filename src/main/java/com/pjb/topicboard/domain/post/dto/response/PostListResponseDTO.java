package com.pjb.topicboard.domain.post.dto.response;

import com.pjb.topicboard.global.CustomDateUtil;
import com.pjb.topicboard.model.board.BoardEntity;
import com.pjb.topicboard.model.post.PostEntity;
import com.pjb.topicboard.model.user.UserEntity;
import lombok.Getter;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class PostListResponseDTO {

    private List<PostDTO> posts;
    private int totalPages;
    private long totalElements;
    private int currentPage;
    private int pageSize;

    public PostListResponseDTO(Page<PostEntity> postEntityPage) {
        this.posts = postEntityPage.getContent().stream().map(PostDTO::new).collect(Collectors.toList());
        this.totalPages = postEntityPage.getTotalPages();
        this.totalElements = postEntityPage.getTotalElements();
        this.currentPage = postEntityPage.getNumber();
        this.pageSize = postEntityPage.getSize();
    }

    @Getter
    private class PostDTO {
        private Long id;
        private String title;
        private String createdAt;
        private BoardDTO board;
        private AuthorDTO author;

        private PostDTO(PostEntity post) {
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

            private BoardDTO(BoardEntity board) {
                this.id = board.getId();
                this.name = board.getName();
            }
        }
    }


}
