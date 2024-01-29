package com.ssafy.goumunity.domain.feed.service.post;

import com.ssafy.goumunity.domain.feed.domain.FeedLike;

public interface FeedLikeRepository {

    void createFeedLike(FeedLike feedLike);

    void deleteFeedLike(FeedLike feedLike);

    boolean existsByFeedLike(FeedLike feedLike);
}
