package com.ssafy.goumunity.domain.feed.domain;

import com.ssafy.goumunity.common.exception.CustomErrorCode;
import com.ssafy.goumunity.common.exception.CustomException;
import com.ssafy.goumunity.domain.feed.controller.request.ReplyRequest;
import java.time.Instant;
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
}
