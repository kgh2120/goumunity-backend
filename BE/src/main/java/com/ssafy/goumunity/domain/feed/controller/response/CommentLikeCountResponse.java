package com.ssafy.goumunity.domain.feed.controller.response;

import lombok.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Getter
public class CommentLikeCountResponse {
    private Integer likeCount;

    public static CommentLikeCountResponse from(Integer likeCount){
        return CommentLikeCountResponse.builder()
                .likeCount(likeCount)
                .build();
    }
}
