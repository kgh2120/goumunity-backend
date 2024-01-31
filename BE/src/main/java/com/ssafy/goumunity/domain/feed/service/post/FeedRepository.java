package com.ssafy.goumunity.domain.feed.service.post;

import com.ssafy.goumunity.domain.feed.controller.response.FeedResponse;
import com.ssafy.goumunity.domain.feed.domain.Feed;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

import com.ssafy.goumunity.domain.feed.domain.FeedRecommendResource;
import com.ssafy.goumunity.domain.region.infra.QRegionEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface FeedRepository {

    Feed save(Feed feed);

    List<FeedRecommendResource> findFeed(Long userId, Instant time, Long regionId);

    FeedResponse findOneFeed(Long feedId);

    Optional<Feed> findOneById(Long feedId);

    void delete(Feed feed);

    boolean existsByFeedId(Long feedId);
}
