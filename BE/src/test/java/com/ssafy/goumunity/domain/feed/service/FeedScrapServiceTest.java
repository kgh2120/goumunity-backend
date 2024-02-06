package com.ssafy.goumunity.domain.feed.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

import com.ssafy.goumunity.domain.feed.domain.FeedScrap;
import com.ssafy.goumunity.domain.feed.exception.FeedException;
import com.ssafy.goumunity.domain.feed.service.post.FeedRepository;
import com.ssafy.goumunity.domain.feed.service.post.FeedScrapRepository;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class FeedScrapServiceTest {

    @Mock FeedRepository feedRepository;

    @Mock FeedScrapRepository feedScrapRepository;

    @InjectMocks FeedScarpServiceImpl feedScrapService;

    @Test
    @DisplayName("스크랩_성공")
    void 스크랩() {

        BDDMockito.given(feedRepository.existsByFeedId(any())).willReturn(true);

        BDDMockito.given(feedScrapRepository.existByUserIdAndFeedId(any(), any())).willReturn(false);

        assertDoesNotThrow(() -> feedScrapService.createFeedScrap(Long.valueOf(1), Long.valueOf(1)));
    }

    @Test
    @DisplayName("스크랩_피드없음_실패")
    void 스크랩_피드없음() {

        BDDMockito.given(feedRepository.existsByFeedId(any())).willReturn(false);

        assertThrows(
                FeedException.class,
                () -> feedScrapService.createFeedScrap(Long.valueOf(1), Long.valueOf(1)));
    }

    @Test
    @DisplayName("스크랩_중복스크랩_실패")
    void 스크랩_중복스크랩() {

        BDDMockito.given(feedRepository.existsByFeedId(any())).willReturn(true);

        BDDMockito.given(feedScrapRepository.existByUserIdAndFeedId(any(), any())).willReturn(true);

        assertThrows(
                FeedException.class,
                () -> feedScrapService.createFeedScrap(Long.valueOf(1), Long.valueOf(1)));
    }

    @Test
    @DisplayName("스크린해제_일반_성공")
    void 스크랩해제() {

        BDDMockito.given(feedRepository.existsByFeedId(any())).willReturn(true);

        BDDMockito.given(feedScrapRepository.findOneByUserIdAndFeedId(any()))
                .willReturn(
                        Optional.of(
                                FeedScrap.builder()
                                        .id(Long.valueOf(1))
                                        .feedId(Long.valueOf(1))
                                        .userId(Long.valueOf(1))
                                        .build()));

        assertDoesNotThrow(() -> feedScrapService.deleteFeedScrap(Long.valueOf(1), Long.valueOf(1)));
    }

    @Test
    @DisplayName("스크랩해제_피드없음_실패")
    void 스크랩해제_피드없음() {

        BDDMockito.given(feedRepository.existsByFeedId(any())).willReturn(false);

        assertThrows(
                FeedException.class,
                () -> feedScrapService.deleteFeedScrap(Long.valueOf(1), Long.valueOf(1)));
    }

    @Test
    @DisplayName("스크랩해제_스크랩없음_실패")
    void 스크랩해제_스크랩데이터없음() {

        BDDMockito.given(feedRepository.existsByFeedId(any())).willReturn(true);

        BDDMockito.given(feedScrapRepository.findOneByUserIdAndFeedId(any()))
                .willReturn(Optional.empty());

        assertThrows(
                FeedException.class,
                () -> feedScrapService.deleteFeedScrap(Long.valueOf(1), Long.valueOf(1)));
    }
}
