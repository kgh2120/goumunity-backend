package com.ssafy.goumunity.domain.feed.infra.feedlike;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeedLikeJpaRepository extends JpaRepository<FeedLikeEntity, Long> {

    Optional<FeedLikeEntity> findByUserEntity_IdAndAndFeedEntity_Id(Long userId, Long feedId);

    boolean existsByUserEntity_IdAndFeedEntity_Id(Long userId, Long feedId);
}
