package com.ssafy.goumunity.domain.feed.controller.response;

import java.util.List;
import lombok.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Getter
public class FeedRecommendResponse {
    private List<FeedRecommend> feedRecommends;
    private Boolean hasNext;
    private Integer pageNumber;
    private Integer maxPage;

    public static FeedRecommendResponse from(
            List<FeedRecommend> recommends, Boolean hasNext, Integer pageNumber, Integer maxPage) {
        return FeedRecommendResponse.builder()
                .feedRecommends(recommends)
                .hasNext(hasNext)
                .pageNumber(pageNumber)
                .maxPage(maxPage)
                .build();
    }
}
