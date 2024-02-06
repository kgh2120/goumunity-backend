package com.ssafy.goumunity.domain.feed.infra.feed;

import com.ssafy.goumunity.domain.feed.domain.Feed;
import com.ssafy.goumunity.domain.feed.domain.FeedCategory;
import com.ssafy.goumunity.domain.feed.infra.comment.CommentEntity;
import com.ssafy.goumunity.domain.feed.infra.feedimg.FeedImgEntity;
import com.ssafy.goumunity.domain.feed.infra.feedlike.FeedLikeEntity;
import com.ssafy.goumunity.domain.feed.infra.feedscrap.FeedScrapEntity;
import com.ssafy.goumunity.domain.region.infra.RegionEntity;
import com.ssafy.goumunity.domain.user.infra.UserEntity;
import jakarta.persistence.*;
import java.time.Instant;
import java.util.List;
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
    private Long id;

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

    @OneToMany(mappedBy = "feedEntity", cascade = CascadeType.REMOVE)
    private List<FeedImgEntity> images;

    @OneToMany(mappedBy = "feedEntity", cascade = CascadeType.REMOVE)
    private List<CommentEntity> comments;

    @OneToMany(mappedBy = "feedEntity", cascade = CascadeType.REMOVE)
    private List<FeedLikeEntity> feedLikes;

    @OneToMany(mappedBy = "feedEntity", cascade = CascadeType.REMOVE)
    private List<FeedScrapEntity> feedScraps;

    @Column(name = "created_at")
    private Instant createdAt;

    @Column(name = "updated_at")
    private Instant updatedAt;

    public Feed to() {
        return Feed.builder()
                .id(id)
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
        return FeedEntity.builder()
                .id(feed.getId())
                .content(feed.getContent())
                .feedCategory(feed.getFeedCategory())
                .price(feed.getPrice())
                .afterPrice(feed.getAfterPrice())
                .regionEntity(RegionEntity.regionEntityOnlyWithId(feed.getRegionId()))
                .userEntity(UserEntity.userEntityOnlyWithId(feed.getUserId()))
                .createdAt(feed.getCreatedAt())
                .updatedAt(feed.getUpdatedAt())
                .build();
    }

    public static FeedEntity feedEntityOnlyWithId(Long id) {
        return FeedEntity.builder().id(id).build();
    }
}
