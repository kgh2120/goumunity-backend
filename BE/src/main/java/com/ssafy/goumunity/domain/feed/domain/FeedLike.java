package com.ssafy.goumunity.domain.feed.domain;

import java.time.Instant;
import lombok.*;

@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode(exclude = {"createdAt", "updatedAt"})
public class FeedLike {
    private Long feedLikeId;

    private Long feedId;
    private Long userId;

    private Instant createdAt;
    private Instant updatedAt;

    public static FeedLike from(Long userId, Long feedId) {
        return FeedLike.builder()
                .userId(userId)
                .feedId(feedId)
                .createdAt(Instant.now())
                .updatedAt(Instant.now())
                .build();
    }
}
