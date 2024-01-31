package com.ssafy.goumunity.domain.feed.service;

import static com.ssafy.goumunity.domain.feed.exception.ReplyErrorCode.NO_LIKE_DATA;

import com.ssafy.goumunity.domain.feed.domain.ReplyLike;
import com.ssafy.goumunity.domain.feed.exception.ReplyErrorCode;
import com.ssafy.goumunity.domain.feed.exception.ReplyException;
import com.ssafy.goumunity.domain.feed.service.post.ReplyLikeRepository;
import com.ssafy.goumunity.domain.feed.service.post.ReplyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ReplyLikeServiceImpl implements ReplyLikeService {

    private final ReplyLikeRepository replyLikeRepository;
    private final ReplyRepository replyRepository;

    @Override
    public void createReplyLike(Long userId, Long replyId) {
        verifyReply(replyId);
        ReplyLike replyLike = ReplyLike.from(userId, replyId);

        if (replyLikeRepository.existsByReplyLike(replyLike)) {
            throw new ReplyException(ReplyErrorCode.ALREADY_LIKED);
        }

        replyLikeRepository.createReplyLike(replyLike);
    }

    @Override
    public void deleteReplyLike(Long userId, Long replyId) {
        verifyReply(replyId);

        ReplyLike replyLike =
                replyLikeRepository
                        .findOneByUserIdAndReplyId(userId, replyId)
                        .orElseThrow(() -> new ReplyException(NO_LIKE_DATA));

        replyLikeRepository.deleteReplyLike(replyLike);
    }

    private void verifyReply(Long replyId) {
        if (!replyRepository.existsByReplyId(replyId)) {
            throw new ReplyException(ReplyErrorCode.REPLY_NOT_FOUND);
        }
    }
}
