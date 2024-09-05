package com.pjb.topicboard.model.post;

import com.pjb.topicboard.global.common.BaseTimeEntity;
import com.pjb.topicboard.model.board.BoardEntity;
import com.pjb.topicboard.model.user.UserEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
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

    @ManyToOne
    @JoinColumn(name = "author_id", nullable = false)
    private UserEntity author;

    @ManyToOne
    @JoinColumn(name = "board_id", nullable = false)
    private BoardEntity board;
}
