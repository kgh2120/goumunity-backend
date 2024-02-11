package com.ssafy.goumunity.domain.feed.infra.feed;

import java.time.Instant;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface FeedJpaRepository extends JpaRepository<FeedEntity, Long> {
    List<FeedEntity> findAllByUserEntity_IdAndPriceIsNotNullAndAfterPriceIsNotNull(Long userId);

    Long countByUserEntity_Id(Long userId);

    @Query("select f.id from FeedEntity f where f.userEntity.id =:userId")
    List<Long> findAllFeedIdsByUserId(Long userId);

    @Query(
            value =
                    "SELECT f.feed_id as feedId, f.content, f.category, count(s.scrap_id) as scrapCount "
                            + "FROM feed AS f LEFT JOIN scrap AS s ON f.feed_id = s.feed_id "
                            + "WHERE s.created_at BETWEEN ?1 AND ?2 "
                            + "GROUP BY f.feed_id "
                            + "ORDER BY scrapCount DESC "
                            + "LIMIT 10;",
            nativeQuery = true)
    List<FeedScrapRankingInterface> findFeedScrapRanking(Instant startTime, Instant endTime);
}
