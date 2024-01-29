package com.ssafy.goumunity.domain.feed.service;

import com.ssafy.goumunity.domain.feed.controller.request.CommentLikeCountRequest;
import com.ssafy.goumunity.domain.feed.controller.response.CommentLikeCountResponse;

public interface CommentLikeService {

    void likeButton(Long commentId, Long userId);

    void unLikeButton(Long commentId, Long userId);

    CommentLikeCountResponse countCommentLikeByCommentId(
            CommentLikeCountRequest commentLikeCountRequest);
}
