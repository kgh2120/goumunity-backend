package com.ssafy.goumunity.domain.feed.infra.feed;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeedJpaRepository extends JpaRepository<FeedEntity, Long> {

    Optional<FeedEntity> findByFeedId(Long feedId);
}
