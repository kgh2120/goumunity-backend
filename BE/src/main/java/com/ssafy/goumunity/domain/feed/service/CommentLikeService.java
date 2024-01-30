package com.ssafy.goumunity.domain.feed.service;

public interface CommentLikeService {

    void createCommentLike(Long userId, Long commentId);

    void deleteCommentLike(Long userId, Long commentId);
}
