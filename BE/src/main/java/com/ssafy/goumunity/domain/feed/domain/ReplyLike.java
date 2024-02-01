package com.ssafy.goumunity.domain.feed.domain;

import java.time.Instant;
import lombok.*;

@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode(exclude = {"createdAt", "updatedAt"})
public class ReplyLike {
    private Long replyLikeId;

    private Long replyId;
    private Long userId;

    private Instant createdAt;
    private Instant updatedAt;

    public static ReplyLike from(Long userId, Long replyId) {
        return ReplyLike.builder().userId(userId).replyId(replyId).build();
    }
}
