package com.ssafy.goumunity.domain.feed.controller.response;

import com.ssafy.goumunity.domain.feed.domain.FeedCategory;
import com.ssafy.goumunity.domain.feed.infra.feed.FeedScrapRankingInterface;
import lombok.*;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Getter
public class FeedScrapRankingResponse {
    private Long feedId;
    private String content;
    private FeedCategory category;
    private Long scrapCount;

    public static FeedScrapRankingResponse from(FeedScrapRankingInterface feedScrapRanking) {
        return FeedScrapRankingResponse.builder()
                .feedId(feedScrapRanking.getFeedId())
                .content(feedScrapRanking.getContent())
                .category(feedScrapRanking.getCategory())
                .scrapCount(feedScrapRanking.getScrapCount())
                .build();
    }
}
