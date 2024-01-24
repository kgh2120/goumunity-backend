package com.ssafy.goumunity.domain.chat.controller;

import com.ssafy.goumunity.common.util.SliceResponse;
import com.ssafy.goumunity.domain.chat.controller.request.HashtagCreateRequest;
import com.ssafy.goumunity.domain.chat.controller.response.HashtagResponse;
import com.ssafy.goumunity.domain.chat.domain.Hashtag;
import com.ssafy.goumunity.domain.chat.service.HashtagService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/api/hashtags")
@RestController
public class HashtagController {

    private final HashtagService hashtagService;

    @PostMapping
    public ResponseEntity<HashtagResponse> createHashtag(
            @RequestBody @Valid HashtagCreateRequest dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(HashtagResponse.from(hashtagService.createHashtag(dto)));
    }

    @GetMapping
    public ResponseEntity<SliceResponse<HashtagResponse>> findAllHashtagByKeyword(
            @RequestParam String keyword, @RequestParam Long time, Pageable pageable) {

        SliceResponse<Hashtag> res = hashtagService.findAllByHashtagName(keyword, pageable, time);
        return ResponseEntity.ok(
                SliceResponse.from(
                        res.getContents().stream().map(HashtagResponse::from).toList(), res.getHasNext()));
    }
}
