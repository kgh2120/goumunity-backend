package com.ssafy.goumunity.domain.feed.infra.reply;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReplyJpaRepository extends JpaRepository<ReplyEntity, Long> {
    Optional<ReplyEntity> findByReplyId(Long id);
}
