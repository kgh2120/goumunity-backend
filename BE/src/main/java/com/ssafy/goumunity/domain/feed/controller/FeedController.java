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
    public ResponseEntity<SliceResponse<FeedResponse>> find(
            @RequestParam("time") Long time, Pageable pageable) {
        Slice<FeedResponse> feeds = feedService.find(time, pageable);
        return ResponseEntity.ok(SliceResponse.from(feeds.getContent(), feeds.hasNext()));
    }

    @DeleteMapping("/{feedId}")
    public ResponseEntity<Void> deleteOneByFeedId(
            @PathVariable Long feedId, @AuthenticationPrincipal User user) {
        feedService.deleteOneByFeedId(feedId, user);
        return ResponseEntity.ok().build();
    }
}
