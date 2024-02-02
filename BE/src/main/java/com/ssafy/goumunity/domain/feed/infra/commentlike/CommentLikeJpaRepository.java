package com.ssafy.goumunity.domain.feed.infra.commentlike;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentLikeJpaRepository extends JpaRepository<CommentLikeEntity, Long> {

    Optional<CommentLikeEntity> findByUserEntity_IdAndCommentEntity_Id(Long userId, Long commentId);

    boolean existsByUserEntity_IdAndCommentEntity_Id(Long userId, Long commentId);
}
