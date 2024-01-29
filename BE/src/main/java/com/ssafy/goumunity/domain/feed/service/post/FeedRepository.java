package com.ssafy.goumunity.domain.feed.service.post;

import com.ssafy.goumunity.domain.feed.domain.Feed;
import com.ssafy.goumunity.domain.feed.infra.feed.FeedEntity;
import java.util.List;
import java.util.Optional;

public interface FeedRepository {
    Optional<Feed> findOneByFeedId(Long feedId);

    List<Feed> findAllByUserId(Long userId);

    void save(FeedEntity feedEntity);
}
