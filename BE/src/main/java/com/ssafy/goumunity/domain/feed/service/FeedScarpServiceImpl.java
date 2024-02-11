package com.ssafy.goumunity.domain.feed.service;

import com.ssafy.goumunity.domain.feed.domain.FeedScrap;
import com.ssafy.goumunity.domain.feed.exception.FeedErrorCode;
import com.ssafy.goumunity.domain.feed.exception.FeedException;
import com.ssafy.goumunity.domain.feed.service.post.FeedRepository;
import com.ssafy.goumunity.domain.feed.service.post.FeedScrapRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FeedScarpServiceImpl implements FeedScrapService {

    private final FeedScrapRepository feedScrapRepository;
    private final FeedRepository feedRepository;

    @Override
    @Transactional
    public void createFeedScrap(Long userId, Long feedId) {
        if (!feedRepository.existsByFeedId(feedId)) {
            throw new FeedException(FeedErrorCode.FEED_NOT_FOUND);
        }

        if (feedScrapRepository.existByUserIdAndFeedId(userId, feedId)) {
            throw new FeedException(FeedErrorCode.ALREADY_SCARPPED);
        }

        feedScrapRepository.create(FeedScrap.from(userId, feedId));
    }

    @Override
    @Transactional
    public void deleteFeedScrap(Long userId, Long feedId) {
        if (!feedRepository.existsByFeedId(feedId)) {
            throw new FeedException(FeedErrorCode.FEED_NOT_FOUND);
        }

        FeedScrap feedScrap =
                feedScrapRepository
                        .findOneByUserIdAndFeedId(FeedScrap.feedScrapWithUserIdAndFeedId(userId, feedId))
                        .orElseThrow(() -> new FeedException(FeedErrorCode.NO_SCRAP_DATA));

        feedScrapRepository.delete(feedScrap.getId());
    }
}
