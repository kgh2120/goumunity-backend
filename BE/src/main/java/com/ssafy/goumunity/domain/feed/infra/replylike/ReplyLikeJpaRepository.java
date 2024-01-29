package com.ssafy.goumunity.domain.feed.infra.replylike;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ReplyLikeJpaRepository extends JpaRepository<ReplyLikeEntity, Long> {
    boolean existsByUserEntity_IdAndReplyEntity_ReplyId(Long userId, Long replyId);
}
