package com.pjb.topicboard.model.post;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<PostEntity, Long> {
    Page<PostEntity> findAllByBoardId(Long boardId, Pageable pageable);
    boolean existsById(Long id);
}
