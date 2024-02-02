package com.ssafy.goumunity.domain.feed.service;

import com.ssafy.goumunity.domain.feed.controller.request.ReplyRequest;
import com.ssafy.goumunity.domain.feed.controller.response.ReplyResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface ReplyService {
    Long createReply(Long userId, Long commentId, ReplyRequest.Create reply);

    Slice<ReplyResponse> findAllByCommentId(
            Long userId, Long commentId, Long time, Pageable pageable);

    ReplyResponse findOneReply(Long userId, Long replyId);

    void modifyReply(Long userId, Long commentId, Long replyId, ReplyRequest.Modify reply);

    void deleteReply(Long userId, Long commentId, Long replyId);
}
