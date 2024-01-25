package com.ssafy.goumunity.domain.feed.service;

import com.ssafy.goumunity.domain.feed.controller.response.FeedImgResponse;
import java.util.List;

public interface FeedImgService {
    FeedImgResponse findOneByFeedImgId(Long feedImgId);

    List<FeedImgResponse> findAllByFeedId(Long feedId);
}
