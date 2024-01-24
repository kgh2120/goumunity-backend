package com.ssafy.goumunity.domain.feed.controller;

import com.ssafy.goumunity.domain.feed.controller.request.CommentRequest;
import com.ssafy.goumunity.domain.feed.controller.response.CommentResponse;
import com.ssafy.goumunity.domain.feed.domain.Comment;
import com.ssafy.goumunity.domain.feed.service.CommentService;
import com.ssafy.goumunity.domain.user.domain.User;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/feeds/{feed-id}/comments")
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<Comment> saveComment(
            @AuthenticationPrincipal User user,
            @PathVariable("feed-id") Long feedId,
            @RequestBody @Valid CommentRequest.Create comment) {
        return ResponseEntity.ok().body(commentService.saveComment(user.getId(), feedId, comment));
    }

    @GetMapping
    public ResponseEntity<Slice<CommentResponse>> findAllComments(
            @PathVariable("feed-id") Long id, Pageable pageable) {
        return ResponseEntity.ok(commentService.findAllByFeedId(id, pageable));
    }

    @PutMapping("/{comment-id}")
    public ResponseEntity<Comment> modifyComment(
            @AuthenticationPrincipal User user,
            @PathVariable("feed-id") Long feedId,
            @PathVariable("comment-id") Long commentId,
            @RequestBody @Valid CommentRequest.Modify comment) {
        return ResponseEntity.ok()
                .body(commentService.modifyComment(user.getId(), feedId, commentId, comment));
    }

    @DeleteMapping("/{comment-id}")
    public ResponseEntity<Void> deleteComment(
            @AuthenticationPrincipal User user,
            @PathVariable("feed-id") Long feedId,
            @PathVariable("comment-id") Long commentId) {
        commentService.deleteComment(user.getId(), feedId, commentId);
        return ResponseEntity.ok().build();
    }
}
