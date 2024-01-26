package com.ssafy.goumunity.domain.feed.controller.response;

import com.querydsl.core.annotations.QueryProjection;
import com.ssafy.goumunity.domain.feed.infra.reply.ReplyEntity;
import com.ssafy.goumunity.domain.user.dto.UserResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class ReplyResponse {
    private Long replyId;
    private String content;
    private Long commentId;
    private UserResponse user;
    private Long createdAt;
    private Long updatedAt;

    @QueryProjection
    public ReplyResponse(ReplyEntity reply) {
        this.replyId = reply.getReplyId();
        this.content = reply.getContent();
        this.commentId = reply.getCommentEntity().getCommentId();
        this.user = UserResponse.from(reply.getUserEntity().toModel());
        this.createdAt = reply.getCreatedAt().toEpochMilli();
        this.updatedAt = reply.getUpdatedAt().toEpochMilli();
    }
}
