package com.ssafy.goumunity.domain.feed.infra.commentlike;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentLikeJpaRepository extends JpaRepository<CommentLikeEntity, Long> {

    boolean existsByUserEntity_IdAndCommentEntity_CommentId(Long userId, Long commentId);
}
