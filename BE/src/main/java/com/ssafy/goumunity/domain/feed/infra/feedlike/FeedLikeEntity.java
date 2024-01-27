package com.ssafy.goumunity.domain.feed.infra.feedlike;

import com.ssafy.goumunity.domain.feed.domain.FeedLike;
import com.ssafy.goumunity.domain.feed.infra.feed.FeedEntity;
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
@Table(name = "feed_like")
public class FeedLikeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "feed_like_id")
    private Long feedLikeId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity userEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "feed_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private FeedEntity feedEntity;

    @Column(name = "created_at", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Instant createdAt;

    @Column(
            name = "updated_at",
            columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    private Instant updatedAt;

    public FeedLike to() {
        return FeedLike.builder()
                .feedLikeId(feedLikeId)
                .createdAt(createdAt)
                .updatedAt(updatedAt)
                .build();
    }

    public static FeedLikeEntity from(FeedLike feedLike) {
        FeedLikeEntityBuilder feedLikeEntityBuilder =
                FeedLikeEntity.builder().feedLikeId(feedLike.getFeedLikeId());

        if (feedLike.getCreatedAt() != null) feedLikeEntityBuilder.createdAt(feedLike.getCreatedAt());
        if (feedLike.getUpdatedAt() != null) feedLikeEntityBuilder.updatedAt(feedLike.getUpdatedAt());

        return feedLikeEntityBuilder.build();
    }
}
