package com.pjb.topicboard.domain.board.dto.response;

import com.pjb.topicboard.global.CustomDateUtil;
import com.pjb.topicboard.model.board.BoardEntity;
import lombok.Getter;

@Getter
public class BoardSaveResponseDTO {
    private Long id;
    private String name;
    private String description;
    private String createdDate;

    public BoardSaveResponseDTO(BoardEntity board) {
        this.id = board.getId();
        this.name = board.getName();
        this.description = board.getDescription();
        this.createdDate = CustomDateUtil.toStringFormat(board.getCreatedDate());
    }
}
