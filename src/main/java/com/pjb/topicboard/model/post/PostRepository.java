package com.pjb.topicboard.model.post;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PostRepository extends JpaRepository<PostEntity, Long> {

    @Query("SELECT p FROM PostEntity p JOIN FETCH p.author WHERE p.board.id = :boardId")
    Page<PostEntity> findAllByBoardId(Long boardId, Pageable pageable);

    @Query("SELECT p FROM PostEntity p JOIN FETCH p.author WHERE p.board.id = :boardId AND (p.title LIKE %:keyword% OR p.content LIKE %:keyword%)")
    Page<PostEntity> searchByKeyword(@Param("boardId") Long boardId,@Param("keyword") String keyword, Pageable pageable);
    boolean existsById(Long id);
}
