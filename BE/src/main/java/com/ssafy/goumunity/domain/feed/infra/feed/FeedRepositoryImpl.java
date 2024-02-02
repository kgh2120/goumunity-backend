package com.ssafy.goumunity.domain.feed.infra.feed;

import com.ssafy.goumunity.domain.feed.controller.response.FeedResponse;
import com.ssafy.goumunity.domain.feed.domain.Feed;
import com.ssafy.goumunity.domain.feed.domain.FeedRecommendResource;
import com.ssafy.goumunity.domain.feed.service.post.FeedRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
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
    public List<FeedRecommendResource> findFeed(Long userId, Long regionId) {
        return feedQueryDslRepository.findFeed(userId, regionId);
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
