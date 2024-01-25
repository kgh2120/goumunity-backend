package com.ssafy.goumunity.domain.feed.domain;

import java.time.Instant;
import lombok.*;

@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class Feed {
    private Long feedId;
    private String content;
    private Integer type;
    private Integer price;
    private Integer afterPrice;
    private Integer profit;

    private Long regionId;
    private Long userId;

    private Instant createdAt;
    private Instant updatedAt;
}
