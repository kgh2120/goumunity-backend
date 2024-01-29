package com.ssafy.goumunity.domain.feed.infra.feedlike;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface FeedLikeJpaRepository extends JpaRepository<FeedLikeEntity, Long> {

    @Query(
            "select fl from FeedLikeEntity fl where fl.feedEntity.feedId=:feedId and fl.userEntity.id=:userId")
    Optional<FeedLikeEntity> findOneByFeedIdAndUserId(
            @Param("feedId") Long feedId, @Param("userId") Long userId);

    @Query("select count(fl) from FeedLikeEntity fl where fl.feedEntity.feedId=:feedId")
    Integer countFeedLikeByFeedId(@Param("feedId") Long feedId);
}
