package com.ssafy.goumunity.domain.feed.service.post;

import com.ssafy.goumunity.domain.feed.domain.Comment;
import java.util.List;

public interface CommentRepository {
    Comment save(Comment comment);

    List<Comment> findAllByFeedId(Long feedId);
}
