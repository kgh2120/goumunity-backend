package com.ssafy.goumunity.domain.feed.service;

import com.ssafy.goumunity.domain.feed.controller.request.CommentLikeCountRequest;
import com.ssafy.goumunity.domain.feed.controller.request.CommentLikeRequest;
import com.ssafy.goumunity.domain.feed.controller.response.CommentLikeCountResponse;
import com.ssafy.goumunity.domain.feed.domain.CommentLike;
import com.ssafy.goumunity.domain.feed.infra.commentlike.CommentLikeEntity;
import com.ssafy.goumunity.domain.feed.service.post.CommentLikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentLikeServiceImpl implements CommentLikeService{

    private final CommentLikeRepository commentLikeRepository;

    @Override
    public boolean pushLikeButton(CommentLikeRequest commentLikeRequest, Long nowUserId) {

        // TODO : user통합시 현재유저검증로직 추가

        Optional<CommentLike> commentLike = commentLikeRepository.findOneByCommentIdAndUserId(commentLikeRequest.getCommentId(), commentLikeRequest.getUserId());

        if(commentLike.isEmpty()){
            commentLikeRepository.save(CommentLikeEntity.from(CommentLike.from(commentLikeRequest)));
            return true;
        } else {
            commentLikeRepository.delete(CommentLikeEntity.from(commentLike.get()));
            return false;
        }

    }

    @Override
    public CommentLikeCountResponse countCommentLikeByCommentId(CommentLikeCountRequest commentLikeCountRequest) {
        return CommentLikeCountResponse.from(commentLikeRepository.countCommentLikeByCommentId(commentLikeCountRequest.getCommentId()));
    }
}
