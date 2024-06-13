package com.ssafy.goumunity.domain.feed.service;

public interface FeedDeleteService {

    void deleteFeed();

    void clearUsersFeed(Long userId);
}
