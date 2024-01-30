package com.ssafy.goumunity.domain.feed.service.post;

import com.ssafy.goumunity.domain.feed.domain.ReplyLike;
import java.util.Optional;

public interface ReplyLikeRepository {

    void createReplyLike(ReplyLike replyLike);

    Optional<ReplyLike> findOneByUserIdAndReplyId(Long userId, Long replyId);

    void deleteReplyLike(ReplyLike replyLike);

    boolean existsByReplyLike(ReplyLike replyLike);
}
