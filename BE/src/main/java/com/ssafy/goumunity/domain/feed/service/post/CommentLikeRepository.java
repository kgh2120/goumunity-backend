package com.ssafy.goumunity.domain.feed.service.post;

import com.ssafy.goumunity.domain.feed.domain.CommentLike;
import java.util.Optional;

public interface CommentLikeRepository {

    void create(CommentLike commentLike);

    Optional<CommentLike> findOneByUserIdAndCommentId(Long userId, Long commentId);

    void delete(Long commentLikeId);

    boolean existByCommentLike(CommentLike commentLike);
}
