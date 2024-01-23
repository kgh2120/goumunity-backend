package com.ssafy.goumunity.domain.feed.service.post;

import com.ssafy.goumunity.domain.feed.controller.response.CommentResponse;
import com.ssafy.goumunity.domain.feed.domain.Comment;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface CommentRepository {
    Comment save(Comment comment);

    Slice<CommentResponse> findAllByFeedId(Long feedId, Pageable page);
}
