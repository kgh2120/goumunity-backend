package com.ssafy.goumunity.domain.feed.domain;

import com.ssafy.goumunity.domain.feed.controller.request.FeedLikeRequest;
import java.time.Instant;
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

    public static FeedLike from(FeedLikeRequest feedLikeRequest) {
        return FeedLike.builder()
                .feedId(feedLikeRequest.getFeedId())
                .userId(feedLikeRequest.getUserId())
                .createdAt(Instant.now())
                .updatedAt(Instant.now())
                .build();
    }
}
