package com.ssafy.goumunity.domain.feed.service;

import com.ssafy.goumunity.common.exception.CustomException;
import com.ssafy.goumunity.domain.feed.controller.request.ReplyRequest;
import com.ssafy.goumunity.domain.feed.controller.response.ReplyResponse;
import com.ssafy.goumunity.domain.feed.domain.Reply;
import com.ssafy.goumunity.domain.feed.exception.CommentErrorCode;
import com.ssafy.goumunity.domain.feed.exception.ReplyErrorCode;
import com.ssafy.goumunity.domain.feed.exception.ReplyException;
import com.ssafy.goumunity.domain.feed.service.post.ReplyRepository;
import java.time.Instant;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ReplyServiceImpl implements ReplyService {

    private final ReplyRepository replyRepository;
    private final CommentService commentService;

    @Override
    public void saveReply(Long userId, Long commentId, ReplyRequest.Create reply) {
        if (!commentService.isExistComment(commentId)) {
            throw new CustomException(CommentErrorCode.COMMENT_NOT_FOUND);
        }

        replyRepository.save(Reply.from(userId, commentId, reply));
    }

    @Override
    @Transactional(readOnly = true)
    public Slice<ReplyResponse> findAllByCommentId(Long id, Long time, Pageable pageable) {
        return replyRepository.findAllByCommentId(id, Instant.ofEpochMilli(time), pageable);
    }

    @Override
    public Reply modifyReply(Long userId, Long commentId, Long replyId, ReplyRequest.Modify reply) {
        // reply-id 로 조회 했을 때 조회결과가 없으면 exception 발생
        Reply originalReply =
                replyRepository
                        .findOneById(replyId)
                        .orElseThrow(() -> new ReplyException(ReplyErrorCode.REPLY_NOT_FOUND));

        // 조회해온 reply의 작성자와 세션에 로그인 되어 있는 유저가 다르면 exception 발생
        originalReply.checkUser(userId);

        // 조회해온 reply의 게시글과 param으로 받은 comment-id가 다르면 exception 발생
        originalReply.checkComment(commentId);

        originalReply.modifyContent(reply.getContent());
        return replyRepository.modify(originalReply);
    }
}
