package com.ssafy.goumunity.domain.feed.infra.commentlike;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CommentLikeJpaRepository extends JpaRepository<CommentLikeEntity, Long> {

    @Query(
            "select cl from CommentLikeEntity cl where cl.commentEntity.commentId=:commentId and cl.userEntity.id=:userId")
    boolean existsByCommentIdAndUserId(
            @Param("commentId") Long commentId, @Param("userId") Long userId);
}
