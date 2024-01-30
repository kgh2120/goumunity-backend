package com.ssafy.goumunity.domain.feed.controller;

import com.ssafy.goumunity.common.util.SliceResponse;
import com.ssafy.goumunity.domain.feed.controller.request.ReplyRequest;
import com.ssafy.goumunity.domain.feed.controller.response.ReplyResponse;
import com.ssafy.goumunity.domain.feed.service.ReplyService;
import com.ssafy.goumunity.domain.user.domain.User;
import com.ssafy.goumunity.domain.user.dto.UserResponse;
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
@RequestMapping("/api/comments/{comment-id}/replies")
public class ReplyController {

    private final ReplyService replyService;

    @PostMapping
    public ResponseEntity<Void> saveReply(
            @AuthenticationPrincipal User user,
            @PathVariable("comment-id") Long commentId,
            @RequestBody @Valid ReplyRequest.Create reply) {
        replyService.saveReply(user.getId(), commentId, reply);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    public ResponseEntity<SliceResponse<ReplyResponse>> findAllReplies(
            @PathVariable("comment-id") Long commentId,
            @RequestParam("time") Long time,
            Pageable pageable) {
        Slice<ReplyResponse> replies = replyService.findAllByCommentId(commentId, time, pageable);
        return ResponseEntity.ok(SliceResponse.from(replies.getContent(), replies.hasNext()));
    }

    @PutMapping("/{reply-id}")
    public ResponseEntity<ReplyResponse> modifyReply(
            @AuthenticationPrincipal User user,
            @PathVariable("comment-id") Long commentId,
            @PathVariable("reply-id") Long replyId,
            @RequestBody @Valid ReplyRequest.Modify reply) {
        return ResponseEntity.ok()
                .body(
                        ReplyResponse.from(
                                replyService.modifyReply(user.getId(), commentId, replyId, reply),
                                UserResponse.from(user)));
    }

    @DeleteMapping("/{reply-id}")
    public ResponseEntity<Void> deleteReply(
            @AuthenticationPrincipal User user,
            @PathVariable("comment-id") Long commentId,
            @PathVariable("reply-id") Long replyId) {
        replyService.deleteReply(user.getId(), commentId, replyId);
        return ResponseEntity.ok().build();
    }
}
