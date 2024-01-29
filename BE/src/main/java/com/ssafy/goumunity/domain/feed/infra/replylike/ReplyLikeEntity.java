package com.ssafy.goumunity.domain.feed.infra.replylike;

import com.ssafy.goumunity.domain.feed.domain.ReplyLike;
import com.ssafy.goumunity.domain.feed.infra.reply.ReplyEntity;
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
@Table(name = "reply_like")
public class ReplyLikeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reply_like_id")
    private Long replyLikeId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity userEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reply_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private ReplyEntity replyEntity;

    @Column(name = "created_at", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Instant createdAt;

    @Column(
            name = "updated_at",
            columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    private Instant updatedAt;

    public ReplyLike to() {
        return ReplyLike.builder()
                .replyLikeId(replyLikeId)
                .userId(userEntity.getId())
                .replyId(replyEntity.getReplyId())
                .createdAt(createdAt)
                .updatedAt(updatedAt)
                .build();
    }

    public static ReplyLikeEntity from(ReplyLike replyLike) {
        ReplyLikeEntityBuilder replyLikeEntityBuilder =
                ReplyLikeEntity.builder()
                        .replyLikeId(replyLike.getReplyLikeId())
                        .userEntity(UserEntity.userEntityOnlyWithId(replyLike.getUserId()))
                        .replyEntity(ReplyEntity.replyEntityOnlyWithId(replyLike.getReplyId()));

        if (replyLike.getCreatedAt() != null)
            replyLikeEntityBuilder.createdAt(replyLike.getCreatedAt());
        if (replyLike.getUpdatedAt() != null)
            replyLikeEntityBuilder.updatedAt(replyLike.getUpdatedAt());

        return replyLikeEntityBuilder.build();
    }
}
