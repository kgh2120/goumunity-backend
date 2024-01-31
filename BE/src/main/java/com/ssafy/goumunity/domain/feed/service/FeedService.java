package com.ssafy.goumunity.domain.feed.service;

import com.ssafy.goumunity.domain.feed.controller.request.FeedRequest;
import com.ssafy.goumunity.domain.feed.controller.response.FeedRecommendResponse;
import com.ssafy.goumunity.domain.feed.controller.response.FeedResponse;
import java.util.List;

import com.ssafy.goumunity.domain.feed.domain.FeedRecommendResource;
import com.ssafy.goumunity.domain.feed.domain.FeedWeight;
import com.ssafy.goumunity.domain.user.domain.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.web.multipart.MultipartFile;

public interface FeedService {

    void createFeed(Long userId, FeedRequest.Create feedRequest, List<MultipartFile> images);

    FeedRecommendResponse findFeed(User user, Long time, Long regionId);

    FeedResponse findOneByFeedId(Long feedId);

    void deleteFeed(Long userId, Long feedId);
}
