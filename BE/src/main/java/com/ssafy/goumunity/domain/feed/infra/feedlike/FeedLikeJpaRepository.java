package com.ssafy.goumunity.domain.feed.infra.feedlike;

import org.springframework.data.jpa.repository.JpaRepository;

public interface FeedLikeJpaRepository extends JpaRepository<FeedLikeEntity, Long> {
    boolean existsByUserEntity_IdAndFeedEntity_FeedId(Long userId, Long feedId);
}
