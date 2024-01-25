package com.ssafy.goumunity.domain.feed.domain;

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
}
