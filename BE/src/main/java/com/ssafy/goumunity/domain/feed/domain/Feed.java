package com.ssafy.goumunity.domain.feed.domain;

import com.ssafy.goumunity.domain.region.domain.Region;
import com.ssafy.goumunity.domain.user.domain.User;
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

    private Region region;
    private User user;

    private Instant createdAt;
    private Instant updatedAt;
}
