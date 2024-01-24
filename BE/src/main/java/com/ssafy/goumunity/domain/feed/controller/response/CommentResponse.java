package com.ssafy.goumunity.domain.feed.controller.response;

import com.querydsl.core.annotations.QueryProjection;
import com.ssafy.goumunity.domain.feed.infra.comment.CommentEntity;
import com.ssafy.goumunity.domain.user.dto.UserResponse;
import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class CommentResponse {
    private Long commentId;
    private String content;
    private Long feedId;
    private UserResponse user;
    private Instant createdAt;
    private Instant updatedAt;

    @QueryProjection
    public CommentResponse(CommentEntity comment) {
        this.commentId = comment.getCommentId();
        this.content = comment.getContent();
        this.feedId = comment.getFeedEntity().getFeedId();
        this.user = UserResponse.from(comment.getUserEntity().toModel());
        this.createdAt = comment.getCreatedAt();
        this.updatedAt = comment.getUpdatedAt();
    }

    public CommentResponse from(CommentEntity comment) {
        return CommentResponse.builder()
                .commentId(comment.getCommentId())
                .content(comment.getContent())
                .feedId(comment.getFeedEntity().getFeedId())
                .user(UserResponse.from(comment.getUserEntity().toModel()))
                .build();
    }
}
