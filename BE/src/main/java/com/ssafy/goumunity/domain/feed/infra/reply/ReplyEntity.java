package com.ssafy.goumunity.domain.feed.infra.reply;

import com.ssafy.goumunity.domain.feed.domain.Reply;
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
@Table(name = "reply")
public class ReplyEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reply_id")
    private Long replyId;

    @Column(name = "comment")
    private String comment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comment_id")
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
                .comment(comment)
                .createdAt(createdAt)
                .updatedAt(updatedAt)
                .build();
    }

    public static ReplyEntity from(Reply reply) {

        ReplyEntityBuilder replyEntityBuilder =
                ReplyEntity.builder().replyId(reply.getReplyId()).comment(reply.getComment());

        if (reply.getCreatedAt() != null) replyEntityBuilder.createdAt(reply.getCreatedAt());
        if (reply.getUpdatedAt() != null) replyEntityBuilder.updatedAt(reply.getUpdatedAt());

        return replyEntityBuilder.build();
    }
}
