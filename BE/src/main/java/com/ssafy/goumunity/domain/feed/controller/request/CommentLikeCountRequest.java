package com.ssafy.goumunity.domain.feed.controller.request;

import lombok.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Builder
public class CommentLikeCountRequest {

    private Long commentId;
}
