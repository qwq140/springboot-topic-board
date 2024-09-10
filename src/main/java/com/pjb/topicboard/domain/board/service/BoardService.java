package com.pjb.topicboard.domain.board.service;

import com.pjb.topicboard.domain.board.dto.request.BoardSaveRequestDTO;
import com.pjb.topicboard.domain.board.dto.request.BoardUpdateRequestDTO;
import com.pjb.topicboard.domain.board.dto.response.BoardDetailResponseDTO;
import com.pjb.topicboard.domain.board.dto.response.BoardListResponseDTO;
import com.pjb.topicboard.domain.board.dto.response.BoardSaveResponseDTO;
import com.pjb.topicboard.domain.board.dto.response.BoardUpdateResponseDTO;
import com.pjb.topicboard.global.common.ErrorEnum;
import com.pjb.topicboard.global.exception.CustomCommonException;
import com.pjb.topicboard.model.board.BoardEntity;
import com.pjb.topicboard.model.board.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class BoardService {
    private final BoardRepository boardRepository;

    @Transactional
    public BoardSaveResponseDTO saveBoard(BoardSaveRequestDTO requestDTO) {
        Optional<BoardEntity> boardOptional = boardRepository.findByName(requestDTO.name());
        if(boardOptional.isPresent()) {
            throw new CustomCommonException(ErrorEnum.BOARD_ALREADY_EXIST);
        }

        BoardEntity savedBoard = boardRepository.save(requestDTO.toEntity());

        return new BoardSaveResponseDTO(savedBoard);
    }

    public BoardDetailResponseDTO findBoard(Long boardId) {
        BoardEntity board = boardRepository.findById(boardId).orElseThrow(
                () -> new CustomCommonException(ErrorEnum.BOARD_NOT_FOUND)
        );
        return new BoardDetailResponseDTO(board);
    }

    public BoardListResponseDTO findAllBoard() {
        List<BoardEntity> boardList = boardRepository.findAll();
        return new BoardListResponseDTO(boardList);
    }

    @Transactional
    public BoardUpdateResponseDTO updateBoard(Long boardId, BoardUpdateRequestDTO requestDTO) {
        BoardEntity board = boardRepository.findById(boardId).orElseThrow(
                () -> new CustomCommonException(ErrorEnum.BOARD_NOT_FOUND)
        );
        board.setName(requestDTO.name());
        board.setDescription(requestDTO.description());

        BoardEntity updatedBoard = boardRepository.saveAndFlush(board);

        return new BoardUpdateResponseDTO(updatedBoard);
    }

    @Transactional
    public void deleteBoard(Long boardId) {
        BoardEntity board = boardRepository.findById(boardId).orElseThrow(
                () -> new CustomCommonException(ErrorEnum.BOARD_NOT_FOUND)
        );
        boardRepository.delete(board);
    }
}
