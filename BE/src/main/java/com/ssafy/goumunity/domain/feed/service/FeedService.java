package com.ssafy.goumunity.domain.feed.service;

import com.ssafy.goumunity.domain.feed.controller.request.FeedRegistRequest;
import com.ssafy.goumunity.domain.feed.controller.response.FeedResponse;
import com.ssafy.goumunity.domain.feed.domain.Feed;
import com.ssafy.goumunity.domain.user.domain.User;
import java.util.List;

public interface FeedService {
    FeedResponse findOneByFeedId(Long feedId);

    List<FeedResponse> findAllByUserId(Long userId);

    Feed save(FeedRegistRequest feedRegistRequest);

    void deleteOneByFeedId(Long feedId, User user);
}
