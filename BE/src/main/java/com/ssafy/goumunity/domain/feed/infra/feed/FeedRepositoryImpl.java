package com.ssafy.goumunity.domain.feed.infra.feed;

import com.ssafy.goumunity.domain.feed.controller.response.FeedResponse;
import com.ssafy.goumunity.domain.feed.domain.Feed;
import com.ssafy.goumunity.domain.feed.service.post.FeedRepository;
import java.time.Instant;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class FeedRepositoryImpl implements FeedRepository {

    private final FeedJpaRepository feedJpaRepository;
    private final FeedQueryDslRepository feedQueryDslRepository;

    @Override
    public Feed save(Feed feed) {
        return feedJpaRepository.save(FeedEntity.from(feed)).to();
    }

    @Override
    public Slice<FeedResponse> findFeed(Instant time, Pageable pageable) {
        return feedQueryDslRepository.findFeed(time, pageable);
    }

    @Override
    public FeedResponse findOneFeed(Long feedId) {
        return feedQueryDslRepository.findOneFeed(feedId);
    }

    @Override
    public Optional<Feed> findOneById(Long feedId) {
        return feedJpaRepository.findByFeedId(feedId).map(FeedEntity::to);
    }

    @Override
    public Feed modify(Feed feed) {
        return feedJpaRepository.save(FeedEntity.from(feed)).to();
    }

    @Override
    public void delete(Feed feed) {
        feedJpaRepository.delete(FeedEntity.from(feed));
    }

    @Override
    public boolean existsByFeedId(Long feedId) {
        return feedJpaRepository.existsById(feedId);
    }
}
