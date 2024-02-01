package com.ssafy.goumunity.domain.feed.domain;

import com.ssafy.goumunity.domain.feed.controller.request.CommentRequest;
import com.ssafy.goumunity.domain.feed.exception.CommentErrorCode;
import com.ssafy.goumunity.domain.feed.exception.CommentException;
import com.ssafy.goumunity.domain.user.exception.UserErrorCode;
import com.ssafy.goumunity.domain.user.exception.UserException;
import java.time.Instant;
import lombok.*;

@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode(exclude = {"createdAt", "updatedAt"})
public class Comment {
    private Long commentId;
    private String content;
    private Long feedId;
    private Long userId;

    private Instant createdAt;
    private Instant updatedAt;

    public void modifyContent(String content) {
        this.content = content;
        this.updatedAt = Instant.now();
    }

    public void checkUser(Long userId) {
        if (!this.getUserId().equals(userId)) throw new UserException(UserErrorCode.INVALID_USER);
    }

    public void checkFeed(Long feedId) {
        if (!this.getFeedId().equals(feedId))
            throw new CommentException(CommentErrorCode.FEED_NOT_MATCH);
    }

    public static Comment from(Long userId, Long feedId, CommentRequest.Create comment) {
        return Comment.builder()
                .feedId(feedId)
                .userId(userId)
                .content(comment.getContent())
                .createdAt(Instant.now())
                .updatedAt(Instant.now())
                .build();
    }
}
