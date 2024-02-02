package com.ssafy.goumunity.domain.feed.service.post;

import com.ssafy.goumunity.domain.feed.controller.response.FeedResponse;
import com.ssafy.goumunity.domain.feed.domain.Feed;
import com.ssafy.goumunity.domain.feed.domain.FeedRecommendResource;
import java.util.List;
import java.util.Optional;

public interface FeedRepository {

    Feed create(Feed feed);

    List<FeedRecommendResource> findFeed(Long userId, Long regionId);

    FeedResponse findOneFeed(Long feedId);

    Optional<Feed> findOneById(Long feedId);

    void modify(Feed feed);

    void delete(Long feedId);

    boolean existsByFeedId(Long feedId);
}
