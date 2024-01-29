package com.ssafy.goumunity.domain.feed.service.post;

import com.ssafy.goumunity.domain.feed.controller.response.ReplyResponse;
import com.ssafy.goumunity.domain.feed.domain.Reply;
import java.time.Instant;
import java.util.Optional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface ReplyRepository {
    void save(Reply reply);

    Slice<ReplyResponse> findAllByCommentId(Long commentId, Instant instant, Pageable pageable);

    Optional<Reply> findOneById(Long replyId);

    Reply modify(Reply reply);
}
