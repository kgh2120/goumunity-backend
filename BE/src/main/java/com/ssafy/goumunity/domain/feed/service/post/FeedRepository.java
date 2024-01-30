package com.ssafy.goumunity.domain.feed.service.post;

import com.ssafy.goumunity.domain.feed.controller.response.FeedResponse;
import com.ssafy.goumunity.domain.feed.domain.Feed;
import com.ssafy.goumunity.domain.feed.infra.feed.FeedEntity;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface FeedRepository {

    Feed save(Feed feed);

    Slice<FeedResponse> find(Instant time, Pageable pageable);

    Optional<Feed> findOneByFeedId(Long feedId);

    List<Feed> findAllByUserId(Long userId);

    void delete(FeedEntity feedEntity);

    boolean existsByFeedId(Long feedId);
}
