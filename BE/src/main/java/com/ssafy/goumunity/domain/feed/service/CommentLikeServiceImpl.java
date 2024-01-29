package com.ssafy.goumunity.domain.feed.service;

import com.ssafy.goumunity.domain.feed.controller.response.CommentLikeCountResponse;
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
        commentLikeRepository.save(
                CommentLikeEntity.from(CommentLike.builder().commentId(commentId).userId(userId).build()));
    }

    @Override
    public void unLikeButton(Long commentId, Long userId) {
        commentLikeRepository.delete(
                CommentLikeEntity.from(CommentLike.builder().commentId(commentId).userId(userId).build()));
    }

    @Override
    public CommentLikeCountResponse countCommentLikeByCommentId(Long commentId) {
        return CommentLikeCountResponse.from(
                commentLikeRepository.countCommentLikeByCommentId(commentId));
    }
}
