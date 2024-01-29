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
@Transactional
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;

    @Override
    public Comment saveComment(Long userId, Long feedId, CommentRequest.Create comment) {
        return commentRepository.save(Comment.from(userId, feedId, comment));
    }

    @Override
    @Transactional(readOnly = true)
    public Slice<CommentResponse> findAllByFeedId(Long feedId, Long time, Pageable pageable) {
        return commentRepository.findAllByFeedId(feedId, Instant.ofEpochMilli(time), pageable);
    }

    @Override
    public Comment modifyComment(
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
        return commentRepository.modify(originalComment);
    }

    @Override
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

        // TODO: 댓글을 참조하는 댓글좋아요(CommentLike) 모두 삭제하기
        // TODO: 댓글을 참조하는 답글(Reply) 모두 삭제하기

        commentRepository.delete(originalComment);
    }

    @Override
    public boolean isExistComment(Long commentId) {
        return commentRepository.existsById(commentId);
    }
}
