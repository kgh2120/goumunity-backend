package com.ssafy.goumunity.domain.feed.infra.replylike;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReplyLikeJpaRepository extends JpaRepository<ReplyLikeEntity, Long> {

    Optional<ReplyLikeEntity> findByUserEntity_IdAndReplyEntity_ReplyId(Long userId, Long replyId);

    boolean existsByUserEntity_IdAndReplyEntity_ReplyId(Long userId, Long replyId);
}
