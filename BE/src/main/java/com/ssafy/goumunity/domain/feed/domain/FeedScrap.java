package com.ssafy.goumunity.domain.feed.domain;

import java.time.Instant;
import lombok.*;

@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class FeedScrap {

    private Long id;

    private Long userId;
    private Long feedId;

    private Instant createdAt;
    private Instant updatedAt;

    public static FeedScrap feedScrapWithUserIdAndFeedId(Long userId, Long feedId) {
        return FeedScrap.builder().userId(userId).feedId(feedId).build();
    }
}
