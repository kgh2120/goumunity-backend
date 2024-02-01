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

    @Column(name = "content")
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comment_id")
    private CommentEntity commentEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity userEntity;

    @Column(name = "created_at")
    private Instant createdAt;

    @Column(name = "updated_at")
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
        return ReplyEntity.builder()
                .content(reply.getContent())
                .commentEntity(CommentEntity.commentEntityOnlyWithId(reply.getCommentId()))
                .userEntity(UserEntity.userEntityOnlyWithId(reply.getUserId()))
                .replyId(reply.getReplyId())
                .createdAt(reply.getCreatedAt())
                .updatedAt(reply.getUpdatedAt())
                .build();
    }

    public static ReplyEntity replyEntityOnlyWithId(Long id) {
        return ReplyEntity.builder().replyId(id).build();
    }
}
