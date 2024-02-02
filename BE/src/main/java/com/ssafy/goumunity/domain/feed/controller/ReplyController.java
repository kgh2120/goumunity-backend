package com.ssafy.goumunity.domain.feed.controller;

import com.ssafy.goumunity.common.util.SliceResponse;
import com.ssafy.goumunity.domain.feed.controller.request.ReplyRequest;
import com.ssafy.goumunity.domain.feed.controller.response.ReplyResponse;
import com.ssafy.goumunity.domain.feed.service.ReplyService;
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
@RequestMapping("/api/comments/{commentId}/replies")
public class ReplyController {

    private final ReplyService replyService;

    @PostMapping
    public ResponseEntity<Long> createReply(
            @AuthenticationPrincipal User user,
            @PathVariable Long commentId,
            @RequestBody @Valid ReplyRequest.Create reply) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(replyService.createReply(user.getId(), commentId, reply));
    }

    @GetMapping
    public ResponseEntity<SliceResponse<ReplyResponse>> findAllReplies(
            @PathVariable Long commentId, @RequestParam("time") Long time, Pageable pageable) {
        Slice<ReplyResponse> replies = replyService.findAllByCommentId(commentId, time, pageable);
        return ResponseEntity.ok(SliceResponse.from(replies.getContent(), replies.hasNext()));
    }

    @PatchMapping("/{replyId}")
    public ResponseEntity<Void> modifyReply(
            @AuthenticationPrincipal User user,
            @PathVariable Long commentId,
            @PathVariable Long replyId,
            @RequestBody @Valid ReplyRequest.Modify reply) {
        replyService.modifyReply(user.getId(), commentId, replyId, reply);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{replyId}")
    public ResponseEntity<Void> deleteReply(
            @AuthenticationPrincipal User user,
            @PathVariable Long commentId,
            @PathVariable Long replyId) {
        replyService.deleteReply(user.getId(), commentId, replyId);
        return ResponseEntity.ok().build();
    }
}
