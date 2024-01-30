package com.ssafy.goumunity.domain.feed.infra.commentlike;

import com.ssafy.goumunity.domain.feed.domain.CommentLike;
import com.ssafy.goumunity.domain.feed.service.post.CommentLikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CommentLikeRepositoryImpl implements CommentLikeRepository {

    private final CommentLikeJpaRepository commentLikeJpaRepository;

    @Override
    public boolean existByCommentLike(CommentLike commentLike) {
        return commentLikeJpaRepository.existsByUserEntity_IdAndCommentEntity_CommentId(
                commentLike.getUserId(), commentLike.getCommentId());
    }

    @Override
    public void save(CommentLike commentLike) {
        commentLikeJpaRepository.save(CommentLikeEntity.from(commentLike));
    }

    @Override
    public void delete(CommentLike commentLike) {
        commentLikeJpaRepository.delete(CommentLikeEntity.from(commentLike));
    }
}
