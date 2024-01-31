package com.ssafy.goumunity.domain.feed.service.post;

import com.ssafy.goumunity.domain.feed.controller.response.FeedResponse;
import com.ssafy.goumunity.domain.feed.domain.Feed;
import java.time.Instant;
import java.util.Optional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface FeedRepository {

    Feed save(Feed feed);

    Slice<FeedResponse> findFeed(Instant time, Pageable pageable);

    FeedResponse findOneFeed(Long feedId);

    Optional<Feed> findOneById(Long feedId);

    void delete(Feed feed);

    boolean existsByFeedId(Long feedId);
}
