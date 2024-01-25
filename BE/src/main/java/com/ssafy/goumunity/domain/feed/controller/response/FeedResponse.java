package com.ssafy.goumunity.domain.feed.controller.response;

import com.ssafy.goumunity.domain.feed.domain.Feed;
import lombok.*;

@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class FeedResponse {
    private Long feedId;
    private String content;
    private Integer type;
    private Integer price;
    private Integer afterPrice;
    private Integer profit;

    private Long regionId;
    private Long userId;

    private Long createdAt;
    private Long updatedAt;

    public static FeedResponse from(Feed feed) {
        return FeedResponse.builder()
                .feedId(feed.getFeedId())
                .content(feed.getContent())
                .type(feed.getType())
                .price(feed.getPrice())
                .afterPrice(feed.getAfterPrice())
                .profit(feed.getProfit())
                .regionId(feed.getRegion().getRegionId())
                .userId(feed.getUser().getId())
                .createdAt(feed.getCreatedAt().toEpochMilli())
                .updatedAt(feed.getUpdatedAt().toEpochMilli())
                .build();
    }
}
