package com.ssafy.goumunity.domain.feed.domain;

import com.ssafy.goumunity.domain.feed.controller.response.FeedImgResponse;
import java.time.Instant;
import lombok.*;

@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class FeedImg {
    private Long feedImgId;
    private String imgSrc;
    private Integer sequence;

    private Instant createdAt;
    private Instant updatedAt;

    public FeedImgResponse to() {
        return FeedImgResponse.builder()
                .feedImgId(feedImgId)
                .imgSrc(imgSrc)
                .sequence(sequence)
                .createdAt(createdAt.getEpochSecond())
                .updatedAt(updatedAt.getEpochSecond())
                .build();
    }
}
