package com.ssafy.goumunity.domain.feed.infra.reply;

import com.ssafy.goumunity.domain.feed.domain.Reply;
import com.ssafy.goumunity.domain.feed.infra.comment.CommentEntity;
import com.ssafy.goumunity.domain.user.infra.UserEntity;
import jakarta.persistence.*;
import java.time.Instant;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "reply")
public class ReplyEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reply_id")
    private Long replyId;

    @Column(name = "content")
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comment_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private CommentEntity commentEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity userEntity;

    @Column(name = "created_at", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Instant createdAt;

    @Column(
            name = "updated_at",
            columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    private Instant updatedAt;

    public Reply to() {
        return Reply.builder()
                .replyId(replyId)
                .content(content)
                .commentId(commentEntity.getCommentId())
                .userId(userEntity.getId())
                .createdAt(createdAt)
                .updatedAt(updatedAt)
                .build();
    }

    public static ReplyEntity from(Reply reply) {

        ReplyEntityBuilder replyEntityBuilder =
                ReplyEntity.builder()
                        .content(reply.getContent())
                        .commentEntity(CommentEntity.commentEntityOnlyWithId(reply.getCommentId()))
                        .userEntity(UserEntity.userEntityOnlyWithId(reply.getUserId()));

        if (reply.getReplyId() != null) replyEntityBuilder.replyId(reply.getReplyId());
        if (reply.getCreatedAt() != null) replyEntityBuilder.createdAt(reply.getCreatedAt());
        if (reply.getUpdatedAt() != null) replyEntityBuilder.updatedAt(reply.getUpdatedAt());

        return replyEntityBuilder.build();
    }

    public static ReplyEntity replyEntityOnlyWithId(Long id) {
        return ReplyEntity.builder().replyId(id).build();
    }
}
