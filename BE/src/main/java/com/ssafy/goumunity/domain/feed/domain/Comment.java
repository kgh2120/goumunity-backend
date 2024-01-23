package com.ssafy.goumunity.domain.feed.domain;

import com.ssafy.goumunity.domain.feed.controller.request.CommentRegistRequest;
import java.time.Instant;
import lombok.*;

@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment {
    private Long commentId;
    private String content;
    private Long feedId;
    private Long userId;

    private Instant createdAt;
    private Instant updatedAt;

    public static Comment from(Long userId, Long feedId, CommentRegistRequest comment) {
        return Comment.builder().feedId(feedId).userId(userId).content(comment.getContent()).build();
    }
}
