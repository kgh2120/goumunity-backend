package com.ssafy.goumunity.domain.feed.domain;

import java.time.Instant;
import java.util.Objects;
import lombok.*;

@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class FeedLike {
    private Long feedLikeId;

    private Long feedId;
    private Long userId;

    private Instant createdAt;
    private Instant updatedAt;

    public static FeedLike from(Long userId, Long feedId) {
        return FeedLike.builder().userId(userId).feedId(feedId).build();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FeedLike feedLike = (FeedLike) o;
        return Objects.equals(feedLikeId, feedLike.feedLikeId)
                && Objects.equals(feedId, feedLike.feedId)
                && Objects.equals(userId, feedLike.userId)
                && Objects.equals(createdAt, feedLike.createdAt)
                && Objects.equals(updatedAt, feedLike.updatedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(feedLikeId, feedId, userId, createdAt, updatedAt);
    }
}
