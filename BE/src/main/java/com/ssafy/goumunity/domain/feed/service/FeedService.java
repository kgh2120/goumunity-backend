package com.ssafy.goumunity.domain.feed.service;

import com.ssafy.goumunity.domain.feed.controller.response.FeedResponse;
import java.util.List;

public interface FeedService {
    FeedResponse findOneByFeedId(Long feedId);

    List<FeedResponse> findAllByUserId(Long userId);
}
