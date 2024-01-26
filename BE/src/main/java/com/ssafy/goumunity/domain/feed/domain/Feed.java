package com.ssafy.goumunity.domain.feed.domain;

import com.ssafy.goumunity.domain.feed.controller.request.FeedRegistRequest;
import java.time.Instant;
import lombok.*;

@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class Feed {
    private Long feedId;
    private String content;
    private FeedCategory feedCategory;
    private Integer price;
    private Integer afterPrice;
    private Integer profit;

    private Long regionId;
    private Long userId;

    private Instant createdAt;
    private Instant updatedAt;

    public static Feed from(FeedRegistRequest feedRegistRequest) {
        return Feed.builder()
                .content(feedRegistRequest.getContent())
                .feedCategory(feedRegistRequest.getFeedCategory())
                .price(feedRegistRequest.getPrice())
                .afterPrice(feedRegistRequest.getAfterPrice())
                .profit(feedRegistRequest.getProfit())
                .regionId(feedRegistRequest.getRegionId())
                .userId(feedRegistRequest.getUserId())
                .createdAt(Instant.now())
                .updatedAt(Instant.now())
                .build();
    }
}
