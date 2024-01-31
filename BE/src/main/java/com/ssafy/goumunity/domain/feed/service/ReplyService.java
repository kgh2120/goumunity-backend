package com.ssafy.goumunity.domain.feed.service;

import com.ssafy.goumunity.domain.feed.controller.request.ReplyRequest;
import com.ssafy.goumunity.domain.feed.controller.response.ReplyResponse;
import com.ssafy.goumunity.domain.feed.domain.Reply;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface ReplyService {
    void saveReply(Long userId, Long commentId, ReplyRequest.Create reply);

    Slice<ReplyResponse> findAllByCommentId(Long id, Long time, Pageable pageable);

    Reply modifyReply(Long userId, Long commentId, Long replyId, ReplyRequest.Modify reply);

    void deleteReply(Long userId, Long commentId, Long replyId);
}
