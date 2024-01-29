package com.ssafy.goumunity.domain.feed.controller;

import com.ssafy.goumunity.domain.feed.service.ReplyLikeService;
import com.ssafy.goumunity.domain.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/replies/{reply-id}")
public class ReplyLikeController {

    private final ReplyLikeService replyLikeService;

    @PostMapping("/like")
    public ResponseEntity<Void> createdReplyLike(
            @AuthenticationPrincipal User user, @PathVariable("reply-id") Long replyId) {
        replyLikeService.createReplyLike(user.getId(), replyId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/unlike")
    public ResponseEntity<Void> deleteReplyLike(
            @AuthenticationPrincipal User user, @PathVariable("reply-id") Long replyId) {
        replyLikeService.deleteReplyLike(user.getId(), replyId);
        return ResponseEntity.ok().build();
    }
}
