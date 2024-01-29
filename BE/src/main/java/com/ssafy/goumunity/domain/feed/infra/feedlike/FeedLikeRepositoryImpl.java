package com.ssafy.goumunity.domain.feed.infra.feedlike;

import com.ssafy.goumunity.domain.feed.domain.FeedLike;
import com.ssafy.goumunity.domain.feed.service.post.FeedLikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class FeedLikeRepositoryImpl implements FeedLikeRepository {

    private final FeedLikeJpaRepository feedLikeJpaRepository;

    @Override
    public void createFeedLike(FeedLike feedLike) {
        feedLikeJpaRepository.save(FeedLikeEntity.from(feedLike));
    }

    @Override
    public void deleteFeedLike(FeedLike feedLike) {
        feedLikeJpaRepository.delete(FeedLikeEntity.from(feedLike));
    }

    @Override
    public boolean existsByFeedLike(FeedLike feedLike) {
        return feedLikeJpaRepository.existsByUserEntity_IdAndFeedEntity_FeedId(
                feedLike.getUserId(), feedLike.getFeedId());
    }
}
