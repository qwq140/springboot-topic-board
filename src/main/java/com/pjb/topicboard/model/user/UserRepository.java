package com.pjb.topicboard.model.user;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    @Query("select m from UserEntity m join fetch m.role where m.username = :username")
    Optional<UserEntity> findByUsername(String username);
    boolean existsByUsername(String username);
    boolean existsByNickname(String nickname);
}
