package com.ssafy.goumunity.domain.feed.service;

import com.ssafy.goumunity.domain.feed.controller.request.ReplyRequest;
import com.ssafy.goumunity.domain.feed.controller.response.ReplyResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface ReplyService {
    void saveReply(Long userId, Long commentId, ReplyRequest.Create reply);

    Slice<ReplyResponse> findAllByCommentId(Long id, Long time, Pageable pageable);
}
