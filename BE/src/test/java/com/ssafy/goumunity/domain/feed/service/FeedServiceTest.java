package com.ssafy.goumunity.domain.feed.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

import com.ssafy.goumunity.common.exception.feed.ResourceNotFoundException;
import com.ssafy.goumunity.domain.feed.domain.Feed;
import com.ssafy.goumunity.domain.feed.domain.FeedCategory;
import com.ssafy.goumunity.domain.feed.service.post.FeedRepository;
import com.ssafy.goumunity.domain.user.domain.User;
import java.time.Instant;
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
class FeedServiceTest {

    @Mock FeedRepository feedRepository;

    @InjectMocks FeedServiceImpl feedService;

    @Nested
    class 삭제테스트 {

        @Test
        @DisplayName("정상삭제테스트_성공")
        void 정상삭제() {

            Feed feed =
                    Feed.builder()
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

            User user = User.builder().id(Long.valueOf(1)).build();

            BDDMockito.given(feedRepository.findOneByFeedId(any())).willReturn(Optional.of(feed));

            assertDoesNotThrow(() -> feedService.deleteOneByFeedId(Long.valueOf(1), user));
        }

        @Test
        @DisplayName("없는건삭제시도_성공")
        void 없는건삭제테스트() {

            BDDMockito.given(feedRepository.findOneByFeedId(any())).willReturn(Optional.empty());

            User user = User.builder().id(Long.valueOf(1)).build();

            assertThrows(
                    ResourceNotFoundException.class,
                    () -> feedService.deleteOneByFeedId(Long.valueOf(1), user));
        }
    }
}
