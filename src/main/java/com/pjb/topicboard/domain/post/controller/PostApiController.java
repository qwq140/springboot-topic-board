package com.pjb.topicboard.domain.post.controller;

import com.pjb.topicboard.domain.board.dto.response.BoardUpdateResponseDTO;
import com.pjb.topicboard.domain.post.dto.request.PostSaveRequestDTO;
import com.pjb.topicboard.domain.post.dto.request.PostUpdateRequestDTO;
import com.pjb.topicboard.domain.post.dto.response.PostDetailResponseDTO;
import com.pjb.topicboard.domain.post.dto.response.PostListResponseDTO;
import com.pjb.topicboard.domain.post.dto.response.PostSaveResponseDTO;
import com.pjb.topicboard.domain.post.dto.response.PostUpdateResponseDTO;
import com.pjb.topicboard.domain.post.service.PostService;
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
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Tag(name = "게시물 API", description = "게시물 API에 대한 설명입니다.")
@RequiredArgsConstructor
@RequestMapping("/api")
@RestController
public class PostApiController {

    private final PostService postService;

    @Operation(summary = "게시물 생성 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "게시물 생성 성공", useReturnTypeSchema = true),
            @ApiResponse(responseCode = "400", description = "게시물 생성 실패 : 유효성 검사 실패", content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class))),
            @ApiResponse(responseCode = "401", description = "인증되지 않은 사용자", content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "게시물 생성 실패 : 게시판이 존재하지 않음", content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    @PostMapping("/boards/{boardId}/posts")
    public ResponseEntity<ResponseDTO<PostSaveResponseDTO>> savePost(
            @PathVariable Long boardId,
            @Valid @RequestBody PostSaveRequestDTO requestDTO,
            BindingResult bindingResult,
            @AuthenticationPrincipal CustomUserDetails customUserDetails
    ) {
        PostSaveResponseDTO responseDTO = postService.savePost(requestDTO, customUserDetails, boardId);
        return ResponseEntity.ok(new ResponseDTO<>(HttpStatus.OK.value(), "게시글 작성 완료", responseDTO));
    }

    @Operation(summary = "게시물 수정 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "게시물 수정 성공", useReturnTypeSchema = true),
            @ApiResponse(responseCode = "400", description = "게시물 수정 실패 : 유효성 검사 실패", content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class))),
            @ApiResponse(responseCode = "401", description = "인증되지 않은 사용자", content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class))),
            @ApiResponse(responseCode = "403", description = "권한 없는 사용자", content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "게시물 수정 실패 : 게시물이 존재하지 않음", content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    @PutMapping("/posts/{postId}")
    public ResponseEntity<ResponseDTO<PostUpdateResponseDTO>> updatePost(
            @PathVariable Long postId,
            @Valid @RequestBody PostUpdateRequestDTO requestDTO,
            BindingResult bindingResult,
            @AuthenticationPrincipal CustomUserDetails customUserDetails
    ) {
        PostUpdateResponseDTO responseDTO = postService.updatePost(requestDTO, customUserDetails, postId);
        return ResponseEntity.ok(new ResponseDTO<>(HttpStatus.OK.value(), "게시글 수정 완료", responseDTO));
    }

    @Operation(summary = "게시물 삭제 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "게시물 삭제 성공", useReturnTypeSchema = true),
            @ApiResponse(responseCode = "401", description = "인증되지 않은 사용자", content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class))),
            @ApiResponse(responseCode = "403", description = "권한 없는 사용자", content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "게시물 삭제 실패 : 게시물이 존재하지 않음", content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    @DeleteMapping("/posts/{postId}")
    public ResponseEntity<ResponseDTO<?>> deletePost(
            @PathVariable Long postId,
            @AuthenticationPrincipal CustomUserDetails customUserDetails
    ) {
        postService.deletePost(customUserDetails, postId);
        return ResponseEntity.ok(new ResponseDTO<>(HttpStatus.OK.value(), "게시글 삭제 완료"));
    }

    @Operation(summary = "게시물 상세 조회 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "게시물 상세 조회 성공", useReturnTypeSchema = true),
            @ApiResponse(responseCode = "404", description = "게시물 상세 조회 실패 : 게시물이 존재하지 않음", content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    @GetMapping("/posts/{postId}")
    public ResponseEntity<ResponseDTO<PostDetailResponseDTO>> findPostById(@PathVariable Long postId) {
        PostDetailResponseDTO responseDTO = postService.findByPostId(postId);
        return ResponseEntity.ok(new ResponseDTO<>(HttpStatus.OK.value(), "게시글 조회 완료", responseDTO));
    }

    @Operation(summary = "게시물 목록 조회 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "게시물 목록 조회 성공", useReturnTypeSchema = true),
            @ApiResponse(responseCode = "404", description = "게시물 목록 조회 실패 : 게시판이 존재하지 않음", content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    @GetMapping("/boards/{boardId}/posts")
    public ResponseEntity<ResponseDTO<PostListResponseDTO>> pagingPost(
            @PathVariable Long boardId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        PostListResponseDTO response = postService.pagingPostsByBoardId(boardId, page, size);
        return ResponseEntity.ok(new ResponseDTO<>(HttpStatus.OK.value(), "게시글 목록 조회", response));
    }
}
