package com.ssafy.goumunity.domain.feed.infra.feed;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface FeedJpaRepository extends JpaRepository<FeedEntity, Long> {
    @Query("select f from FeedEntity f where f.feedId=:feedId")
    Optional<FeedEntity> findOneByFeedId(@Param("feedId") Long feedId);

    @Query("select f from FeedEntity f where f.userEntity.id=:userId")
    List<FeedEntity> findAllByUserId(@Param("userId") Long userId);
}
