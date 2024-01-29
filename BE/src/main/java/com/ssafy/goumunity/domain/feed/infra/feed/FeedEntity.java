package com.ssafy.goumunity.domain.feed.infra.feed;

import com.ssafy.goumunity.domain.feed.domain.Feed;
import com.ssafy.goumunity.domain.feed.domain.FeedCategory;
import com.ssafy.goumunity.domain.region.infra.RegionEntity;
import com.ssafy.goumunity.domain.user.infra.UserEntity;
import jakarta.persistence.*;
import java.time.Instant;
import lombok.*;

@Entity
@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "feed")
public class FeedEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "feed_id")
    private Long feedId;

    @Column(name = "content")
    private String content;

    @Column(name = "category")
    @Enumerated(EnumType.STRING)
    private FeedCategory feedCategory;

    @Column(name = "price")
    private Integer price;

    @Column(name = "after_price")
    private Integer afterPrice;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity userEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "region_id")
    private RegionEntity regionEntity;

    @Column(name = "created_at", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Instant createdAt;

    @Column(
            name = "updated_at",
            columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    private Instant updatedAt;

    public Feed to() {
        return Feed.builder()
                .feedId(feedId)
                .content(content)
                .feedCategory(feedCategory)
                .price(price)
                .afterPrice(afterPrice)
                .regionId(regionEntity.getRegionId())
                .userId(userEntity.getId())
                .createdAt(createdAt)
                .updatedAt(updatedAt)
                .build();
    }

    public static FeedEntity from(Feed feed) {
        FeedEntityBuilder feedEntityBuilder =
                FeedEntity.builder()
                        .feedId(feed.getFeedId())
                        .content(feed.getContent())
                        .feedCategory(feed.getFeedCategory())
                        .price(feed.getPrice())
                        .afterPrice(feed.getAfterPrice())
                        .regionEntity(RegionEntity.regionEntityOnlyWithId(feed.getRegionId()))
                        .userEntity(UserEntity.userEntityOnlyWithId(feed.getUserId()));

        if (feed.getCreatedAt() != null) feedEntityBuilder.createdAt(feed.getCreatedAt());
        if (feed.getUpdatedAt() != null) feedEntityBuilder.updatedAt(feed.getUpdatedAt());

        return feedEntityBuilder.build();
    }

    public static FeedEntity feedEntityOnlyWithId(Long id) {
        return FeedEntity.builder().feedId(id).build();
    }
}
