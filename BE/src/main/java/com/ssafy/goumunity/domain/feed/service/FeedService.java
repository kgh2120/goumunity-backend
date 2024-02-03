package com.ssafy.goumunity.domain.feed.service;

import com.ssafy.goumunity.domain.feed.controller.request.FeedRequest;
import com.ssafy.goumunity.domain.feed.controller.response.FeedRecommend;
import com.ssafy.goumunity.domain.feed.controller.response.FeedResponse;
import com.ssafy.goumunity.domain.user.domain.User;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;

public interface FeedService {

    Long createFeed(Long userId, FeedRequest.Create feedRequest, List<MultipartFile> images);

    List<FeedRecommend> findFeed(User user, Long regionId);

    FeedResponse findOneByFeedId(Long feedId);

    void modifyFeed(
            Long userId, Long feedId, FeedRequest.Modify feedRequest, List<MultipartFile> images);

    void deleteFeed(Long userId, Long feedId);

    void findAllByRecommend(User user, Long regionId);
}
