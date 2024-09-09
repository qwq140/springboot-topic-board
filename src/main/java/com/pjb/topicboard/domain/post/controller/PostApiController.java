package com.pjb.topicboard.domain.post.controller;

import com.pjb.topicboard.domain.post.dto.request.PostSaveRequestDTO;
import com.pjb.topicboard.domain.post.dto.request.PostUpdateRequestDTO;
import com.pjb.topicboard.domain.post.dto.response.PostDetailResponseDTO;
import com.pjb.topicboard.domain.post.dto.response.PostListResponseDTO;
import com.pjb.topicboard.domain.post.dto.response.PostSaveResponseDTO;
import com.pjb.topicboard.domain.post.dto.response.PostUpdateResponseDTO;
import com.pjb.topicboard.domain.post.service.PostService;
import com.pjb.topicboard.global.common.ResponseDTO;
import com.pjb.topicboard.global.config.security.CustomUserDetails;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/api")
@RestController
public class PostApiController {

    private final PostService postService;

    @PostMapping("/boards/{boardId}/posts")
    public ResponseEntity<?> savePost(
            @PathVariable Long boardId,
            @Valid @RequestBody PostSaveRequestDTO requestDTO,
            BindingResult bindingResult,
            @AuthenticationPrincipal CustomUserDetails customUserDetails
    ) {
        PostSaveResponseDTO responseDTO = postService.savePost(requestDTO, customUserDetails, boardId);
        return ResponseEntity.ok(new ResponseDTO<>(1, "게시글 작성 완료", responseDTO));
    }

    @PutMapping("/posts/{postId}")
    public ResponseEntity<?> updatePost(
            @PathVariable Long postId,
            @Valid @RequestBody PostUpdateRequestDTO requestDTO,
            BindingResult bindingResult,
            @AuthenticationPrincipal CustomUserDetails customUserDetails
    ) {
        PostUpdateResponseDTO responseDTO = postService.updatePost(requestDTO, customUserDetails, postId);
        return ResponseEntity.ok(new ResponseDTO<>(1, "게시글 수정 완료", responseDTO));
    }

    @DeleteMapping("/posts/{postId}")
    public ResponseEntity<?> deletePost(
            @PathVariable Long postId,
            @AuthenticationPrincipal CustomUserDetails customUserDetails
    ) {
        postService.deletePost(customUserDetails, postId);
        return ResponseEntity.ok(new ResponseDTO<>(1, "게시글 삭제 완료"));
    }

    @GetMapping("/posts/{postId}")
    public ResponseEntity<?> findPostById(@PathVariable Long postId) {
        PostDetailResponseDTO responseDTO = postService.findByPostId(postId);
        return ResponseEntity.ok(new ResponseDTO<>(1, "게시글 조회 완료", responseDTO));
    }

    @GetMapping("/boards/{boardId}/posts")
    public ResponseEntity<?> pagingPost(
            @PathVariable Long boardId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        PostListResponseDTO response = postService.pagingPostsByBoardId(boardId, page, size);
        return ResponseEntity.ok(new ResponseDTO<>(1, "게시글 목록 조회", response));
    }
}
