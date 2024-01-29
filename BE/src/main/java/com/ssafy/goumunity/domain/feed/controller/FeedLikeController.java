package com.ssafy.goumunity.domain.feed.controller;

import com.ssafy.goumunity.domain.feed.controller.request.FeedLikeCountRequest;
import com.ssafy.goumunity.domain.feed.controller.request.FeedLikeRequest;
import com.ssafy.goumunity.domain.feed.controller.response.FeedLikeCountResponse;
import com.ssafy.goumunity.domain.feed.service.FeedLikeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/feeds/{feed-id}/feedlikes")
public class FeedLikeController {

    private final FeedLikeService feedLikeService;

    @PostMapping
    public ResponseEntity<Void> pushLikeButton(@RequestBody @Valid FeedLikeRequest feedLikeRequest) {
        // TODO : user통합시 받아오는과정 추가 필요
        if (feedLikeService.pushLikeButton(feedLikeRequest, Long.valueOf(1))) {
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } else {
            return ResponseEntity.ok().build();
        }
    }

    @GetMapping
    public ResponseEntity<FeedLikeCountResponse> getLikeCount(
            FeedLikeCountRequest feedLikeCountRequest) {
        return ResponseEntity.ok(feedLikeService.countFeedLikeByFeedId(feedLikeCountRequest));
    }
}
