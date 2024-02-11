package com.ssafy.goumunity.domain.feed.infra.feed;

import com.ssafy.goumunity.domain.feed.domain.FeedCategory;

public interface FeedScrapRankingInterface {
    Long getFeedId();

    String getContent();

    FeedCategory getCategory();

    Long getScrapCount();
}
