package com.ssafy.goumunity.domain.feed.service;

import com.ssafy.goumunity.domain.feed.controller.request.CommentRegistRequest;
import com.ssafy.goumunity.domain.feed.controller.response.CommentResponse;
import com.ssafy.goumunity.domain.feed.domain.Comment;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface CommentService {
    Comment saveComment(Long userId, Long feedId, CommentRegistRequest comment);

    Slice<CommentResponse> findAllByFeedId(Long feedId, Pageable pageable);
}
