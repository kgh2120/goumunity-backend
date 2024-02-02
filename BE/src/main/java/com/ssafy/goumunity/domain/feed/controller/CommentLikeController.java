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
@RequestMapping("/api/comments/{commentId}")
public class CommentLikeController {

    private final CommentLikeService commentLikeService;

    @PostMapping("/like")
    public ResponseEntity<Void> likeButton(
            @AuthenticationPrincipal User user, @PathVariable Long commentId) {
        commentLikeService.createCommentLike(user.getId(), commentId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/unlike")
    public ResponseEntity<Void> unLikeButton(
            @AuthenticationPrincipal User user, @PathVariable Long commentId) {
        commentLikeService.deleteCommentLike(user.getId(), commentId);
        return ResponseEntity.ok().build();
    }
}
