package com.ssafy.goumunity.domain.feed.service;

import com.ssafy.goumunity.domain.feed.controller.request.FeedRequest;
import com.ssafy.goumunity.domain.feed.controller.response.FeedResponse;
import com.ssafy.goumunity.domain.user.domain.User;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.web.multipart.MultipartFile;

public interface FeedService {

    void createFeed(Long userId, FeedRequest.Create feedRequest, List<MultipartFile> images);

    Slice<FeedResponse> findFeed(Long time, Pageable pageable);

    //    FeedResponse findOneByFeedId(Long feedId);
    //
    //    List<FeedResponse> findAllByUserId(Long userId);

    void deleteOneByFeedId(Long feedId, User user);
}
