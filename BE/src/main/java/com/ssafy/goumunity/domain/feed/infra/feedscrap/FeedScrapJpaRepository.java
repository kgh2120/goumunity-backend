package com.ssafy.goumunity.domain.feed.infra.feedscrap;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeedScrapJpaRepository extends JpaRepository<FeedScrapEntity, Long> {

    Optional<FeedScrapEntity> findByUserEntityIdAndFeedEntityId(Long userId, Long feedId);

    boolean existsByUserEntityIdAndFeedEntityId(Long userId, Long feedId);
}
