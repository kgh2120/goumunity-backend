package com.ssafy.goumunity.domain.feed.service;

import com.ssafy.goumunity.domain.feed.domain.FeedLike;
import com.ssafy.goumunity.domain.feed.exception.FeedErrorCode;
import com.ssafy.goumunity.domain.feed.exception.FeedException;
import com.ssafy.goumunity.domain.feed.service.post.FeedLikeRepository;
import com.ssafy.goumunity.domain.feed.service.post.FeedRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FeedLikeServiceImpl implements FeedLikeService {

    private final FeedLikeRepository feedLikeRepository;
    private final FeedRepository feedRepository;

    @Override
    public void createFeedLike(Long userId, Long feedId) {
        if (!feedRepository.existsByFeedId(feedId)) {
            throw new FeedException(FeedErrorCode.FEED_NOT_FOUND);
        }

        FeedLike feedLike = FeedLike.from(userId, feedId);

        if (feedLikeRepository.existsByFeedLike(feedLike)) {
            throw new FeedException(FeedErrorCode.ALREADY_LIKED);
        }

        feedLikeRepository.createFeedLike(feedLike);
    }

    @Override
    public void deleteFeedLike(Long userId, Long feedId) {
        if (!feedRepository.existsByFeedId(feedId)) {
            throw new FeedException(FeedErrorCode.FEED_NOT_FOUND);
        }

        FeedLike feedLike = FeedLike.from(userId, feedId);

        if (!feedLikeRepository.existsByFeedLike(feedLike)) {
            throw new FeedException(FeedErrorCode.NO_LIKE_DATA);
        }

        feedLikeRepository.deleteFeedLike(feedLike);
    }
}
