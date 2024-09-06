package com.pjb.topicboard.domain.board.service;

import com.pjb.topicboard.domain.board.dto.request.BoardSaveRequestDTO;
import com.pjb.topicboard.domain.board.dto.request.BoardUpdateRequestDTO;
import com.pjb.topicboard.domain.board.dto.response.BoardDetailResponseDTO;
import com.pjb.topicboard.domain.board.dto.response.BoardListResponseDTO;
import com.pjb.topicboard.domain.board.dto.response.BoardSaveResponseDTO;
import com.pjb.topicboard.domain.board.dto.response.BoardUpdateResponseDTO;
import com.pjb.topicboard.global.exception.Exception400;
import com.pjb.topicboard.global.exception.Exception404;
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
            throw new Exception400("존재하는 게시판 이름입니다.");
        }

        BoardEntity savedBoard = boardRepository.save(requestDTO.toEntity());

        return new BoardSaveResponseDTO(savedBoard);
    }

    public BoardDetailResponseDTO findBoard(Long boardId) {
        BoardEntity board = boardRepository.findById(boardId).orElseThrow(
                () -> new Exception404("존재 하지 않은 게시판 입니다.")
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
                () -> new Exception404("존재 하지 않은 게시판 입니다.")
        );
        board.setName(requestDTO.name());
        board.setDescription(requestDTO.description());

        return new BoardUpdateResponseDTO(board);
    }

    @Transactional
    public void deleteBoard(Long boardId) {
        BoardEntity board = boardRepository.findById(boardId).orElseThrow(
                () -> new Exception404("존재 하지 않은 게시판 입니다.")
        );
        boardRepository.delete(board);
    }
}
