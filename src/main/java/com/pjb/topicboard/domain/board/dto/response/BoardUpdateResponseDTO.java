package com.pjb.topicboard.domain.board.dto.response;

import com.pjb.topicboard.global.CustomDateUtil;
import com.pjb.topicboard.model.board.BoardEntity;
import lombok.Getter;

@Getter
public class BoardUpdateResponseDTO {
    private Long id;
    private String name;
    private String description;
    private String createdDate;
    private String updatedDate;

    public BoardUpdateResponseDTO(BoardEntity board) {
        this.id = board.getId();
        this.name = board.getName();
        this.description = board.getDescription();
        this.createdDate = CustomDateUtil.toStringFormat(board.getCreatedDate());
        this.updatedDate = CustomDateUtil.toStringFormat(board.getUpdatedDate());
    }
}