package com.ssafy.goumunity.domain.feed.service;

import com.ssafy.goumunity.domain.feed.controller.request.FeedRequest;
import com.ssafy.goumunity.domain.feed.controller.response.FeedResponse;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.web.multipart.MultipartFile;

public interface FeedService {

    void createFeed(Long userId, FeedRequest.Create feedRequest, List<MultipartFile> images);

    Slice<FeedResponse> findFeed(Long time, Pageable pageable);

    void deleteFeed(Long userId, Long feedId);
}
