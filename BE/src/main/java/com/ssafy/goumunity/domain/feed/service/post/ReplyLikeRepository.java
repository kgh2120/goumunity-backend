package com.ssafy.goumunity.domain.feed.service.post;

import com.ssafy.goumunity.domain.feed.domain.ReplyLike;
import java.util.List;
import java.util.Optional;

public interface ReplyLikeRepository {

    void create(ReplyLike replyLike);

    Optional<ReplyLike> findOneByUserIdAndReplyId(Long userId, Long replyId);

    void delete(Long replyLikeId);

    boolean existsByReplyLike(ReplyLike replyLike);

    void deleteAllByReplyIds(List<Long> replyIds);
}
