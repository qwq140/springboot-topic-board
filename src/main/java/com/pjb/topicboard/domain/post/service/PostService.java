package com.pjb.topicboard.domain.post.service;

import com.pjb.topicboard.domain.post.dto.request.PostSaveRequestDTO;
import com.pjb.topicboard.domain.post.dto.request.PostUpdateRequestDTO;
import com.pjb.topicboard.domain.post.dto.response.PostDetailResponseDTO;
import com.pjb.topicboard.domain.post.dto.response.PostListResponseDTO;
import com.pjb.topicboard.domain.post.dto.response.PostSaveResponseDTO;
import com.pjb.topicboard.domain.post.dto.response.PostUpdateResponseDTO;
import com.pjb.topicboard.global.common.ErrorEnum;
import com.pjb.topicboard.global.config.security.CustomUserDetails;
import com.pjb.topicboard.global.exception.CustomCommonException;
import com.pjb.topicboard.model.board.BoardEntity;
import com.pjb.topicboard.model.board.BoardRepository;
import com.pjb.topicboard.model.post.PostEntity;
import com.pjb.topicboard.model.post.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class PostService {

    private final PostRepository postRepository;
    private final BoardRepository boardRepository;

    @Transactional
    public PostSaveResponseDTO savePost(PostSaveRequestDTO requestDTO, CustomUserDetails customUserDetails, Long boardId) {
        BoardEntity board = boardRepository.findById(boardId).orElseThrow(
                () -> new CustomCommonException(ErrorEnum.BOARD_NOT_FOUND)
        );

        PostEntity post = requestDTO.toEntity(board, customUserDetails.getUser());

        PostEntity savedPost = postRepository.save(post);

        return new PostSaveResponseDTO(savedPost);
    }

    @Transactional
    public PostUpdateResponseDTO updatePost(PostUpdateRequestDTO requestDTO, CustomUserDetails customUserDetails, Long postId) {
        PostEntity post = postRepository.findById(postId).orElseThrow(
                () -> new CustomCommonException(ErrorEnum.POST_NOT_FOUND)
        );

        if(!post.getAuthor().getId().equals(customUserDetails.getUser().getId())) {
            throw new CustomCommonException(ErrorEnum.FORBIDDEN_USER);
        }

        post.setTitle(requestDTO.title());
        post.setContent(requestDTO.content());

        PostEntity updatedPost = postRepository.saveAndFlush(post);
        return new PostUpdateResponseDTO(updatedPost);
    }

    @Transactional
    public void deletePost(CustomUserDetails customUserDetails, Long postId) {
        PostEntity post = postRepository.findById(postId).orElseThrow(
                () -> new CustomCommonException(ErrorEnum.POST_NOT_FOUND)
        );

        if(!post.getAuthor().getId().equals(customUserDetails.getUser().getId())) {
            throw new CustomCommonException(ErrorEnum.FORBIDDEN_USER);
        }

        postRepository.delete(post);
    }

    public PostDetailResponseDTO findByPostId(Long postId) {
        PostEntity post = postRepository.findById(postId).orElseThrow(
                () -> new CustomCommonException(ErrorEnum.POST_NOT_FOUND)
        );

        return new PostDetailResponseDTO(post);
    }

    public Page<PostListResponseDTO> pagingPostsByBoardId(Long boardId, int page, int size) {
        boardRepository.findById(boardId).orElseThrow(
                () -> new CustomCommonException(ErrorEnum.BOARD_NOT_FOUND)
        );

        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdDate"));

        Page<PostEntity> postEntityPage = postRepository.findAllByBoardId(boardId, pageable);

        return postEntityPage.map(PostListResponseDTO::new);
    }
}
