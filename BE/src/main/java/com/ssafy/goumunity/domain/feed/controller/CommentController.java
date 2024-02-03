package com.ssafy.goumunity.domain.feed.controller;

import com.ssafy.goumunity.common.util.SliceResponse;
import com.ssafy.goumunity.domain.feed.controller.request.CommentRequest;
import com.ssafy.goumunity.domain.feed.controller.response.CommentResponse;
import com.ssafy.goumunity.domain.feed.service.CommentService;
import com.ssafy.goumunity.domain.user.domain.User;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/feeds/{feedId}/comments")
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<Long> createComment(
            @AuthenticationPrincipal User user,
            @PathVariable Long feedId,
            @RequestBody @Valid CommentRequest.Create comment) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(commentService.createComment(user.getId(), feedId, comment));
    }

    @GetMapping
    public ResponseEntity<SliceResponse<CommentResponse>> findAllComments(
            @AuthenticationPrincipal User user,
            @PathVariable Long feedId,
            @RequestParam("time") Long time,
            Pageable pageable) {
        Slice<CommentResponse> comments =
                commentService.findAllByFeedId(user.getId(), feedId, time, pageable);
        return ResponseEntity.ok(SliceResponse.from(comments.getContent(), comments.hasNext()));
    }

    @GetMapping("/{commentId}")
    public ResponseEntity<CommentResponse> findOneByCommentId(
            @AuthenticationPrincipal User user, @PathVariable Long feedId, @PathVariable Long commentId) {
        return ResponseEntity.ok(commentService.findOneComment(user.getId(), commentId));
    }

    @PatchMapping("/{commentId}")
    public ResponseEntity<Void> modifyComment(
            @AuthenticationPrincipal User user,
            @PathVariable Long feedId,
            @PathVariable Long commentId,
            @RequestBody @Valid CommentRequest.Modify comment) {
        commentService.modifyComment(user.getId(), feedId, commentId, comment);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> deleteComment(
            @AuthenticationPrincipal User user, @PathVariable Long feedId, @PathVariable Long commentId) {
        commentService.deleteComment(user.getId(), feedId, commentId);
        return ResponseEntity.ok().build();
    }
}
