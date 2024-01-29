package com.ssafy.goumunity.domain.feed.infra.commentlike;

import com.ssafy.goumunity.domain.feed.domain.CommentLike;
import com.ssafy.goumunity.domain.feed.service.post.CommentLikeRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CommentLikeRepositoryImpl implements CommentLikeRepository {

    private final CommentLikeJpaRepository commentLikeJpaRepository;

    @Override
    public Optional<CommentLike> findOneByCommentIdAndUserId(Long commentId, Long userId) {
        return commentLikeJpaRepository
                .findOneByCommentIdAndUserId(commentId, userId)
                .map(CommentLikeEntity::to);
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
