package com.ssafy.goumunity.domain.feed.service.post;

import com.ssafy.goumunity.domain.feed.infra.commentlike.CommentLikeEntity;

public interface CommentLikeRepository {

    boolean existByCommentIdandUserId(Long commentId, Long userId);

    void save(CommentLikeEntity commentLikeEntity);

    void delete(CommentLikeEntity commentLikeEntity);
}
