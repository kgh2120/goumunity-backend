package com.ssafy.goumunity.domain.feed.controller;

import com.ssafy.goumunity.common.util.SliceResponse;
import com.ssafy.goumunity.domain.feed.controller.request.FeedRequest;
import com.ssafy.goumunity.domain.feed.controller.response.FeedResponse;
import com.ssafy.goumunity.domain.feed.service.FeedService;
import com.ssafy.goumunity.domain.user.domain.User;
import jakarta.annotation.Nullable;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
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
    public ResponseEntity<Void> createFeed(
            @AuthenticationPrincipal User user,
            @RequestPart("data") @Valid FeedRequest.Create feedRequest,
            @RequestPart("images") @Nullable List<MultipartFile> images) {
        feedService.createFeed(user.getId(), feedRequest, images);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    public ResponseEntity<SliceResponse<FeedResponse>> findFeed(
            @RequestParam("time") Long time, Pageable pageable) {
        Slice<FeedResponse> feeds = feedService.findFeed(time, pageable);
        return ResponseEntity.ok(SliceResponse.from(feeds.getContent(), feeds.hasNext()));
    }

    @GetMapping("/{feed-id}")
    public ResponseEntity<FeedResponse> findOneByFeedId(@PathVariable("feed-id") Long feedId) {
        return ResponseEntity.ok(feedService.findOneByFeedId(feedId));
    }

    @PutMapping("{feed-id}")
    public ResponseEntity<Void> modifyFeed(
            @AuthenticationPrincipal User user,
            @PathVariable("feed-id") Long feedId,
            @RequestPart("data") @Valid FeedRequest.Modify feedRequest,
            @RequestPart("images") @Nullable List<MultipartFile> images) {
        feedService.modifyFeed(user.getId(), feedId, feedRequest, images);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{feed-id}")
    public ResponseEntity<Void> deleteFeed(
            @AuthenticationPrincipal User user, @PathVariable("feed-id") Long feedId) {
        feedService.deleteFeed(user.getId(), feedId);
        return ResponseEntity.ok().build();
    }
}
