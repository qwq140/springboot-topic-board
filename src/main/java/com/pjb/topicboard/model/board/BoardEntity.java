package com.pjb.topicboard.model.board;

import com.pjb.topicboard.global.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@Table(name = "BOARD_TB")
@Entity
public class BoardEntity extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 60)
    private String name;
    private String description;

    @Builder
    public BoardEntity(Long id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }
}
