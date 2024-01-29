package com.ssafy.goumunity.domain.feed.infra.feedimg;

import com.ssafy.goumunity.domain.feed.domain.FeedImg;
import com.ssafy.goumunity.domain.feed.infra.feed.FeedEntity;
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
@Table(name = "feed_img")
public class FeedImgEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "feed_img_id")
    private Long feedImgId;

    @Column(name = "img_src")
    private String imgSrc;

    @Column(name = "sequence")
    private Integer sequence;

    @Column(name = "size")
    private Long size;

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

    public FeedImg to() {
        return FeedImg.builder()
                .feedImgId(feedImgId)
                .imgSrc(imgSrc)
                .sequence(sequence)
                .createdAt(createdAt)
                .updatedAt(updatedAt)
                .build();
    }

    public static FeedImgEntity from(FeedImg feedImg) {
        FeedImgEntityBuilder feedImgEntityBuilder =
                FeedImgEntity.builder()
                        .feedImgId(feedImg.getFeedImgId())
                        .imgSrc(feedImg.getImgSrc())
                        .sequence(feedImg.getSequence());

        if (feedImg.getCreatedAt() != null) feedImgEntityBuilder.createdAt(feedImg.getCreatedAt());
        if (feedImg.getUpdatedAt() != null) feedImgEntityBuilder.updatedAt(feedImg.getUpdatedAt());

        return feedImgEntityBuilder.build();
    }
}
