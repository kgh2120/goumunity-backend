package com.ssafy.goumunity.domain.feed.controller;

import com.ssafy.goumunity.domain.feed.controller.request.ReplyRequest;
import com.ssafy.goumunity.domain.feed.service.ReplyService;
import com.ssafy.goumunity.domain.user.domain.User;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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
}
