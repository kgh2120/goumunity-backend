package com.ssafy.goumunity.domain.feed.service.post;

import com.ssafy.goumunity.domain.feed.domain.FeedScrap;
import com.ssafy.goumunity.domain.feed.infra.feedscrap.FeedScrapEntity;
import java.util.Optional;

public interface FeedScrapRepository {

    void create(FeedScrapEntity feedScrapEntity);

    void delete(Long feedScrapId);

    Optional<FeedScrap> findOneByUserIdAndFeedId(Long userId, Long feedId);

    boolean existByUserIdAndFeedId(Long userId, Long feedId);
}
