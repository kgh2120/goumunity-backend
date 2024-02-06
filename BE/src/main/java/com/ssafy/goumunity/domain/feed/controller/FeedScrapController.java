package com.ssafy.goumunity.domain.feed.controller;

import com.ssafy.goumunity.domain.feed.service.FeedScrapService;
import com.ssafy.goumunity.domain.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/feeds/{feedId}")
public class FeedScrapController {

    private final FeedScrapService feedScrapService;

    @PostMapping("/scrap")
    public ResponseEntity<Void> createFeedScrap(
            @AuthenticationPrincipal User user, @PathVariable Long feedId) {
        feedScrapService.createFeedScrap(user.getId(), feedId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/unscrap")
    public ResponseEntity<Void> deleteFeedScarp(
            @AuthenticationPrincipal User user, @PathVariable Long feedId) {
        feedScrapService.deleteFeedScrap(user.getId(), feedId);
        return ResponseEntity.ok().build();
    }
}
