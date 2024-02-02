package com.ssafy.goumunity.domain.feed.service;

import com.ssafy.goumunity.domain.feed.controller.request.CommentRequest;
import com.ssafy.goumunity.domain.feed.controller.response.CommentResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface CommentService {
    Long createComment(Long userId, Long feedId, CommentRequest.Create comment);

    Slice<CommentResponse> findAllByFeedId(Long userId, Long feedId, Long time, Pageable pageable);

    CommentResponse findOneComment(Long userId, Long commentId);

    void modifyComment(Long userId, Long feedId, Long commentId, CommentRequest.Modify comment);

    void deleteComment(Long userId, Long feedId, Long commentId);

    boolean isExistComment(Long commentId);
}
