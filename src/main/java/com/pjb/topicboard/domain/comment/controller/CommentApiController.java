package com.pjb.topicboard.domain.comment.controller;

import com.pjb.topicboard.domain.comment.dto.request.CommentSaveRequestDTO;
import com.pjb.topicboard.domain.comment.dto.request.CommentUpdateRequestDTO;
import com.pjb.topicboard.domain.comment.dto.response.CommentListResponseDTO;
import com.pjb.topicboard.domain.comment.dto.response.CommentSaveResponseDTO;
import com.pjb.topicboard.domain.comment.dto.response.CommentUpdateResponseDTO;
import com.pjb.topicboard.domain.comment.service.CommentService;
import com.pjb.topicboard.global.common.ResponseDTO;
import com.pjb.topicboard.global.config.security.CustomUserDetails;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


@RequiredArgsConstructor
@RequestMapping("/api")
@RestController
public class CommentApiController {

    private final CommentService commentService;

    @PostMapping("/posts/{postId}/comments")
    public ResponseEntity<?> saveComment(
            @Valid @RequestBody CommentSaveRequestDTO requestDTO,
            BindingResult bindingResult,
            @PathVariable Long postId,
            @AuthenticationPrincipal CustomUserDetails customUserDetails
    ){
        CommentSaveResponseDTO responseDTO = commentService.save(requestDTO, postId, customUserDetails);
        return ResponseEntity.ok(new ResponseDTO<>(0, "댓글 작성 완료", responseDTO));
    }

    @PutMapping("/comments/{commentId}")
    public ResponseEntity<?> updateComment(
            @Valid @RequestBody CommentUpdateRequestDTO requestDTO,
            BindingResult bindingResult,
            @PathVariable Long commentId,
            @AuthenticationPrincipal CustomUserDetails customUserDetails
    ){
        CommentUpdateResponseDTO responseDTO = commentService.update(requestDTO, commentId, customUserDetails);
        return ResponseEntity.ok(new ResponseDTO<>(0, "댓글 수정 완료", responseDTO));
    }

    @DeleteMapping("/comments/{commentId}")
    public ResponseEntity<?> deleteComment(
            @PathVariable Long commentId,
            @AuthenticationPrincipal CustomUserDetails customUserDetails
    ){
        commentService.delete(commentId, customUserDetails);
        return ResponseEntity.ok(new ResponseDTO<>(0, "댓글 삭제 완료"));
    }

    @GetMapping("/posts/{postId}/comments")
    public ResponseEntity<?> getCommentsByBoard(
            @PathVariable Long postId
    ) {
        CommentListResponseDTO responseDTO = commentService.findAllByPostId(postId);
        return ResponseEntity.ok(new ResponseDTO<>(0, "댓글 목록 조회 완료", responseDTO));
    }
}
