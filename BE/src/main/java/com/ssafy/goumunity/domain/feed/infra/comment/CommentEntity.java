package com.ssafy.goumunity.domain.feed.infra.comment;

import com.ssafy.goumunity.domain.feed.domain.Comment;
import com.ssafy.goumunity.domain.feed.infra.feed.FeedEntity;
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
    private Long id;

    @Column(name = "content")
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity userEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "feed_id")
    private FeedEntity feedEntity;

    @Column(name = "created_at")
    private Instant createdAt;

    @Column(name = "updated_at")
    private Instant updatedAt;

    public Comment to() {
        return Comment.builder()
                .id(id)
                .content(content)
                .feedId(feedEntity.getId())
                .userId(userEntity.getId())
                .createdAt(createdAt)
                .updatedAt(updatedAt)
                .build();
    }

    public static CommentEntity from(Comment comment) {

        return CommentEntity.builder()
                .id(comment.getId())
                .content(comment.getContent())
                .feedEntity(FeedEntity.feedEntityOnlyWithId(comment.getFeedId()))
                .userEntity(UserEntity.userEntityOnlyWithId(comment.getUserId()))
                .createdAt(comment.getCreatedAt())
                .updatedAt(comment.getUpdatedAt())
                .build();
    }

    public static CommentEntity commentEntityOnlyWithId(Long id) {
        return CommentEntity.builder().id(id).build();
    }
}
