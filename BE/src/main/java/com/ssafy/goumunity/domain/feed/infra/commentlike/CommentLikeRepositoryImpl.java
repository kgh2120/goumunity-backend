package com.ssafy.goumunity.domain.feed.infra.commentlike;

import com.ssafy.goumunity.domain.feed.service.post.CommentLikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CommentLikeRepositoryImpl implements CommentLikeRepository {

    private final CommentLikeJpaRepository commentLikeJpaRepository;

    @Override
    public boolean existByCommentIdandUserId(Long commentId, Long userId) {
        return commentLikeJpaRepository.existsByCommentIdAndUserId(commentId, userId);
    }

    @Override
    public void save(CommentLikeEntity commentLikeEntity) {
        commentLikeJpaRepository.save(commentLikeEntity);
    }

    @Override
    public void delete(CommentLikeEntity commentLikeEntity) {
        commentLikeJpaRepository.delete(commentLikeEntity);
    }
}
