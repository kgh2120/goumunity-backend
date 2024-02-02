package com.ssafy.goumunity.domain.feed.infra.feedlike;

import com.ssafy.goumunity.domain.feed.domain.FeedLike;
import com.ssafy.goumunity.domain.feed.service.post.FeedLikeRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class FeedLikeRepositoryImpl implements FeedLikeRepository {

    private final FeedLikeJpaRepository feedLikeJpaRepository;

    @Override
    public void create(FeedLike feedLike) {
        feedLikeJpaRepository.save(FeedLikeEntity.from(feedLike));
    }

    @Override
    public Optional<FeedLike> findOneByUserIdAndFeedId(Long userId, Long feedId) {
        return feedLikeJpaRepository
                .findByUserEntity_IdAndAndFeedEntity_Id(userId, feedId)
                .map(FeedLikeEntity::to);
    }

    @Override
    public void delete(Long feedLikeId) {
        feedLikeJpaRepository.deleteById(feedLikeId);
    }

    @Override
    public boolean existsByFeedLike(FeedLike feedLike) {
        return feedLikeJpaRepository.existsByUserEntity_IdAndFeedEntity_Id(
                feedLike.getUserId(), feedLike.getFeedId());
    }
}
