package com.ssafy.goumunity.domain.feed.infra.commentlike;

import com.ssafy.goumunity.domain.feed.domain.CommentLike;
import com.ssafy.goumunity.domain.feed.infra.comment.CommentEntity;
import com.ssafy.goumunity.domain.user.infra.UserEntity;
import jakarta.persistence.*;
import java.time.Instant;
import lombok.*;

@Entity
@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "comment_like")
public class CommentLikeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_like_id")
    private Long commentLikeId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity userEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comment_id")
    private CommentEntity commentEntity;

    @Column(name = "created_at")
    private Instant createdAt;

    @Column(name = "updated_at")
    private Instant updatedAt;

    public CommentLike to() {
        return CommentLike.builder()
                .id(commentLikeId)
                .commentId(commentEntity.getId())
                .userId(userEntity.getId())
                .createdAt(createdAt)
                .updatedAt(updatedAt)
                .build();
    }

    public static CommentLikeEntity from(CommentLike commentLike) {

        return CommentLikeEntity.builder()
                .commentLikeId(commentLike.getId())
                .userEntity(UserEntity.userEntityOnlyWithId(commentLike.getUserId()))
                .commentEntity(CommentEntity.commentEntityOnlyWithId(commentLike.getCommentId()))
                .createdAt(commentLike.getCreatedAt())
                .updatedAt(commentLike.getUpdatedAt())
                .build();
    }
}
