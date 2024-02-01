package com.ssafy.goumunity.domain.feed.domain;

import java.time.Instant;
import lombok.*;

@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode(exclude = {"createdAt", "updatedAt"})
public class CommentLike {
    private Long commentLikeId;

    private Long commentId;
    private Long userId;

    private Instant createdAt;
    private Instant updatedAt;

    public static CommentLike from(Long userId, Long commentId) {
        return CommentLike.builder()
                .userId(userId)
                .commentId(commentId)
                .createdAt(Instant.now())
                .updatedAt(Instant.now())
                .build();
    }
}
