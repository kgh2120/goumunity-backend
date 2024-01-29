package com.ssafy.goumunity.domain.feed.service;

import static org.junit.jupiter.api.Assertions.*;

import com.ssafy.goumunity.domain.feed.controller.request.FeedRegistRequest;
import com.ssafy.goumunity.domain.feed.domain.FeedCategory;
import com.ssafy.goumunity.domain.feed.service.post.FeedRepository;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class FeedServiceTest {

    @Mock FeedRepository feedRepository;

    @InjectMocks FeedServiceImpl feedService;

    @Nested
    class 입력테스트 {
        @Test
        void 정상입력테스트() {

            FeedRegistRequest feedRegistRequest =
                    FeedRegistRequest.builder()
                            .feedCategory(FeedCategory.FUN)
                            .content("hllo")
                            .price(20000)
                            .afterPrice(10000)
                            .profit(10000)
                            .userId(Long.valueOf(1))
                            .regionId(Long.valueOf(1))
                            .build();

            assertDoesNotThrow(() -> feedService.save(feedRegistRequest));
        }
    }
}
