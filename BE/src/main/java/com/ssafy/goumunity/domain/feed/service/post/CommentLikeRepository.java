package com.ssafy.goumunity.domain.feed.service.post;

import com.ssafy.goumunity.domain.feed.domain.CommentLike;

public interface CommentLikeRepository {

    boolean existByCommentLike(CommentLike commentLike);

    void save(CommentLike commentLike);

    void delete(CommentLike commentLike);
}
