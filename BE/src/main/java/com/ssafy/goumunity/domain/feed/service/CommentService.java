package com.ssafy.goumunity.domain.feed.service;

import com.ssafy.goumunity.domain.feed.controller.request.CommentRegistRequest;
import com.ssafy.goumunity.domain.feed.domain.Comment;
import java.util.List;

public interface CommentService {
    Comment saveComment(Long feedId, CommentRegistRequest comment);

    List<Comment> findAllByFeedId(Long feedId);
}
