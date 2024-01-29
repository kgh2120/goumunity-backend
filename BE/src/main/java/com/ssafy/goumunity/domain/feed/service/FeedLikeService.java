package com.ssafy.goumunity.domain.feed.service;

public interface FeedLikeService {

    void createFeedLike(Long userId, Long feedId);

    void deleteFeedLike(Long userId, Long feedId);
}
