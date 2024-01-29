package com.ssafy.goumunity.domain.feed.service;

import com.ssafy.goumunity.domain.feed.domain.CommentLike;
import com.ssafy.goumunity.domain.feed.infra.commentlike.CommentLikeEntity;
import com.ssafy.goumunity.domain.feed.service.post.CommentLikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentLikeServiceImpl implements CommentLikeService {

    private final CommentLikeRepository commentLikeRepository;

    @Override
    public void likeButton(Long commentId, Long userId) {
        commentLikeRepository
                .findOneByCommentIdAndUserId(commentId, userId)
                .orElseThrow(
                        () -> {
                            commentLikeRepository.save(
                                    CommentLikeEntity.from(
                                            CommentLike.builder().commentId(commentId).userId(userId).build()));
                            return null;
                        });
    }

    @Override
    public void unLikeButton(Long commentId, Long userId) {
        commentLikeRepository
                .findOneByCommentIdAndUserId(commentId, userId)
                .ifPresent(
                        commentLike -> commentLikeRepository.delete(CommentLikeEntity.from(commentLike)));
    }
}
