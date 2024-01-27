package com.ssafy.goumunity.domain.feed.service;

import com.ssafy.goumunity.common.exception.feed.ResourceNotFoundException;
import com.ssafy.goumunity.domain.feed.controller.request.FeedRegistRequest;
import com.ssafy.goumunity.domain.feed.domain.Feed;
import com.ssafy.goumunity.domain.feed.domain.FeedCategory;
import com.ssafy.goumunity.domain.feed.infra.feed.FeedEntity;
import com.ssafy.goumunity.domain.feed.service.post.FeedRepository;
import com.ssafy.goumunity.domain.region.domain.Region;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class FeedServiceTest {

    @Mock
    FeedRepository feedRepository;

    @InjectMocks
    FeedServiceImpl feedService;

    @Nested
    class 입력테스트 {
        @Test
        void 정상입력테스트() {

            FeedRegistRequest feedRegistRequest = FeedRegistRequest.builder()
                    .feedCategory(FeedCategory.FUN)
                    .content("hllo")
                    .price(20000)
                    .afterPrice(10000)
                    .profit(10000)
                    .userId(Long.valueOf(1))
                    .regionId(Long.valueOf(1))
                    .build();

            Feed feed = Feed.from(feedRegistRequest);

            BDDMockito.given(
                    feedRepository.save(any())
            ).willReturn(feed);

            Feed result = feedService.save(feedRegistRequest);

            SoftAssertions sa = new SoftAssertions();

            sa.assertThat(result.getProfit()).isEqualTo(10000);
            sa.assertThat(result.getPrice()).isEqualTo(20000);

            sa.assertAll();

        }

    }

    @Nested
    class 삭제테스트 {

        @Test
        @DisplayName("정상삭제테스트_성공")
        void 정상삭제() {

            Feed feed = Feed.builder()
                    .feedId(Long.valueOf(1))
                    .content("ㅎㅇ")
                    .price(10000)
                    .afterPrice(5000)
                    .profit(5000)
                    .feedCategory(FeedCategory.INFO)
                    .userId(Long.valueOf(1))
                    .regionId(Long.valueOf(1))
                    .createdAt(Instant.now())
                    .updatedAt(Instant.now())
                    .build();

            BDDMockito.given(
                    feedRepository.findOneByFeedId(any())
            ).willReturn(Optional.of(feed));

            assertDoesNotThrow(() -> feedService.deleteOneByFeedId(Long.valueOf(1)));

        }

        @Test
        @DisplayName("없는건삭제시도_성공")
        void 없는건삭제테스트(){

            BDDMockito.given(
                    feedRepository.findOneByFeedId(any())
            ).willReturn(Optional.empty());

            assertThrows(ResourceNotFoundException.class, () -> feedService.deleteOneByFeedId(Long.valueOf(1)));

        }

    }

}