package com.ssafy.goumunity.domain.feed.controller.response;

import com.ssafy.goumunity.domain.feed.domain.Feed;
import com.ssafy.goumunity.domain.feed.domain.FeedCategory;
import lombok.*;

@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class FeedResponse {
    private Long feedId;
    private String content;
    private FeedCategory feedCategory;
    private Integer price;
    private Integer afterPrice;

    private Long regionId;
    private Long userId;

    private Long createdAt;
    private Long updatedAt;

    public static FeedResponse from(Feed feed) {
        return FeedResponse.builder()
                .feedId(feed.getFeedId())
                .content(feed.getContent())
                .feedCategory(feed.getFeedCategory())
                .price(feed.getPrice())
                .afterPrice(feed.getAfterPrice())
                .regionId(feed.getRegionId())
                .userId(feed.getUserId())
                .createdAt(feed.getCreatedAt().toEpochMilli())
                .updatedAt(feed.getUpdatedAt().toEpochMilli())
                .build();
    }
}
