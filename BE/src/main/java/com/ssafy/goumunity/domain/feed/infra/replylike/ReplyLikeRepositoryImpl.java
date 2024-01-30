package com.ssafy.goumunity.domain.feed.infra.replylike;

import com.ssafy.goumunity.domain.feed.domain.ReplyLike;
import com.ssafy.goumunity.domain.feed.service.post.ReplyLikeRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ReplyLikeRepositoryImpl implements ReplyLikeRepository {

    private final ReplyLikeJpaRepository replyLikeJpaRepository;

    @Override
    public void createReplyLike(ReplyLike replyLike) {
        replyLikeJpaRepository.save(ReplyLikeEntity.from(replyLike));
    }

    @Override
    public Optional<ReplyLike> findOneByUserIdAndReplyId(Long userId, Long replyId) {
        return replyLikeJpaRepository
                .findByUserEntity_IdAndReplyEntity_ReplyId(userId, replyId)
                .map(ReplyLikeEntity::to);
    }

    @Override
    public void deleteReplyLike(ReplyLike replyLike) {
        replyLikeJpaRepository.delete(ReplyLikeEntity.from(replyLike));
    }

    @Override
    public boolean existsByReplyLike(ReplyLike replyLike) {
        return replyLikeJpaRepository.existsByUserEntity_IdAndReplyEntity_ReplyId(
                replyLike.getUserId(), replyLike.getReplyId());
    }
}
