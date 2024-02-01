package com.ssafy.goumunity.domain.feed.service;

import static com.ssafy.goumunity.domain.feed.exception.FeedErrorCode.NO_LIKE_DATA;

import com.ssafy.goumunity.domain.feed.domain.FeedLike;
import com.ssafy.goumunity.domain.feed.exception.FeedErrorCode;
import com.ssafy.goumunity.domain.feed.exception.FeedException;
import com.ssafy.goumunity.domain.feed.service.post.FeedLikeRepository;
import com.ssafy.goumunity.domain.feed.service.post.FeedRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FeedLikeServiceImpl implements FeedLikeService {

    private final FeedLikeRepository feedLikeRepository;
    private final FeedRepository feedRepository;

    @Override
    @Transactional
    public void createFeedLike(Long userId, Long feedId) {
        verifyFeed(feedId);
        FeedLike feedLike = FeedLike.from(userId, feedId);

        if (feedLikeRepository.existsByFeedLike(feedLike)) {
            throw new FeedException(FeedErrorCode.ALREADY_LIKED);
        }

        feedLikeRepository.create(feedLike);
    }

    @Override
    @Transactional
    public void deleteFeedLike(Long userId, Long feedId) {
        verifyFeed(feedId);

        FeedLike feedLike =
                feedLikeRepository
                        .findOneByUserIdAndFeedId(userId, feedId)
                        .orElseThrow(() -> new FeedException(NO_LIKE_DATA));

        feedLikeRepository.delete(feedLike.getId());
    }

    private void verifyFeed(Long feedId) {
        if (!feedRepository.existsByFeedId(feedId)) {
            throw new FeedException(FeedErrorCode.FEED_NOT_FOUND);
        }
    }
}
