package com.ssafy.goumunity.domain.feed.domain;

import com.ssafy.goumunity.domain.feed.infra.feed.FeedEntity;
import lombok.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Getter
public class SavingResource {
    private Integer price;
    private Integer afterPrice;

    private Long feedId;
    private Long userId;

    private Long createAt;

    public static SavingResource from(FeedEntity feed) {
        return SavingResource.builder()
                .price(feed.getPrice())
                .afterPrice(feed.getAfterPrice())
                .feedId(feed.getId())
                .userId(feed.getUserEntity().getId())
                .createAt(feed.getCreatedAt().toEpochMilli())
                .build();
    }
}
