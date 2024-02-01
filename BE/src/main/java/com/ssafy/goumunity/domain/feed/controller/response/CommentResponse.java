package com.ssafy.goumunity.domain.feed.controller.response;

import com.querydsl.core.annotations.QueryProjection;
import com.ssafy.goumunity.domain.feed.infra.comment.CommentEntity;
import com.ssafy.goumunity.domain.user.controller.response.UserResponse;
import java.time.Instant;
import lombok.*;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Getter
public class CommentResponse {
    private Long commentId;
    private String content;
    private Long feedId;
    private UserResponse user;
    private Instant createdAt;
    private Instant updatedAt;

    private Long replyCount;
    private Long likeCount;

    @QueryProjection
    public CommentResponse(CommentEntity comment, Long replyCount, Long likeCount) {
        this.commentId = comment.getCommentId();
        this.content = comment.getContent();
        this.feedId = comment.getFeedEntity().getFeedId();
        this.user = UserResponse.from(comment.getUserEntity().toModel());
        this.createdAt = comment.getCreatedAt();
        this.updatedAt = comment.getUpdatedAt();
        this.replyCount = replyCount;
        this.likeCount = likeCount;
    }
}
