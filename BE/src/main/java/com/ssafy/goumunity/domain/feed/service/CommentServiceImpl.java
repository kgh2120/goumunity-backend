package com.ssafy.goumunity.domain.feed.service;

import com.ssafy.goumunity.domain.feed.controller.request.CommentRegistRequest;
import com.ssafy.goumunity.domain.feed.controller.response.CommentResponse;
import com.ssafy.goumunity.domain.feed.domain.Comment;
import com.ssafy.goumunity.domain.feed.service.post.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;

    @Override
    @Transactional
    public Comment saveComment(Long userId, Long feedId, CommentRegistRequest comment) {
        return commentRepository.save(Comment.from(userId, feedId, comment));
    }

    @Override
    public Slice<CommentResponse> findAllByFeedId(Long feedId, Pageable pageable) {
        return commentRepository.findAllByFeedId(feedId, pageable);
    }
}
