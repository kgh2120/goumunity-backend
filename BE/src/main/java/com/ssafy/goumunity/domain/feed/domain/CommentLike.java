package com.ssafy.goumunity.domain.feed.domain;

import com.ssafy.goumunity.domain.feed.controller.request.CommentLikeRequest;
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

    public static CommentLike from(CommentLikeRequest commentLikeRequest) {
        return CommentLike.builder()
                .commentId(commentLikeRequest.getCommentId())
                .userId(commentLikeRequest.getUserId())
                .createdAt(Instant.now())
                .updatedAt(Instant.now())
                .build();
    }
}
