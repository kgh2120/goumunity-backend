package com.ssafy.goumunity.domain.feed.infra.feedlike;

import com.ssafy.goumunity.domain.feed.domain.FeedLike;
import com.ssafy.goumunity.domain.feed.infra.commentlike.CommentLikeEntity;
import com.ssafy.goumunity.domain.feed.infra.feed.FeedJpaRepository;
import com.ssafy.goumunity.domain.feed.service.post.FeedLikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class FeedLikeRepositoryImpl implements FeedLikeRepository {

    private final FeedLikeJpaRepository feedLikeJpaRepository;

    @Override
    public Optional<FeedLike> findOneByFeedIdAndUserId(Long feedId, Long userId) {
        return feedLikeJpaRepository.findOneByFeedIdAndUserId(feedId, userId).map(FeedLikeEntity::to);
    }

    @Override
    public Integer countFeedLikeByFeedId(Long feedId) {
        return feedLikeJpaRepository.countFeedLikeByFeedId(feedId);
    }

    @Override
    public void save(FeedLikeEntity feedLike) {
        feedLikeJpaRepository.save(feedLike);
    }

    @Override
    public void delete(FeedLikeEntity feedLike) {
        feedLikeJpaRepository.delete(feedLike);
    }

    @Override
    public void deleteAllByFeedId(Long feedId) {
        feedLikeJpaRepository.deleteAllByFeedId(feedId);
    }
}
