package com.ssafy.goumunity.domain.feed.service;

import com.ssafy.goumunity.common.util.TimeUtils;
import com.ssafy.goumunity.domain.feed.controller.request.FeedRequest;
import com.ssafy.goumunity.domain.feed.controller.response.*;
import com.ssafy.goumunity.domain.user.domain.User;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;

public interface FeedService {

    FeedIdWithUser createFeed(User user, FeedRequest.Create feedRequest, List<MultipartFile> images);

    List<FeedRecommend> findFeed(User user, Long regionId);

    FeedResponse findOneFeed(Long userId, Long feedId);

    FeedSearchResult findAllFeedByUserId(Long userId);

    SavingResult findAllSavingByUserId(Long userId);

    FeedSearchResult findAllScrappedFeedByUserId(Long userId);

    List<FeedScrapRankingResponse> findFeedScrapRanking(TimeUtils.TimeKey key);

    void modifyFeed(
            Long userId, Long feedId, FeedRequest.Modify feedRequest, List<MultipartFile> images);

    void deleteFeed(Long userId, Long feedId);

    void clearUserFeed(Long userId);
}
