package com.ssafy.goumunity.domain.feed.service.post;

import com.ssafy.goumunity.domain.feed.domain.FeedLike;
import com.ssafy.goumunity.domain.feed.infra.commentlike.CommentLikeEntity;
import com.ssafy.goumunity.domain.feed.infra.feedlike.FeedLikeEntity;

import java.util.Optional;

public interface FeedLikeRepository {

    Optional<FeedLike> findOneByFeedIdAndUserId(Long feedId, Long userId);

    Integer countFeedLikeByFeedId(Long feedId);

    void save(FeedLikeEntity feedLike);

    void delete(FeedLikeEntity feedLike);

    void deleteAllByFeedId(Long feedId);

}
