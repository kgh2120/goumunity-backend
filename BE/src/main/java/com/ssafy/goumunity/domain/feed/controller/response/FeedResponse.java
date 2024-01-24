package com.ssafy.goumunity.domain.feed.controller.response;

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
}
