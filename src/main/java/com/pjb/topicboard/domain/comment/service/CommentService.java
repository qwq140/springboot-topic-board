package com.pjb.topicboard.domain.comment.service;

import com.pjb.topicboard.domain.comment.dto.request.CommentSaveRequestDTO;
import com.pjb.topicboard.domain.comment.dto.request.CommentUpdateRequestDTO;
import com.pjb.topicboard.domain.comment.dto.response.CommentListResponseDTO;
import com.pjb.topicboard.domain.comment.dto.response.CommentSaveResponseDTO;
import com.pjb.topicboard.domain.comment.dto.response.CommentUpdateResponseDTO;
import com.pjb.topicboard.global.common.ErrorEnum;
import com.pjb.topicboard.global.config.security.CustomUserDetails;
import com.pjb.topicboard.global.exception.CustomCommonException;
import com.pjb.topicboard.model.comment.CommentEntity;
import com.pjb.topicboard.model.comment.CommentRepository;
import com.pjb.topicboard.model.post.PostEntity;
import com.pjb.topicboard.model.post.PostRepository;
import com.pjb.topicboard.model.user.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;


    @Transactional
    public CommentSaveResponseDTO save(CommentSaveRequestDTO requestDTO, Long postId, CustomUserDetails customUserDetails) {
        PostEntity post = postRepository.findById(postId).orElseThrow(
                () -> new CustomCommonException(ErrorEnum.POST_NOT_FOUND)
        );

        CommentEntity comment = requestDTO.toEntity(post, customUserDetails.getUser());
        CommentEntity savedComment = commentRepository.save(comment);

        return new CommentSaveResponseDTO(savedComment);
    }

    @Transactional
    public CommentUpdateResponseDTO update(CommentUpdateRequestDTO requestDTO, Long commentId, CustomUserDetails customUserDetails) {
        CommentEntity comment = commentRepository.findById(commentId).orElseThrow(
                () -> new CustomCommonException(ErrorEnum.COMMENT_NOT_FOUND)
        );

        if(!comment.getAuthor().getId().equals(customUserDetails.getUser().getId())) {
           throw new CustomCommonException(ErrorEnum.FORBIDDEN_USER);
        }

        comment.setContent(requestDTO.content());

        CommentEntity updatedComment = commentRepository.saveAndFlush(comment);

        return new CommentUpdateResponseDTO(updatedComment);
    }

    @Transactional
    public void delete(Long commentId, CustomUserDetails customUserDetails) {
        CommentEntity comment = commentRepository.findById(commentId).orElseThrow(
                () -> new CustomCommonException(ErrorEnum.COMMENT_NOT_FOUND)
        );

        if(!comment.getAuthor().getId().equals(customUserDetails.getUser().getId())){
            throw new CustomCommonException(ErrorEnum.FORBIDDEN_USER);
        }

        commentRepository.delete(comment);
    }


    public CommentListResponseDTO findAllByPostId(Long postId) {
        postRepository.findById(postId).orElseThrow(
                () -> new CustomCommonException(ErrorEnum.POST_NOT_FOUND)
        );

        List<CommentEntity> list = commentRepository.findAllByPostId(postId);

        return new CommentListResponseDTO(list);
    }
}
