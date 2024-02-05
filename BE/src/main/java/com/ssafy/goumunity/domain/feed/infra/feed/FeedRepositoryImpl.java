package com.ssafy.goumunity.domain.feed.infra.feed;

import com.ssafy.goumunity.domain.feed.controller.response.FeedResponse;
import com.ssafy.goumunity.domain.feed.domain.Feed;
import com.ssafy.goumunity.domain.feed.domain.FeedRecommendResource;
import com.ssafy.goumunity.domain.feed.domain.FeedSearchResource;
import com.ssafy.goumunity.domain.feed.domain.SavingResource;
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
    public Feed create(Feed feed) {
        return feedJpaRepository.save(FeedEntity.from(feed)).to();
    }

    @Override
    public List<FeedRecommendResource> findFeed(Long userId, Long regionId) {
        return feedQueryDslRepository.findFeed(userId, regionId);
    }

    @Override
    public FeedResponse findOneFeed(Long userId, Long feedId) {
        return feedQueryDslRepository.findOneFeed(userId, feedId);
    }

    @Override
    public Optional<Feed> findOneById(Long feedId) {
        return feedJpaRepository.findById(feedId).map(FeedEntity::to);
    }

    @Override
    public List<FeedSearchResource> findAllFeedByUserId(Long userId) {
        return feedQueryDslRepository.findAllFeedByUserId(userId);
    }

    @Override
    public List<SavingResource> findAllSavingByUserId(Long userId) {
        return feedJpaRepository
                .findAllByUserEntity_IdAndPriceIsNotNullAndAfterPriceIsNotNull(userId)
                .stream()
                .map(SavingResource::from)
                .toList();
    }

    @Override
    public void modify(Feed feed) {
        feedJpaRepository.save(FeedEntity.from(feed)).to();
    }

    @Override
    public void delete(Long feedId) {
        feedJpaRepository.deleteById(feedId);
    }

    @Override
    public boolean existsByFeedId(Long feedId) {
        return feedJpaRepository.existsById(feedId);
    }
}
