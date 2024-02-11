package com.ssafy.goumunity.domain.feed.service;

import com.ssafy.goumunity.domain.feed.domain.FeedScrap;
import com.ssafy.goumunity.domain.feed.exception.FeedErrorCode;
import com.ssafy.goumunity.domain.feed.exception.FeedException;
import com.ssafy.goumunity.domain.feed.infra.feed.FeedEntity;
import com.ssafy.goumunity.domain.feed.infra.feedscrap.FeedScrapEntity;
import com.ssafy.goumunity.domain.feed.service.post.FeedRepository;
import com.ssafy.goumunity.domain.feed.service.post.FeedScrapRepository;
import com.ssafy.goumunity.domain.user.infra.UserEntity;
import java.time.Instant;
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

        feedScrapRepository.create(
                FeedScrapEntity.builder()
                        .userEntity(UserEntity.userEntityOnlyWithId(userId))
                        .feedEntity(FeedEntity.feedEntityOnlyWithId(feedId))
                        .createdAt(Instant.now())
                        .updatedAt(Instant.now())
                        .build());
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
