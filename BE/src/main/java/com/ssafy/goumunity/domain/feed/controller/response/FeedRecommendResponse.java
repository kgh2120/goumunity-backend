package com.ssafy.goumunity.domain.feed.controller.response;

import java.util.List;
import lombok.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Getter
public class FeedRecommendResponse {
    private List<FeedRecommend> feedRecommends;
}
