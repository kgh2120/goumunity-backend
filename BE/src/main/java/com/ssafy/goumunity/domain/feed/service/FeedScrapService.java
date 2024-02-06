package com.ssafy.goumunity.domain.feed.service;

public interface FeedScrapService {

    void createFeedScrap(Long userId, Long feedId);

    void deleteFeedScrap(Long userId, Long feedId);
}
