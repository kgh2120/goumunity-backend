package com.ssafy.goumunity.domain.feed.controller.response;

import lombok.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Getter
public class FeedLikeCountResponse {

    private Integer likeCount;

    public static FeedLikeCountResponse from(Integer likeCount){
        return FeedLikeCountResponse.builder()
                .likeCount(likeCount)
                .build();
    }

}
