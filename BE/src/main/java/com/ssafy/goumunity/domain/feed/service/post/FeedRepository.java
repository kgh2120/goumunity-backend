package com.ssafy.goumunity.domain.feed.service.post;

import com.ssafy.goumunity.domain.feed.controller.request.FeedRegistRequest;
import com.ssafy.goumunity.domain.feed.domain.Feed;
import java.util.List;
import java.util.Optional;

public interface FeedRepository {
    Optional<Feed> findOneByFeedId(Long feedId);

    List<Feed> findAllByUserId(Long userId);

    Feed save(FeedRegistRequest feedRegistRequest);
}
