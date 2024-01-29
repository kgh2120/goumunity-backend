package com.ssafy.goumunity.domain.feed.controller;

import com.ssafy.goumunity.domain.feed.controller.request.FeedRegistRequest;
import com.ssafy.goumunity.domain.feed.controller.response.FeedImgResponse;
import com.ssafy.goumunity.domain.feed.controller.response.FeedResponse;
import com.ssafy.goumunity.domain.feed.service.FeedImgService;
import com.ssafy.goumunity.domain.feed.service.FeedService;
import com.ssafy.goumunity.domain.user.domain.User;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping
    public ResponseEntity<Void> save(@RequestBody @Valid FeedRegistRequest feedRegistRequest) {
        feedService.save(feedRegistRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/{feedId}")
    public ResponseEntity<Void> deleteOneByFeedId(
            @PathVariable Long feedId, @AuthenticationPrincipal User user) {
        feedService.deleteOneByFeedId(feedId, user);
        return ResponseEntity.ok().build();
    }
}
