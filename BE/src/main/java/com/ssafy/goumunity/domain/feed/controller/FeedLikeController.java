package com.ssafy.goumunity.domain.feed.controller;

import com.ssafy.goumunity.domain.feed.service.FeedLikeService;
import com.ssafy.goumunity.domain.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/feeds/{feed-id}")
public class FeedLikeController {

    private final FeedLikeService feedLikeService;

    @PostMapping("/like")
    public ResponseEntity<Void> createdFeedLike(
            @AuthenticationPrincipal User user, @PathVariable("feed-id") Long feedId) {
        feedLikeService.createFeedLike(user.getId(), feedId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/unlike")
    public ResponseEntity<Void> deleteFeedLike(
            @AuthenticationPrincipal User user, @PathVariable("feed-id") Long feedId) {
        feedLikeService.deleteFeedLike(user.getId(), feedId);
        return ResponseEntity.ok().build();
    }
}
