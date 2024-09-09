package com.pjb.topicboard.domain.board.dto.response;

import com.pjb.topicboard.model.board.BoardEntity;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class BoardListResponseDTO {

    private List<BoardDTO> boards;

    public BoardListResponseDTO(List<BoardEntity> boards) {
        this.boards = boards.stream().map(BoardDTO::new).collect(Collectors.toList());
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
