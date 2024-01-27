package com.ssafy.goumunity.domain.feed.service.post;

import com.ssafy.goumunity.domain.feed.controller.request.FeedRegistRequest;
import com.ssafy.goumunity.domain.feed.domain.Feed;
import com.ssafy.goumunity.domain.feed.domain.FeedCategory;
import com.ssafy.goumunity.domain.feed.infra.feed.FeedEntity;
import com.ssafy.goumunity.domain.feed.infra.feed.FeedJpaRepository;
import com.ssafy.goumunity.domain.feed.infra.feed.FeedRepositoryImpl;
import com.ssafy.goumunity.domain.region.infra.RegionEntity;
import com.ssafy.goumunity.domain.user.infra.UserEntity;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class FeedRepositoryTest {

    @Mock
    FeedJpaRepository feedJpaRepository;

    @InjectMocks
    FeedRepositoryImpl feedRepository;

    @Nested
    class 등록{

        @Test
        @DisplayName("단건정상입력테스트")
        void 정상입력테스트() {

            FeedEntity feedEntity = FeedEntity.builder()
                    .feedId(Long.valueOf(1))
                    .feedCategory(FeedCategory.FUN)
                    .price(20000)
                    .afterPrice(10000)
                    .profit(10000)
                    .userEntity(UserEntity.builder().build())
                    .regionEntity(RegionEntity.builder().build())
                    .build();

            BDDMockito.given(feedJpaRepository.save(any())).willReturn(feedEntity);

            Feed result = feedRepository.save(feedEntity);

            SoftAssertions sa = new SoftAssertions();

            sa.assertThat(result.getPrice()).isEqualTo(20000);
            sa.assertThat(result.getProfit()).isEqualTo(10000);

            sa.assertAll();
        }

    }
}