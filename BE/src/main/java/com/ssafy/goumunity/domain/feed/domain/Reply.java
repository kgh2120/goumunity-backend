package com.ssafy.goumunity.domain.feed.domain;

import com.ssafy.goumunity.common.exception.CustomErrorCode;
import com.ssafy.goumunity.common.exception.CustomException;
import com.ssafy.goumunity.domain.feed.controller.request.ReplyRequest;
import java.time.Instant;
import java.util.Objects;
import lombok.*;

@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class Reply {
    private Long replyId;
    private String content;
    private Long commentId;
    private Long userId;

    private Instant createdAt;
    private Instant updatedAt;

    public void checkComment(Long commentId) {
        if (!this.getCommentId().equals(commentId))
            throw new CustomException(CustomErrorCode.COMMENT_NOT_MATCH);
    }

    public static Reply from(Long userId, Long commentId, ReplyRequest.Create reply) {
        return Reply.builder().commentId(commentId).userId(userId).content(reply.getContent()).build();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reply reply = (Reply) o;
        return Objects.equals(replyId, reply.replyId)
                && Objects.equals(content, reply.content)
                && Objects.equals(commentId, reply.commentId)
                && Objects.equals(userId, reply.userId)
                && Objects.equals(createdAt, reply.createdAt)
                && Objects.equals(updatedAt, reply.updatedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(replyId, content, commentId, userId, createdAt, updatedAt);
    }
}
