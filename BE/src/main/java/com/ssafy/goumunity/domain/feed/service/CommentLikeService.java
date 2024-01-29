package com.ssafy.goumunity.domain.feed.service;

public interface CommentLikeService {

    void likeButton(Long commentId, Long userId);

    void unLikeButton(Long commentId, Long userId);
}
