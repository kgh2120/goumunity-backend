package com.ssafy.goumunity.domain.feed.service.post;

import com.ssafy.goumunity.domain.feed.domain.FeedLike;
import java.util.Optional;

public interface FeedLikeRepository {

    void createFeedLike(FeedLike feedLike);

    Optional<FeedLike> findOneByUserIdAndFeedId(Long userId, Long feedId);

    void deleteFeedLike(FeedLike feedLike);

    boolean existsByFeedLike(FeedLike feedLike);
}
