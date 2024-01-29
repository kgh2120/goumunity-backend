package com.ssafy.goumunity.domain.feed.service.post;

import com.ssafy.goumunity.domain.feed.domain.CommentLike;
import com.ssafy.goumunity.domain.feed.infra.commentlike.CommentLikeEntity;
import java.util.Optional;

public interface CommentLikeRepository {

    Optional<CommentLike> findOneByCommentIdAndUserId(Long commentId, Long userId);

    void save(CommentLikeEntity commentLikeEntity);

    void delete(CommentLikeEntity commentLikeEntity);
}
