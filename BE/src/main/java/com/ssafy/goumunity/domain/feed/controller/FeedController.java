package com.ssafy.goumunity.domain.feed.controller;

import com.ssafy.goumunity.domain.feed.controller.response.FeedImgResponse;
import com.ssafy.goumunity.domain.feed.controller.response.FeedResponse;
import com.ssafy.goumunity.domain.feed.service.FeedImgService;
import com.ssafy.goumunity.domain.feed.service.FeedService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/feeds")
public class FeedController {

    private final FeedService feedService;
    private final FeedImgService feedImgService;

    @GetMapping("/{feedId}")
    public ResponseEntity<FeedResponse> findOneByFeedId(@PathVariable Long feedId) {
        return ResponseEntity.ok(feedService.findOneByFeedId(feedId));
    }

    @GetMapping("/{feedId}/feedimgs")
    public ResponseEntity<List<FeedImgResponse>> findAllByFeedId(@PathVariable Long feedId) {
        return ResponseEntity.ok(feedImgService.findAllByFeedId(feedId));
    }
}
