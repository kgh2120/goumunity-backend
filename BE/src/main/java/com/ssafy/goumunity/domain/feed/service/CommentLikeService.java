package com.ssafy.goumunity.domain.feed.service;

import com.ssafy.goumunity.domain.feed.controller.request.CommentLikeCountRequest;
import com.ssafy.goumunity.domain.feed.controller.request.CommentLikeRequest;
import com.ssafy.goumunity.domain.feed.controller.response.CommentLikeCountResponse;

public interface CommentLikeService {

    boolean pushLikeButton(CommentLikeRequest commentLikeRequest, Long nowUserId);

    CommentLikeCountResponse countCommentLikeByCommentId(CommentLikeCountRequest commentLikeCountRequest);

}
