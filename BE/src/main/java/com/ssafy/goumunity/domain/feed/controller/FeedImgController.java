package com.ssafy.goumunity.domain.feed.controller;

import com.ssafy.goumunity.domain.feed.controller.response.FeedImgResponse;
import com.ssafy.goumunity.domain.feed.service.FeedImgService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/feedimgs")
public class FeedImgController {

    private final FeedImgService feedImgService;

    @GetMapping("/{feedImgId}")
    public ResponseEntity<FeedImgResponse> findOneByFeedImgId(@PathVariable Long feedImgId) {
        return ResponseEntity.ok(feedImgService.findOneByFeedImgId(feedImgId));
    }
}
