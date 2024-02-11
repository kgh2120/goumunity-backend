package com.ssafy.goumunity.domain.feed.infra.feedscrap;

import com.ssafy.goumunity.domain.feed.domain.FeedScrap;
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
@Table(name = "scrap")
public class FeedScrapEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "scrap_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "feed_id")
    private FeedEntity feedEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity userEntity;

    @Column(name = "created_at")
    private Instant createdAt;

    @Column(name = "updated_at")
    private Instant updatedAt;

    public FeedScrap to() {
        return FeedScrap.builder()
                .id(id)
                .userId(userEntity.getId())
                .feedId(feedEntity.getId())
                .createdAt(createdAt)
                .updatedAt(updatedAt)
                .build();
    }

    public static FeedScrapEntity from(FeedScrap feedScrap) {
        FeedScrapEntityBuilder builder =
                FeedScrapEntity.builder()
                        .id(feedScrap.getId())
                        .feedEntity(FeedEntity.feedEntityOnlyWithId(feedScrap.getFeedId()))
                        .userEntity(UserEntity.userEntityOnlyWithId(feedScrap.getUserId()));

        if (feedScrap.getCreatedAt() == null) {
            builder.createdAt(Instant.now());
        } else {
            builder.createdAt(feedScrap.getCreatedAt());
        }

        if (feedScrap.getUpdatedAt() == null) {
            builder.updatedAt(Instant.now());
        } else {
            builder.updatedAt(feedScrap.getUpdatedAt());
        }

        return builder.build();
    }
}
