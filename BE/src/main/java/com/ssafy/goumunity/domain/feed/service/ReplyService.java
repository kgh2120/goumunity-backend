package com.ssafy.goumunity.domain.feed.service;

import com.ssafy.goumunity.domain.feed.controller.request.ReplyRequest;
import com.ssafy.goumunity.domain.feed.domain.Reply;

public interface ReplyService {
    Reply saveReply(Long userId, Long commentId, ReplyRequest.Create reply);
}
