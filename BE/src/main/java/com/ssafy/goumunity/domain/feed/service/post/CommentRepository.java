package com.ssafy.goumunity.domain.feed.service.post;

import com.ssafy.goumunity.domain.feed.controller.response.CommentResponse;
import com.ssafy.goumunity.domain.feed.domain.Comment;
import java.util.Optional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface CommentRepository {
    Comment save(Comment comment);

    Slice<CommentResponse> findAllByFeedId(Long feedId, Pageable page);

    Optional<Comment> findOneById(Long commentId);

    Comment modify(Comment comment);

    void delete(Comment comment);
}
