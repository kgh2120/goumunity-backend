package com.ssafy.goumunity.domain.feed.service;

public interface ReplyLikeService {

    void createReplyLike(Long userId, Long replyId);

    void deleteReplyLike(Long userId, Long replyId);
}
