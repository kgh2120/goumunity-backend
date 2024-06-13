package com.ssafy.goumunity.domain.feed.infra.feedlike;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface FeedLikeJpaRepository extends JpaRepository<FeedLikeEntity, Long> {

    Optional<FeedLikeEntity> findByUserEntity_IdAndAndFeedEntity_Id(Long userId, Long feedId);

    boolean existsByUserEntity_IdAndFeedEntity_Id(Long userId, Long feedId);

    @Modifying
    @Query("delete from FeedLikeEntity f where f.feedEntity.id in :feedIds")
    void deleteAllByFeedIds(List<Long> feedIds);
}
