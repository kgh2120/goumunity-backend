package com.ssafy.goumunity.domain.feed.controller.response;

import com.ssafy.goumunity.domain.feed.domain.FeedRecommendResource;
import lombok.*;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Getter
public class FeedRecommendResponse {
    private List<FeedRecommendResource> feedRecommends;
}
