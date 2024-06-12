package com.ssafy.goumunity.domain.feed.infra.feedscrap;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface FeedScrapJpaRepository extends JpaRepository<FeedScrapEntity, Long> {

    Optional<FeedScrapEntity> findByUserEntityIdAndFeedEntityId(Long userId, Long feedId);

    boolean existsByUserEntityIdAndFeedEntityId(Long userId, Long feedId);

    @Modifying
    @Query("delete from FeedScrapEntity f where f.feedEntity.id in :feedIds")
    void deleteAllByFeedIds(List<Long> feedIds);
}
