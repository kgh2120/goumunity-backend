package com.ssafy.goumunity.domain.feed.infra.replylike;

import com.ssafy.goumunity.domain.feed.domain.ReplyLike;
import com.ssafy.goumunity.domain.feed.infra.reply.ReplyEntity;
import com.ssafy.goumunity.domain.user.infra.UserEntity;
import jakarta.persistence.*;
import java.time.Instant;
import lombok.*;

@Entity
@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "reply_like")
public class ReplyLikeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reply_like_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity userEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reply_id")
    private ReplyEntity replyEntity;

    @Column(name = "created_at")
    private Instant createdAt;

    @Column(name = "updated_at")
    private Instant updatedAt;

    public ReplyLike to() {
        return ReplyLike.builder()
                .id(id)
                .userId(userEntity.getId())
                .replyId(replyEntity.getId())
                .createdAt(createdAt)
                .updatedAt(updatedAt)
                .build();
    }

    public static ReplyLikeEntity from(ReplyLike replyLike) {
        return ReplyLikeEntity.builder()
                .id(replyLike.getId())
                .userEntity(UserEntity.userEntityOnlyWithId(replyLike.getUserId()))
                .replyEntity(ReplyEntity.replyEntityOnlyWithId(replyLike.getReplyId()))
                .createdAt(replyLike.getCreatedAt())
                .updatedAt(replyLike.getUpdatedAt())
                .build();
    }
}
