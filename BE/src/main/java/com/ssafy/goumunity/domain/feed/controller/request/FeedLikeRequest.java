package com.ssafy.goumunity.domain.feed.controller.request;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Getter
public class FeedLikeRequest {

    @NotNull(message = "피드 ID는 필수 입력사항입니다.")
    private Long feedId;

    @NotNull(message = "유저 ID는 필수 입력사항입니다.")
    private Long userId;
}
