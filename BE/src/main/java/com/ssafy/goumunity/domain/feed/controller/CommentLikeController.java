package com.ssafy.goumunity.domain.feed.controller;

import com.ssafy.goumunity.domain.feed.service.CommentLikeService;
import com.ssafy.goumunity.domain.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/comments/{comment-id}")
public class CommentLikeController {

    private final CommentLikeService commentLikeService;

    @PostMapping("/like")
    public ResponseEntity<Void> likeButton(
            @PathVariable("comment-id") Long commentId, @AuthenticationPrincipal User user) {
        commentLikeService.likeButton(commentId, user.getId());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/unlike")
    public ResponseEntity<Void> unLikeButton(
            @PathVariable("comment-id") Long commentId, @AuthenticationPrincipal User user) {
        commentLikeService.unLikeButton(commentId, user.getId());
        return ResponseEntity.ok().build();
    }
}
