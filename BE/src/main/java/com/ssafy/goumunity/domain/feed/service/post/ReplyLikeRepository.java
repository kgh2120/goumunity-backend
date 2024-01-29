package com.ssafy.goumunity.domain.feed.service.post;

import com.ssafy.goumunity.domain.feed.domain.ReplyLike;

public interface ReplyLikeRepository {

    void createReplyLike(ReplyLike replyLike);

    void deleteReplyLike(ReplyLike replyLike);

    boolean existsByReplyLike(ReplyLike replyLike);
}
