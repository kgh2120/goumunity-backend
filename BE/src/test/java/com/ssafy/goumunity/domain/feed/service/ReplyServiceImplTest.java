package com.ssafy.goumunity.domain.feed.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import com.ssafy.goumunity.domain.feed.controller.request.ReplyRequest;
import com.ssafy.goumunity.domain.feed.domain.Reply;
import com.ssafy.goumunity.domain.feed.service.post.ReplyRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ReplyServiceImplTest {

    @Mock ReplyRepository replyRepository;

    @InjectMocks ReplyServiceImpl replyService;

    @DisplayName("답글 저장 성공")
    @Test
    void saveReply() {
        Long userId = 1L;
        Long commentId = 1L;

        Reply reply = Reply.builder().replyId(1L).userId(1L).commentId(1L).content("규준 거준 구준표").build();

        ReplyRequest.Create request = ReplyRequest.Create.builder().content(reply.getContent()).build();

        given(replyRepository.save(any())).willReturn(reply);

        Reply result = replyService.saveReply(userId, commentId, request);

        assertThat(reply.getContent()).isEqualTo(result.getContent());
        assertThat(reply.getUserId()).isEqualTo(result.getUserId());
        assertThat(reply.getCommentId()).isEqualTo(result.getCommentId());
    }
}
