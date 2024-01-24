package com.ssafy.goumunity.domain.feed.infra.comment;

import com.ssafy.goumunity.domain.feed.controller.response.CommentResponse;
import com.ssafy.goumunity.domain.feed.domain.Comment;
import com.ssafy.goumunity.domain.feed.infra.feed.FeedEntity;
import com.ssafy.goumunity.domain.user.dto.UserResponse;
import com.ssafy.goumunity.domain.user.infra.UserEntity;
import jakarta.persistence.*;
import java.time.Instant;
import lombok.*;

@Entity
@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "comment")
public class CommentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long commentId;

    @Column(name = "content")
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity userEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "feed_id")
    private FeedEntity feedEntity;

    @Column(name = "created_at", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Instant createdAt;

    @Column(
            name = "updated_at",
            columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    private Instant updatedAt;

    public Comment to() {
        return Comment.builder()
                .commentId(commentId)
                .content(content)
                .feedId(feedEntity.getFeedId())
                .userId(userEntity.getId())
                .createdAt(createdAt)
                .updatedAt(updatedAt)
                .build();
    }

    public CommentResponse toResponse() {
        return CommentResponse.builder()
                .commentId(commentId)
                .content(content)
                .feedId(feedEntity.getFeedId())
                .user(UserResponse.from(userEntity.toModel()))
                .createdAt(createdAt)
                .updatedAt(updatedAt)
                .build();
    }

    public static CommentEntity from(Comment comment) {
        CommentEntityBuilder commentEntityBuilder =
                CommentEntity.builder()
                        .content(comment.getContent())
                        .feedEntity(FeedEntity.feedEntityOnlyWithId(comment.getFeedId()))
                        .userEntity(UserEntity.userEntityOnlyWithId(comment.getUserId()));

        if (comment.getCommentId() != null) commentEntityBuilder.commentId(comment.getCommentId());
        if (comment.getCreatedAt() != null) commentEntityBuilder.createdAt(comment.getCreatedAt());
        if (comment.getUpdatedAt() != null) commentEntityBuilder.updatedAt(comment.getUpdatedAt());

        return commentEntityBuilder.build();
    }
}
