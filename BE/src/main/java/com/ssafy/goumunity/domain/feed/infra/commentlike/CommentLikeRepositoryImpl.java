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
    public void create(CommentLike commentLike) {
        commentLikeJpaRepository.save(CommentLikeEntity.from(commentLike));
    }

    @Override
    public Optional<CommentLike> findOneByUserIdAndCommentId(Long userId, Long commentId) {
        return commentLikeJpaRepository
                .findByUserEntity_IdAndCommentEntity_Id(userId, commentId)
                .map(CommentLikeEntity::to);
    }

    @Override
    public void delete(Long commentLikeId) {
        commentLikeJpaRepository.deleteById(commentLikeId);
    }

    @Override
    public boolean existByCommentLike(CommentLike commentLike) {
        return commentLikeJpaRepository.existsByUserEntity_IdAndCommentEntity_Id(
                commentLike.getUserId(), commentLike.getCommentId());
    }
}
