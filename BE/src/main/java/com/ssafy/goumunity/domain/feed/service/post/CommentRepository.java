package com.ssafy.goumunity.domain.feed.service.post;

import com.ssafy.goumunity.domain.feed.controller.response.CommentResponse;
import com.ssafy.goumunity.domain.feed.domain.Comment;
import java.time.Instant;
import java.util.Optional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface CommentRepository {
    Comment create(Comment comment);

    Slice<CommentResponse> findAllByFeedId(Long userId, Long feedId, Instant time, Pageable page);

    Optional<Comment> findOneById(Long commentId);

    CommentResponse findOneComment(Long userId, Long commentId);

    void modify(Comment comment);

    void delete(Long commentId);

    boolean existsById(Long commentId);
}
