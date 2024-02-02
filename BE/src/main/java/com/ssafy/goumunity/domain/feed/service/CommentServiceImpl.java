package com.ssafy.goumunity.domain.feed.service;

import com.ssafy.goumunity.domain.feed.controller.request.CommentRequest;
import com.ssafy.goumunity.domain.feed.controller.response.CommentResponse;
import com.ssafy.goumunity.domain.feed.domain.Comment;
import com.ssafy.goumunity.domain.feed.exception.CommentErrorCode;
import com.ssafy.goumunity.domain.feed.exception.CommentException;
import com.ssafy.goumunity.domain.feed.service.post.CommentRepository;
import java.time.Instant;
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
    public Long createComment(Long userId, Long feedId, CommentRequest.Create comment) {
        Comment savedComment = commentRepository.create(Comment.create(userId, feedId, comment));
        return savedComment.getId();
    }

    @Override
    public Slice<CommentResponse> findAllByFeedId(
            Long userId, Long feedId, Long time, Pageable pageable) {
        return commentRepository.findAllByFeedId(userId, feedId, Instant.ofEpochMilli(time), pageable);
    }

    @Override
    public CommentResponse findOneComment(Long userId, Long commentId) {
        return commentRepository.findOneComment(userId, commentId);
    }

    @Override
    @Transactional
    public void modifyComment(
            Long userId, Long feedId, Long commentId, CommentRequest.Modify comment) {
        // commend-id 로 조회 했을 때 조회결과가 없으면 exception 발생
        Comment originalComment =
                commentRepository
                        .findOneById(commentId)
                        .orElseThrow(() -> new CommentException(CommentErrorCode.COMMENT_NOT_FOUND));

        // 조회해온 comment의 작성자와 세션에 로그인 되어 있는 유저가 다르면 exception 발생
        originalComment.checkUser(userId);

        // 조회해온 comment의 게시글과 param으로 받은 feed-id가 다르면 exception 발생
        originalComment.checkFeed(feedId);

        originalComment.modifyContent(comment.getContent());
        commentRepository.modify(originalComment);
    }

    @Override
    @Transactional
    public void deleteComment(Long userId, Long feedId, Long commentId) {
        // commend-id 로 조회 했을 때 조회결과가 없으면 exception 발생
        Comment originalComment =
                commentRepository
                        .findOneById(commentId)
                        .orElseThrow(() -> new CommentException(CommentErrorCode.COMMENT_NOT_FOUND));

        // 조회해온 comment의 작성자와 세션에 로그인 되어 있는 유저가 다르면 exception 발생
        originalComment.checkUser(userId);

        // 조회해온 comment의 게시글과 param으로 받은 feed-id가 다르면 exception 발생
        originalComment.checkFeed(feedId);

        commentRepository.delete(originalComment.getId());
    }

    @Override
    public boolean isExistComment(Long commentId) {
        return commentRepository.existsById(commentId);
    }
}
