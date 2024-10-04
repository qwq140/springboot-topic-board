package com.pjb.topicboard.model.post;

import com.pjb.topicboard.global.common.BaseTimeEntity;
import com.pjb.topicboard.model.board.BoardEntity;
import com.pjb.topicboard.model.user.UserEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@Table(name = "POST_TB")
@Entity
public class PostEntity extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id", nullable = false)
    private UserEntity author;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id", nullable = false)
    private BoardEntity board;

    @Builder
    public PostEntity(Long id, String title, String content, UserEntity author, BoardEntity board) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.author = author;
        this.board = board;
    }
}
