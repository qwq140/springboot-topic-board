package com.pjb.topicboard.domain.board.controller;

import com.pjb.topicboard.domain.board.dto.request.BoardSaveRequestDTO;
import com.pjb.topicboard.domain.board.dto.request.BoardUpdateRequestDTO;
import com.pjb.topicboard.domain.board.service.BoardService;
import com.pjb.topicboard.global.common.ResponseDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/api/boards")
@RestController
public class BoardApiController {

    private final BoardService boardService;

    @PostMapping
    public ResponseEntity<?> saveBoard(@Valid @RequestBody BoardSaveRequestDTO requestDTO, BindingResult bindingResult) {
        return ResponseEntity.ok(new ResponseDTO<>(HttpStatus.OK.value(), "게시판 등록 완료", boardService.saveBoard(requestDTO)));
    }

    @GetMapping("/{boardId}")
    public ResponseEntity<?> findBoard(@PathVariable Long boardId) {
        return ResponseEntity.ok(new ResponseDTO<>(HttpStatus.OK.value(), "게시판 단건 조회", boardService.findBoard(boardId)));
    }

    @GetMapping
    public ResponseEntity<?> findAllBoards() {
        return ResponseEntity.ok(new ResponseDTO<>(HttpStatus.OK.value(), "게시판 목록 조회", boardService.findAllBoard()));
    }

    @PutMapping("/{boardId}")
    public ResponseEntity<?> updateBoard(
            @PathVariable Long boardId,
            @Valid @RequestBody BoardUpdateRequestDTO requestDTO,
            BindingResult bindingResult
    ) {
       return ResponseEntity.ok(new ResponseDTO<>(HttpStatus.OK.value(), "게시판 수정 완료", boardService.updateBoard(boardId, requestDTO)));
    }

    @DeleteMapping("/{boardId}")
    public ResponseEntity<?> deleteBoard(@PathVariable Long boardId) {
        boardService.deleteBoard(boardId);
        return ResponseEntity.ok(new ResponseDTO<>(HttpStatus.OK.value(), "게시판 삭제 완료"));
    }
}
