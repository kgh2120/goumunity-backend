package com.ssafy.goumunity.domain.feed.service;

import com.ssafy.goumunity.domain.feed.controller.request.ReplyRequest;

public interface ReplyService {
    void saveReply(Long userId, Long commentId, ReplyRequest.Create reply);
}
