package com.pjb.topicboard.domain.board.controller;

import com.pjb.topicboard.domain.board.dto.request.BoardSaveRequestDTO;
import com.pjb.topicboard.domain.board.dto.request.BoardUpdateRequestDTO;
import com.pjb.topicboard.domain.board.dto.response.BoardDetailResponseDTO;
import com.pjb.topicboard.domain.board.dto.response.BoardListResponseDTO;
import com.pjb.topicboard.domain.board.dto.response.BoardSaveResponseDTO;
import com.pjb.topicboard.domain.board.dto.response.BoardUpdateResponseDTO;
import com.pjb.topicboard.domain.board.service.BoardService;
import com.pjb.topicboard.global.common.ErrorResponseDTO;
import com.pjb.topicboard.global.common.ResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Tag(name = "게시판 API", description = "게시판 API에 대한 설명입니다.")
@RequiredArgsConstructor
@RequestMapping("/api/boards")
@RestController
public class BoardApiController {

    private final BoardService boardService;

    @Operation(summary = "게시판 생성 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "게시판 생성 성공", useReturnTypeSchema = true),
            @ApiResponse(responseCode = "400", description = "게시판 생성 실패 : 유효성 검사 실패, 이미 존재하는 게시판 이름", content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class))),
            @ApiResponse(responseCode = "401", description = "인증되지 않은 사용자", content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class))),
            @ApiResponse(responseCode = "403", description = "권한이 없는 사용자", content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class))),
    })
    @PostMapping
    public ResponseEntity<ResponseDTO<BoardSaveResponseDTO>> saveBoard(@Valid @RequestBody BoardSaveRequestDTO requestDTO, BindingResult bindingResult) {
        return ResponseEntity.ok(new ResponseDTO<>(HttpStatus.OK.value(), "게시판 등록 완료", boardService.saveBoard(requestDTO)));
    }

    @Operation(summary = "게시판 상세 조회 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "게시판 상세 조회 성공", useReturnTypeSchema = true),
            @ApiResponse(responseCode = "404", description = "게시판 상세 조회 실패 : 존재하지 않은 게시판", content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class))),
    })
    @GetMapping("/{boardId}")
    public ResponseEntity<ResponseDTO<BoardDetailResponseDTO>> findBoard(@PathVariable Long boardId) {
        return ResponseEntity.ok(new ResponseDTO<>(HttpStatus.OK.value(), "게시판 단건 조회", boardService.findBoard(boardId)));
    }

    @Operation(summary = "게시판 목록 조회 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "게시판 목록 조회 성공", useReturnTypeSchema = true)
    })
    @GetMapping
    public ResponseEntity<ResponseDTO<BoardListResponseDTO>> findAllBoards() {
        return ResponseEntity.ok(new ResponseDTO<>(HttpStatus.OK.value(), "게시판 목록 조회", boardService.findAllBoard()));
    }

    @Operation(summary = "게시판 삭제 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "게시판 수정 성공", useReturnTypeSchema = true),
            @ApiResponse(responseCode = "400", description = "게시판 수정 실패 : 유효성 검사 실패, 이미 존재하는 게시판 이름", content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class))),
            @ApiResponse(responseCode = "401", description = "인증되지 않은 사용자", content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class))),
            @ApiResponse(responseCode = "403", description = "권한이 없는 사용자", content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "게시판 수정 실패 : 게시판이 존재하지 않음", content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    @PutMapping("/{boardId}")
    public ResponseEntity<ResponseDTO<BoardUpdateResponseDTO>> updateBoard(
            @PathVariable Long boardId,
            @Valid @RequestBody BoardUpdateRequestDTO requestDTO,
            BindingResult bindingResult
    ) {
       return ResponseEntity.ok(new ResponseDTO<>(HttpStatus.OK.value(), "게시판 수정 완료", boardService.updateBoard(boardId, requestDTO)));
    }

    @Operation(summary = "게시판 수정 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "게시판 삭제 성공", useReturnTypeSchema = true),
            @ApiResponse(responseCode = "401", description = "인증되지 않은 사용자", content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class))),
            @ApiResponse(responseCode = "403", description = "권한이 없는 사용자", content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "게시판 삭제 실패 : 게시판이 존재하지 않음", content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    @DeleteMapping("/{boardId}")
    public ResponseEntity<ResponseDTO<?>> deleteBoard(@PathVariable Long boardId) {
        boardService.deleteBoard(boardId);
        return ResponseEntity.ok(new ResponseDTO<>(HttpStatus.OK.value(), "게시판 삭제 완료"));
    }
}
