package com.ssafy.goumunity.domain.feed.domain;

import com.ssafy.goumunity.domain.feed.controller.request.ReplyRequest;
import com.ssafy.goumunity.domain.feed.exception.ReplyErrorCode;
import com.ssafy.goumunity.domain.feed.exception.ReplyException;
import com.ssafy.goumunity.domain.user.exception.UserErrorCode;
import com.ssafy.goumunity.domain.user.exception.UserException;
import java.time.Instant;
import lombok.*;

@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(exclude = {"createdAt", "updatedAt"})
public class Reply {
    private Long replyId;
    private String content;
    private Long commentId;
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

    public void checkComment(Long commentId) {
        if (!this.getCommentId().equals(commentId))
            throw new ReplyException(ReplyErrorCode.COMMENT_NOT_MATCH);
    }

    public static Reply from(Long userId, Long commentId, ReplyRequest.Create reply) {
        return Reply.builder()
                .commentId(commentId)
                .userId(userId)
                .content(reply.getContent())
                .createdAt(Instant.now())
                .updatedAt(Instant.now())
                .build();
    }
}
