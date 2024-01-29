package com.ssafy.goumunity.domain.feed.domain;

import java.time.Instant;
import lombok.*;

@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class CommentLike {
    private Long commentLikeId;

    private Long commentId;
    private Long userId;

    private Instant createdAt;
    private Instant updatedAt;
}
