package com.ssafy.goumunity.domain.feed.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

import com.ssafy.goumunity.domain.feed.controller.request.FeedLikeCountRequest;
import com.ssafy.goumunity.domain.feed.controller.request.FeedLikeRequest;
import com.ssafy.goumunity.domain.feed.domain.FeedLike;
import com.ssafy.goumunity.domain.feed.service.post.FeedLikeRepository;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class FeedLikeServiceTest {

    @Mock FeedLikeRepository feedLikeRepository;

    @InjectMocks FeedLikeServiceImpl feedLikeService;

    @Nested
    class 좋아요기능확인 {

        @Test
        @DisplayName("좋아요_갯수확인_성공")
        void 좋아요갯수확인테스트() {

            FeedLikeCountRequest feedLikeCountRequest =
                    FeedLikeCountRequest.builder().feedId(Long.valueOf(1)).build();

            BDDMockito.given(feedLikeRepository.countFeedLikeByFeedId(any())).willReturn(20);

            assertEquals(feedLikeService.countFeedLikeByFeedId(feedLikeCountRequest).getLikeCount(), 20);
        }

        @Test
        @DisplayName("좋아요추가_성공")
        void 좋아요추가() {

            FeedLikeRequest feedLikeRequest =
                    FeedLikeRequest.builder().feedId(Long.valueOf(1)).userId(Long.valueOf(1)).build();

            BDDMockito.given(feedLikeRepository.findOneByFeedIdAndUserId(any(), any()))
                    .willReturn(Optional.empty());

            assertTrue(feedLikeService.pushLikeButton(feedLikeRequest, Long.valueOf(1)));
        }

        @Test
        @DisplayName("좋아요제거_성공")
        void 좋아요제거() {

            FeedLikeRequest feedLikeRequest =
                    FeedLikeRequest.builder().feedId(Long.valueOf(1)).userId(Long.valueOf(1)).build();

            BDDMockito.given(feedLikeRepository.findOneByFeedIdAndUserId(any(), any()))
                    .willReturn(Optional.of(FeedLike.builder().build()));

            assertFalse(feedLikeService.pushLikeButton(feedLikeRequest, Long.valueOf(1)));
        }
    }
}
