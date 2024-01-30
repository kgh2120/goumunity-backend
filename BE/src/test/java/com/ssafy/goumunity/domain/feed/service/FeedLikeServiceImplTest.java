package com.ssafy.goumunity.domain.feed.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import com.ssafy.goumunity.domain.feed.domain.FeedLike;
import com.ssafy.goumunity.domain.feed.exception.FeedErrorCode;
import com.ssafy.goumunity.domain.feed.exception.FeedException;
import com.ssafy.goumunity.domain.feed.service.post.FeedLikeRepository;
import com.ssafy.goumunity.domain.feed.service.post.FeedRepository;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class FeedLikeServiceImplTest {

    @Mock FeedLikeRepository feedLikeRepository;

    @Mock FeedRepository feedRepository;

    @InjectMocks FeedLikeServiceImpl feedLikeService;

    @DisplayName("피드 좋아요 성공")
    @Test
    void feedLike() {
        long userId = 1L;
        long feedId = 1L;

        given(feedRepository.existsByFeedId(any())).willReturn(true);
        given(feedLikeRepository.existsByFeedLike(any())).willReturn(false);

        feedLikeService.createFeedLike(userId, feedId);
        verify(feedLikeRepository).createFeedLike(FeedLike.from(userId, feedId));
    }

    @DisplayName("피드 좋아요 실패_해당 피드 없음")
    @Test
    void feedLikeFailWithFeedNotMatch() {
        long userId = 1L;
        long feedId = 1L;

        given(feedRepository.existsByFeedId(any())).willReturn(false);
        Assertions.assertThrows(
                FeedException.class, () -> feedLikeService.createFeedLike(userId, feedId));
    }

    @DisplayName("피드 좋아요 실패_이미 존재하는 좋아요")
    @Test
    void feedLikeFailWithAlreadyLiked() {
        long userId = 1L;
        long feedId = 1L;

        given(feedRepository.existsByFeedId(any())).willReturn(true);
        given(feedLikeRepository.existsByFeedLike(any())).willReturn(true);

        Assertions.assertThrows(
                FeedException.class, () -> feedLikeService.createFeedLike(userId, feedId));
    }

    @DisplayName("피드 좋아요 취소 성공")
    @Test
    void feedUnlike() {
        long userId = 1L;
        long feedId = 1L;

        given(feedRepository.existsByFeedId(any())).willReturn(true);
        given(feedLikeRepository.findOneByUserIdAndFeedId(any(), any()))
                .willReturn(Optional.ofNullable(FeedLike.from(userId, feedId)));

        feedLikeService.deleteFeedLike(userId, feedId);
        verify(feedLikeRepository).deleteFeedLike(FeedLike.from(userId, feedId));
    }

    @DisplayName("피드 좋아요 취소 실패_해당 피드 없음")
    @Test
    void feedUnlikeFailWithFeedNotMatch() {
        long userId = 1L;
        long feedId = 1L;

        given(feedRepository.existsByFeedId(any())).willReturn(false);
        Assertions.assertThrows(
                FeedException.class, () -> feedLikeService.deleteFeedLike(userId, feedId));
    }

    @DisplayName("피드 좋아요 취소 실패_존재하지 않는 좋아요")
    @Test
    void feedUnlikeFailWithNoLikeData() {
        long userId = 1L;
        long feedId = 1L;

        given(feedRepository.existsByFeedId(any())).willReturn(true);
        given(feedLikeRepository.findOneByUserIdAndFeedId(any(), any()))
                .willThrow(new FeedException(FeedErrorCode.NO_LIKE_DATA));
        Assertions.assertThrows(
                FeedException.class, () -> feedLikeService.deleteFeedLike(userId, feedId));
    }
}
