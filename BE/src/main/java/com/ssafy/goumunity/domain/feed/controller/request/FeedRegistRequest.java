package com.ssafy.goumunity.domain.feed.controller.request;

import com.ssafy.goumunity.domain.feed.domain.FeedCategory;
import lombok.*;

@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class FeedRegistRequest {
    private String content;
    private FeedCategory feedCategory;
    private Integer price;
    private Integer afterPrice;
    private Integer profit;

    private Long regionId;
    private Long userId;
}
