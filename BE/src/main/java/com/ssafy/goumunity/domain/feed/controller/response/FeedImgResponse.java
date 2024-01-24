package com.ssafy.goumunity.domain.feed.controller.response;

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
}
