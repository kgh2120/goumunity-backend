package com.ssafy.goumunity.domain.feed.service;

import com.ssafy.goumunity.domain.feed.controller.request.FeedLikeCountRequest;
import com.ssafy.goumunity.domain.feed.controller.request.FeedLikeRequest;
import com.ssafy.goumunity.domain.feed.controller.response.FeedLikeCountResponse;

public interface FeedLikeService {

    boolean pushLikeButton(FeedLikeRequest feedLikeRequest, Long nowUserId);

    FeedLikeCountResponse countFeedLikeByFeedId(FeedLikeCountRequest feedLikeCountRequest);
}
