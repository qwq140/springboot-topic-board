package com.pjb.topicboard.domain.comment.controller;

import com.pjb.topicboard.domain.comment.dto.request.CommentSaveRequestDTO;
import com.pjb.topicboard.domain.comment.dto.request.CommentUpdateRequestDTO;
import com.pjb.topicboard.domain.comment.dto.response.CommentListResponseDTO;
import com.pjb.topicboard.domain.comment.dto.response.CommentSaveResponseDTO;
import com.pjb.topicboard.domain.comment.dto.response.CommentUpdateResponseDTO;
import com.pjb.topicboard.domain.comment.service.CommentService;
import com.pjb.topicboard.global.common.ErrorResponseDTO;
import com.pjb.topicboard.global.common.ResponseDTO;
import com.pjb.topicboard.global.config.security.CustomUserDetails;
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
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


@Tag(name = "댓글 API", description = "댓글 API에 대한 설명입니다.")
@RequiredArgsConstructor
@RequestMapping("/api")
@RestController
public class CommentApiController {

    private final CommentService commentService;

    @Operation(summary = "댓글 작성 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "댓글 작성 성공", useReturnTypeSchema = true),
            @ApiResponse(responseCode = "400", description = "댓글 작성 실패 : 유효성 검사 실패", content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class))),
            @ApiResponse(responseCode = "401", description = "인증되지 않은 사용자", content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "댓글 작성 실패 : 게시물이 존재하지 않음", content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    @PostMapping("/posts/{postId}/comments")
    public ResponseEntity<ResponseDTO<CommentSaveResponseDTO>> saveComment(
            @Valid @RequestBody CommentSaveRequestDTO requestDTO,
            BindingResult bindingResult,
            @PathVariable Long postId,
            @AuthenticationPrincipal CustomUserDetails customUserDetails
    ){
        CommentSaveResponseDTO responseDTO = commentService.save(requestDTO, postId, customUserDetails);
        return ResponseEntity.ok(new ResponseDTO<>(HttpStatus.OK.value(), "댓글 작성 완료", responseDTO));
    }

    @Operation(summary = "댓글 수정 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "댓글 수정 성공", useReturnTypeSchema = true),
            @ApiResponse(responseCode = "400", description = "댓글 작성 실패 : 유효성 검사 실패", content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class))),
            @ApiResponse(responseCode = "401", description = "인증되지 않은 사용자", content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class))),
            @ApiResponse(responseCode = "403", description = "권한 없는 사용자", content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "댓글 수정 실패 : 댓글이 존재하지 않음", content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    @PutMapping("/comments/{commentId}")
    public ResponseEntity<ResponseDTO<CommentUpdateResponseDTO>> updateComment(
            @Valid @RequestBody CommentUpdateRequestDTO requestDTO,
            BindingResult bindingResult,
            @PathVariable Long commentId,
            @AuthenticationPrincipal CustomUserDetails customUserDetails
    ){
        CommentUpdateResponseDTO responseDTO = commentService.update(requestDTO, commentId, customUserDetails);
        return ResponseEntity.ok(new ResponseDTO<>(HttpStatus.OK.value(), "댓글 수정 완료", responseDTO));
    }

    @Operation(summary = "댓글 삭제 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "댓글 삭제 성공", useReturnTypeSchema = true),
            @ApiResponse(responseCode = "401", description = "인증되지 않은 사용자", content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class))),
            @ApiResponse(responseCode = "403", description = "권한 없는 사용자", content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "댓글 삭제 실패 : 댓글이 존재하지 않음", content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    @DeleteMapping("/comments/{commentId}")
    public ResponseEntity<ResponseDTO<?>> deleteComment(
            @PathVariable Long commentId,
            @AuthenticationPrincipal CustomUserDetails customUserDetails
    ){
        commentService.delete(commentId, customUserDetails);
        return ResponseEntity.ok(new ResponseDTO<>(HttpStatus.OK.value(), "댓글 삭제 완료"));
    }

    @Operation(summary = "댓글 목록 조회 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "댓글 목록 조회 성공", useReturnTypeSchema = true),
            @ApiResponse(responseCode = "404", description = "댓글 목록 조회 실패 : 게시물이 존재하지 않음", content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    @GetMapping("/posts/{postId}/comments")
    public ResponseEntity<ResponseDTO<CommentListResponseDTO>> getCommentsByBoard(
            @PathVariable Long postId
    ) {
        CommentListResponseDTO responseDTO = commentService.findAllByPostId(postId);
        return ResponseEntity.ok(new ResponseDTO<>(HttpStatus.OK.value(), "댓글 목록 조회 완료", responseDTO));
    }
}
