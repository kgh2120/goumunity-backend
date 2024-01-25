package com.ssafy.goumunity.domain.feed.service;

import com.ssafy.goumunity.common.exception.CustomErrorCode;
import com.ssafy.goumunity.common.exception.CustomException;
import com.ssafy.goumunity.domain.feed.controller.request.ReplyRequest;
import com.ssafy.goumunity.domain.feed.domain.Reply;
import com.ssafy.goumunity.domain.feed.service.post.ReplyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ReplyServiceImpl implements ReplyService {

    private final ReplyRepository replyRepository;
    private final CommentService commentService;

    @Override
    public Reply saveReply(Long userId, Long commentId, ReplyRequest.Create reply) {
        if (!commentService.isExistComment(commentId)) {
            throw new CustomException(CustomErrorCode.COMMENT_NOT_FOUND);
        }

        return replyRepository.save(Reply.from(userId, commentId, reply));
    }
}
