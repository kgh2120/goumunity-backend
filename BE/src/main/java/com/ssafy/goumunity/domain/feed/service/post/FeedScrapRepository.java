package com.ssafy.goumunity.domain.feed.service.post;

import com.ssafy.goumunity.domain.feed.domain.FeedScrap;
import java.util.Optional;

public interface FeedScrapRepository {

    void create(FeedScrap feedScrap);

    void delete(Long feedScrapId);

    Optional<FeedScrap> findOneByUserIdAndFeedId(FeedScrap feedScrap);

    boolean existByUserIdAndFeedId(Long userId, Long feedId);
}
