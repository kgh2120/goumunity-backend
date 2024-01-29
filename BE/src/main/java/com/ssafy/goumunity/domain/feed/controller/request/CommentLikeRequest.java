package com.ssafy.goumunity.domain.feed.controller.request;

import lombok.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Getter
public class CommentLikeRequest {

    private Long commentId;
    private Long userId;
}
