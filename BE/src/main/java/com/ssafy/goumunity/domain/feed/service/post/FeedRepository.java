package com.ssafy.goumunity.domain.feed.service.post;

import com.ssafy.goumunity.domain.feed.controller.response.FeedResponse;
import com.ssafy.goumunity.domain.feed.domain.Feed;
import com.ssafy.goumunity.domain.feed.domain.FeedRecommendResource;
import java.util.List;
import java.util.Optional;

public interface FeedRepository {

    Feed save(Feed feed);

    List<FeedRecommendResource> findFeed(Long userId, Long regionId);

    FeedResponse findOneFeed(Long feedId);

    Optional<Feed> findOneById(Long feedId);

    Feed modify(Feed feed);

    void delete(Feed feed);

    boolean existsByFeedId(Long feedId);
}
