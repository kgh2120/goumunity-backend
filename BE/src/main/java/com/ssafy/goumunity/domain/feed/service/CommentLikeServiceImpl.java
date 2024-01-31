package com.ssafy.goumunity.domain.feed.service;

import com.ssafy.goumunity.domain.feed.domain.CommentLike;
import com.ssafy.goumunity.domain.feed.exception.CommentErrorCode;
import com.ssafy.goumunity.domain.feed.exception.CommentException;
import com.ssafy.goumunity.domain.feed.service.post.CommentLikeRepository;
import com.ssafy.goumunity.domain.feed.service.post.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class CommentLikeServiceImpl implements CommentLikeService {

    private final CommentLikeRepository commentLikeRepository;
    private final CommentRepository commentRepository;

    @Override
    public void createCommentLike(Long userId, Long commentId) {
        verifyComment(commentId);
        CommentLike commentLike = CommentLike.from(userId, commentId);

        if (commentLikeRepository.existByCommentLike(commentLike)) {
            throw new CommentException(CommentErrorCode.ALREADY_LIKED);
        }

        commentLikeRepository.save(commentLike);
    }

    @Override
    public void deleteCommentLike(Long userId, Long commentId) {
        verifyComment(commentId);
    }

    private void verifyComment(Long commentId) {
        if (!commentRepository.existsById(commentId)) {
            throw new CommentException(CommentErrorCode.COMMENT_NOT_FOUND);
        }
    }
}
