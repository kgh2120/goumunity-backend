package com.ssafy.goumunity.domain.feed.controller.response;

import com.querydsl.core.annotations.QueryProjection;
import com.ssafy.goumunity.domain.feed.domain.Reply;
import com.ssafy.goumunity.domain.feed.infra.reply.ReplyEntity;
import com.ssafy.goumunity.domain.user.controller.response.UserResponse;
import lombok.*;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Getter
public class ReplyResponse {
    private Long replyId;
    private String content;
    private Long commentId;
    private UserResponse user;
    private Long createdAt;
    private Long updatedAt;
    private Long likeCount;
    private Boolean iLikeThat;

    @QueryProjection
    public ReplyResponse(ReplyEntity reply, Long likeCount, Boolean iLikeThat) {
        this.replyId = reply.getId();
        this.content = reply.getContent();
        this.commentId = reply.getCommentEntity().getId();
        this.user = UserResponse.from(reply.getUserEntity().toModel());
        this.createdAt = reply.getCreatedAt().toEpochMilli();
        this.updatedAt = reply.getUpdatedAt().toEpochMilli();
        this.likeCount = likeCount;
        this.iLikeThat = iLikeThat;
    }

    public static ReplyResponse from(Reply reply, UserResponse user) {
        return ReplyResponse.builder()
                .replyId(reply.getId())
                .content(reply.getContent())
                .commentId(reply.getCommentId())
                .user(user)
                .createdAt(reply.getCreatedAt().toEpochMilli())
                .updatedAt(reply.getUpdatedAt().toEpochMilli())
                .build();
    }
}
