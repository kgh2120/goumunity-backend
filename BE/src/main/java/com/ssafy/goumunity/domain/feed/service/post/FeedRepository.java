package com.ssafy.goumunity.domain.feed.service.post;

import com.ssafy.goumunity.domain.feed.controller.response.FeedResponse;
import com.ssafy.goumunity.domain.feed.domain.Feed;
import com.ssafy.goumunity.domain.feed.domain.FeedRecommendResource;
import com.ssafy.goumunity.domain.feed.domain.FeedSearchResource;
import com.ssafy.goumunity.domain.feed.domain.SavingResource;
import com.ssafy.goumunity.domain.feed.infra.feed.FeedScrapRankingInterface;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

public interface FeedRepository {

    Feed create(Feed feed);

    List<FeedRecommendResource> findFeed(Long userId, Long regionId);

    FeedResponse findOneFeed(Long userId, Long feedId);

    Optional<Feed> findOneById(Long feedId);

    List<FeedSearchResource> findAllFeedByUserId(Long userId);

    List<SavingResource> findAllSavingByUserId(Long userId);

    List<FeedSearchResource> findAllScrappedFeedByUserId(Long userId);

    List<FeedScrapRankingInterface> findFeedScrapRanking(Instant startTime, Instant endTime);

    void modify(Feed feed);

    void delete(Long feedId);

    boolean existsByFeedId(Long feedId);

    Long countByUserId(Long userId);

    void deleteAllFeedByUserId(Long userId);

    List<Long> findAllFeedIdsByUserId(Long userId);
}
