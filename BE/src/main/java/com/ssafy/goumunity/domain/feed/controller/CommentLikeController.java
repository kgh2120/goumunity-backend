package com.ssafy.goumunity.domain.feed.controller;

import com.ssafy.goumunity.domain.feed.controller.request.CommentLikeCountRequest;
import com.ssafy.goumunity.domain.feed.controller.request.CommentLikeRequest;
import com.ssafy.goumunity.domain.feed.controller.response.CommentLikeCountResponse;
import com.ssafy.goumunity.domain.feed.service.CommentLikeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping( "/api/comments/{comment-id}/commentlikes")
public class CommentLikeController {

    private final CommentLikeService commentLikeService;

    @PostMapping
    public ResponseEntity<Void> pushLikeButton(@RequestBody @Valid CommentLikeRequest commentLikeRequest){

        // TODO : User기능통합시 userid보내는부분 수정 필요

        if(commentLikeService.pushLikeButton(commentLikeRequest, Long.valueOf(1))) {
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } else return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<CommentLikeCountResponse> getLikeCount(CommentLikeCountRequest commentLikeCountRequest){
        return ResponseEntity.ok(commentLikeService.countCommentLikeByCommentId(commentLikeCountRequest));
    }

}
