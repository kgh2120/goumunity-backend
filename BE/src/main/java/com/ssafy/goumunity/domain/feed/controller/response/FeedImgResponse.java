package com.ssafy.goumunity.domain.feed.controller.response;

import com.ssafy.goumunity.domain.feed.infra.feedimg.FeedImgEntity;
import lombok.*;

@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class FeedImgResponse {
    private Long feedImgId;
    private String imgSrc;
    private Integer sequence;

    private Long createdAt;
    private Long updatedAt;

    public static FeedImgResponse from(FeedImgEntity feedImg) {
        FeedImgResponseBuilder feedImgResponseBuilder =
                FeedImgResponse.builder()
                        .feedImgId(feedImg.getFeedImgId())
                        .imgSrc(feedImg.getImgSrc())
                        .sequence(feedImg.getSequence());

        if (feedImg.getCreatedAt() != null)
            feedImgResponseBuilder.createdAt(feedImg.getCreatedAt().toEpochMilli());
        if (feedImg.getUpdatedAt() != null)
            feedImgResponseBuilder.updatedAt(feedImg.getUpdatedAt().toEpochMilli());

        return feedImgResponseBuilder.build();
    }
}
