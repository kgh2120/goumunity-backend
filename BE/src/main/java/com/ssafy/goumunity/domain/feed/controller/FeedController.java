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
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<Long> createFeed(
            @AuthenticationPrincipal User user,
            @RequestPart("data") @Valid FeedRequest.Create feedRequest,
            @RequestPart("images") @Nullable List<MultipartFile> images) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(feedService.createFeed(user.getId(), feedRequest, images));
    }

    @GetMapping
    public ResponseEntity<FeedRecommendResponse> findFeed(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(
                FeedRecommendResponse.builder()
                        .feedRecommends(feedService.findFeed(user, Long.valueOf(user.getRegionId())))
                        .build());
    }

    @GetMapping("/{feedId}")
    public ResponseEntity<FeedResponse> findOneByFeedId(@PathVariable Long feedId) {
        return ResponseEntity.ok(feedService.findOneByFeedId(feedId));
    }

    @PatchMapping("{feedId}")
    public ResponseEntity<Void> modifyFeed(
            @AuthenticationPrincipal User user,
            @PathVariable Long feedId,
            @RequestPart("data") @Valid FeedRequest.Modify feedRequest,
            @RequestPart("images") @Nullable List<MultipartFile> images) {
        feedService.modifyFeed(user.getId(), feedId, feedRequest, images);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{feedId}")
    public ResponseEntity<Void> deleteFeed(
            @AuthenticationPrincipal User user, @PathVariable Long feedId) {
        feedService.deleteFeed(user.getId(), feedId);
        return ResponseEntity.ok().build();
    }
}
