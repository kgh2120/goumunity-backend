package com.ssafy.goumunity.domain.feed.controller;

import com.ssafy.goumunity.domain.feed.controller.request.FeedRequest;
import com.ssafy.goumunity.domain.feed.controller.response.FeedRecommendResponse;
import com.ssafy.goumunity.domain.feed.controller.response.FeedResponse;
import com.ssafy.goumunity.domain.feed.service.FeedService;
import com.ssafy.goumunity.domain.user.domain.User;
import jakarta.annotation.Nullable;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/feeds")
public class FeedController {

    private final FeedService feedService;

    @PostMapping
    public ResponseEntity<Void> createFeed(
            @AuthenticationPrincipal User user,
            @RequestPart("data") @Valid FeedRequest.Create feedRequest,
            @RequestPart("images") @Nullable List<MultipartFile> images) {
        feedService.createFeed(user.getId(), feedRequest, images);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<FeedRecommendResponse> findFeed(
            @AuthenticationPrincipal User user,
            @RequestParam("time") Long time,
            @RequestParam("regionId") Long regionId) {
        return ResponseEntity.ok(feedService.findFeed(user, time, regionId));
    }

    @GetMapping("/{feed-id}")
    public ResponseEntity<FeedResponse> findOneByFeedId(@PathVariable("feed-id") Long feedId) {
        return ResponseEntity.ok(feedService.findOneByFeedId(feedId));
    }

    @DeleteMapping("/{feed-id}")
    public ResponseEntity<Void> deleteFeed(
            @AuthenticationPrincipal User user, @PathVariable("feed-id") Long feedId) {
        feedService.deleteFeed(user.getId(), feedId);
        return ResponseEntity.ok().build();
    }
}
