package com.ssafy.goumunity.domain.feed.domain;

import com.ssafy.goumunity.common.exception.CustomErrorCode;
import com.ssafy.goumunity.common.exception.CustomException;
import com.ssafy.goumunity.domain.feed.controller.request.CommentRequest;
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

    public void modifyContent(String content) {
        this.content = content;
    }

    public void checkUser(Long userId) {
        if (this.getUserId().equals(userId)) throw new CustomException(CustomErrorCode.INVALID_USER);
    }

    public void checkFeed(Long feedId) {
        if (this.getFeedId().equals(feedId)) throw new CustomException(CustomErrorCode.FEED_NOT_MATCH);
    }

    public static Comment from(Long userId, Long feedId, CommentRequest.Create comment) {
        return Comment.builder().feedId(feedId).userId(userId).content(comment.getContent()).build();
    }
}
