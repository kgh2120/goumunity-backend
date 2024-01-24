package com.ssafy.goumunity.domain.feed.infra.feed;

import com.ssafy.goumunity.domain.feed.domain.Feed;
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

    @Column(name = "type")
    private Integer type;

    @Column(name = "price")
    private Integer price;

    @Column(name = "after_price")
    private Integer afterPrice;

    @Column(name = "profit")
    private Integer profit;

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
                .type(type)
                .price(price)
                .afterPrice(afterPrice)
                .profit(profit)
                .region(regionEntity.to())
                .user(userEntity.toModel())
                .createdAt(createdAt)
                .updatedAt(updatedAt)
                .build();
    }

    public FeedEntity from(Feed feed) {
        FeedEntityBuilder feedEntityBuilder =
                FeedEntity.builder()
                        .feedId(feed.getFeedId())
                        .content(feed.getContent())
                        .type(feed.getType())
                        .price(feed.getPrice())
                        .afterPrice(feed.getAfterPrice())
                        .profit(feed.getProfit());

        if (feed.getCreatedAt() != null) feedEntityBuilder.createdAt(feed.getCreatedAt());
        if (feed.getUpdatedAt() != null) feedEntityBuilder.updatedAt(feed.getUpdatedAt());

        return feedEntityBuilder.build();
    }
}
