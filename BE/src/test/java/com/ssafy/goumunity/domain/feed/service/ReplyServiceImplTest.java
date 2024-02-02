package com.ssafy.goumunity.domain.feed.service;

import static com.ssafy.goumunity.domain.feed.exception.CommentErrorCode.COMMENT_NOT_FOUND;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import com.ssafy.goumunity.common.exception.CustomException;
import com.ssafy.goumunity.domain.feed.controller.request.ReplyRequest;
import com.ssafy.goumunity.domain.feed.domain.Reply;
import com.ssafy.goumunity.domain.feed.service.post.ReplyRepository;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ReplyServiceImplTest {

    @Mock ReplyRepository replyRepository;

    @Mock CommentService commentService;

    @InjectMocks ReplyServiceImpl replyService;

    @DisplayName("답글 저장 성공")
    @Test
    void saveReply() {
        Long userId = 1L;
        Long commentId = 1L;

        Reply reply = Reply.builder().id(1L).userId(1L).commentId(1L).content("규준 거준 구준표").build();

        ReplyRequest.Create request = ReplyRequest.Create.builder().content(reply.getContent()).build();

        given(commentService.isExistComment(any())).willReturn(true);
        given(replyRepository.create(any())).willReturn(reply);

        Long savedReply = replyService.createReply(userId, commentId, request);
        assertThat(savedReply).isNotNull();
    }

    @DisplayName("답글 저장 실패 - 댓글 존재하지 않음")
    @Test
    void saveReplyFailWithCommentNotFound() {
        Long userId = 1L;
        Long commentId = -1L;

        Reply reply =
                Reply.builder().id(1L).userId(1L).commentId(commentId).content("규준 거준 구준표").build();

        ReplyRequest.Create request = ReplyRequest.Create.builder().content(reply.getContent()).build();

        assertThat(COMMENT_NOT_FOUND)
                .isEqualTo(
                        assertThrows(
                                        CustomException.class,
                                        () -> replyService.createReply(userId, commentId, request))
                                .getErrorCode());
    }

    @DisplayName("답글 수정 성공")
    @Test
    void modifyReply() {
        Reply reply = Reply.builder().id(1L).userId(1L).commentId(1L).content("규준 거준 구준표").build();
        ReplyRequest.Modify request = ReplyRequest.Modify.builder().content("수정수정").build();

        given(replyRepository.findOneById(any())).willReturn(Optional.of(reply));

        replyService.modifyReply(reply.getUserId(), reply.getCommentId(), reply.getId(), request);
        verify(replyRepository).modify(reply);
    }
}
