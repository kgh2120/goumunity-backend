package com.ssafy.goumunity.domain.feed.infra.feedimg;

import com.ssafy.goumunity.domain.feed.domain.FeedImg;
import com.ssafy.goumunity.domain.feed.infra.feed.FeedEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.time.Instant;
import lombok.*;

@Entity
@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "feed_img")
public class FeedImgEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "feed_img_id")
    private Long feedImgId;

    @Column(name = "img_src")
    private String imgSrc;

    @NotNull
    @Column(name = "sequence")
    private Integer sequence;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "feed_id")
    private FeedEntity feedEntity;

    @Column(name = "created_at", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Instant createdAt;

    @Column(
            name = "updated_at",
            columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    private Instant updatedAt;

    public FeedImg to() {
        return FeedImg.builder()
                .feedImgId(feedImgId)
                .imgSrc(imgSrc)
                .sequence(sequence)
                .feedId(feedEntity.getFeedId())
                .createdAt(createdAt)
                .updatedAt(updatedAt)
                .build();
    }

    public static FeedImgEntity from(FeedImg feedImg) {
        FeedImgEntityBuilder feedImgEntityBuilder =
                FeedImgEntity.builder()
                        .feedImgId(feedImg.getFeedImgId())
                        .feedEntity(FeedEntity.feedEntityOnlyWithId(feedImg.getFeedId()))
                        .imgSrc(feedImg.getImgSrc())
                        .sequence(feedImg.getSequence());

        if (feedImg.getCreatedAt() != null) feedImgEntityBuilder.createdAt(feedImg.getCreatedAt());
        if (feedImg.getUpdatedAt() != null) feedImgEntityBuilder.updatedAt(feedImg.getUpdatedAt());

        return feedImgEntityBuilder.build();
    }
}
