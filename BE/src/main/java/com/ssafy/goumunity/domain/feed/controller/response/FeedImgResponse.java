package com.ssafy.goumunity.domain.feed.controller.response;

import com.ssafy.goumunity.domain.feed.domain.FeedImg;
import lombok.*;

@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class FeedImgResponse {
    private Long feedImgId;
    private String imgSrc;
    private Integer sequence;

    private Long createdAt;
    private Long updatedAt;

    public static FeedImgResponse from(FeedImg feedImg) {
        return FeedImgResponse.builder()
                .feedImgId(feedImg.getFeedImgId())
                .imgSrc(feedImg.getImgSrc())
                .sequence(feedImg.getSequence())
                .createdAt(feedImg.getCreatedAt().toEpochMilli())
                .updatedAt(feedImg.getUpdatedAt().toEpochMilli())
                .build();
    }
}
