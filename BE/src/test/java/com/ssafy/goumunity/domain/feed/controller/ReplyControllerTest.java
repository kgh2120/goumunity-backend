package com.ssafy.goumunity.domain.feed.controller;

import static com.ssafy.goumunity.common.exception.GlobalErrorCode.BIND_ERROR;
import static com.ssafy.goumunity.common.exception.GlobalErrorCode.REQUIRED_PARAM_NOT_FOUND;
import static com.ssafy.goumunity.domain.feed.exception.CommentErrorCode.COMMENT_NOT_FOUND;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.goumunity.common.config.SecurityConfig;
import com.ssafy.goumunity.common.config.security.CustomDetails;
import com.ssafy.goumunity.common.exception.GlobalExceptionHandler;
import com.ssafy.goumunity.domain.feed.controller.request.ReplyRequest;
import com.ssafy.goumunity.domain.feed.controller.response.ReplyResponse;
import com.ssafy.goumunity.domain.feed.domain.Reply;
import com.ssafy.goumunity.domain.feed.exception.CommentException;
import com.ssafy.goumunity.domain.feed.service.ReplyService;
import com.ssafy.goumunity.domain.user.controller.response.UserResponse;
import com.ssafy.goumunity.domain.user.domain.User;
import com.ssafy.goumunity.domain.user.domain.UserCategory;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityFilterAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(
        controllers = {ReplyController.class, GlobalExceptionHandler.class},
        excludeFilters = {
            @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = SecurityConfig.class)
        },
        excludeAutoConfiguration = {
            SecurityAutoConfiguration.class,
            SecurityFilterAutoConfiguration.class
        })
class ReplyControllerTest {

    @MockBean ReplyService replyService;

    @Autowired MockMvc mockMvc;

    @DisplayName("답글 저장 성공")
    @Test
    void saveReply() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        MockHttpSession session = new MockHttpSession();
        User user =
                User.builder()
                        .id(1L)
                        .email("gyu@naver.com")
                        .password("AAbb11!!")
                        .monthBudget(100000L)
                        .age(20)
                        .userCategory(UserCategory.JOB_SEEKER)
                        .gender(1)
                        .nickname("규준")
                        .regionId(1)
                        .build();

        Long commentId = 1L;

        Reply reply =
                Reply.builder().id(1L).userId(1L).commentId(commentId).content("규준 거준 구준표").build();

        mockMvc
                .perform(
                        post("/api/comments/" + commentId + "/replies")
                                .with(SecurityMockMvcRequestPostProcessors.user(new CustomDetails(user)))
                                .content(mapper.writeValueAsString(reply))
                                .contentType(MediaType.APPLICATION_JSON)
                                .session(session))
                .andExpect(status().isCreated())
                .andDo(print());
    }

    @DisplayName("답글 저장 실패_비어있는 요청")
    @Test
    void saveReplyFailWithEmptyContent() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        MockHttpSession session = new MockHttpSession();
        User user =
                User.builder()
                        .id(1L)
                        .email("gyu@naver.com")
                        .password("AAbb11!!")
                        .monthBudget(100000L)
                        .age(20)
                        .userCategory(UserCategory.JOB_SEEKER)
                        .gender(1)
                        .nickname("규준")
                        .regionId(1)
                        .build();

        Long commentId = 1L;

        Reply reply = Reply.builder().id(1L).userId(1L).commentId(commentId).build();

        mockMvc
                .perform(
                        post("/api/comments/" + commentId + "/replies")
                                .with(SecurityMockMvcRequestPostProcessors.user(new CustomDetails(user)))
                                .content(mapper.writeValueAsString(reply))
                                .contentType(MediaType.APPLICATION_JSON)
                                .session(session))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errorName").value(BIND_ERROR.getErrorName()))
                .andExpect(jsonPath("$.errorMessage").value(BIND_ERROR.getErrorMessage()))
                .andDo(print());
    }

    @DisplayName("답글 저장 실패_댓글 내용이 200자 초과했을때")
    @Test
    void saveReplyFailWithLongContent() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        MockHttpSession session = new MockHttpSession();
        User user =
                User.builder()
                        .id(1L)
                        .email("gyu@naver.com")
                        .password("AAbb11!!")
                        .monthBudget(100000L)
                        .age(20)
                        .userCategory(UserCategory.JOB_SEEKER)
                        .gender(1)
                        .nickname("규준")
                        .regionId(1)
                        .build();

        Long commentId = 1L;
        String content =
                "동해물과 백두산이 마르고 닳도록\n"
                        + "하느님이 보우하사 우리나라 만세\n"
                        + "무궁화 삼천리 화려 강산\n"
                        + "대한 사람 대한으로 길이 보전하세"
                        + "남산 위에 저 소나무 철갑을 두른 듯\n"
                        + "바람 서리 불변함은 우리 기상일세\n"
                        + "무궁화 삼천리 화려 강산\n"
                        + "대한 사람 대한으로 길이 보전하세"
                        + "가을 하늘 공활한데 높고 구름 없이\n"
                        + "밝은 달은 우리 가슴 일편단심일세\n"
                        + "무궁화 삼천리 화려 강산\n"
                        + "대한 사람 대한으로 길이 보전하세"
                        + "이 기상과 이 맘으로 충성을 다하여\n"
                        + "괴로우나 즐거우나 나라 사랑하세\n"
                        + "무궁화 삼천리 화려 강산\n"
                        + "대한 사람 대한으로 길이 보전하세";

        Reply reply = Reply.builder().id(1L).userId(1L).content(content).commentId(commentId).build();

        mockMvc
                .perform(
                        post("/api/comments/" + commentId + "/replies")
                                .with(SecurityMockMvcRequestPostProcessors.user(new CustomDetails(user)))
                                .content(mapper.writeValueAsString(reply))
                                .contentType(MediaType.APPLICATION_JSON)
                                .session(session))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errorName").value(BIND_ERROR.getErrorName()))
                .andExpect(jsonPath("$.errorMessage").value(BIND_ERROR.getErrorMessage()))
                .andDo(print());
    }

    @DisplayName("답글 저장 실패_댓글 존재하지 않음")
    @Test
    void saveReplyFailWithCommentNotMatch() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        MockHttpSession session = new MockHttpSession();
        User user =
                User.builder()
                        .id(1L)
                        .email("gyu@naver.com")
                        .password("AAbb11!!")
                        .monthBudget(100000L)
                        .age(20)
                        .userCategory(UserCategory.JOB_SEEKER)
                        .gender(1)
                        .nickname("규준")
                        .regionId(1)
                        .build();

        Long commentId = 1L;

        Reply reply =
                Reply.builder().id(1L).userId(1L).commentId(commentId).content("규준 거준 구준표").build();

        doThrow(new CommentException(COMMENT_NOT_FOUND))
                .when(replyService)
                .createReply(any(), any(), any());

        mockMvc
                .perform(
                        post("/api/comments/" + commentId + "/replies")
                                .with(SecurityMockMvcRequestPostProcessors.user(new CustomDetails(user)))
                                .content(mapper.writeValueAsString(reply))
                                .contentType(MediaType.APPLICATION_JSON)
                                .session(session))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.errorName").value(COMMENT_NOT_FOUND.getErrorName()))
                .andExpect(jsonPath("$.errorMessage").value(COMMENT_NOT_FOUND.getErrorMessage()))
                .andDo(print());
    }

    @DisplayName("답글 조회 성공")
    @Test
    void findALlReplyByCommentId() throws Exception {
        MockHttpSession session = new MockHttpSession();
        User user =
                User.builder()
                        .id(1L)
                        .email("gyu@naver.com")
                        .password("AAbb11!!")
                        .monthBudget(100000L)
                        .age(20)
                        .userCategory(UserCategory.JOB_SEEKER)
                        .gender(1)
                        .nickname("규준")
                        .regionId(1)
                        .build();

        ReplyResponse reply =
                ReplyResponse.builder()
                        .replyId(1L)
                        .commentId(1L)
                        .content("규준 발표 파이팅")
                        .user(UserResponse.from(user))
                        .build();

        long commentId = 1L;
        int size = 3;
        int page = 0;
        Long time = Instant.now().toEpochMilli();
        PageRequest pageRequest = PageRequest.of(page, size);
        List<ReplyResponse> replies = new ArrayList<>();
        replies.add(reply);
        Slice<ReplyResponse> res = new SliceImpl<>(replies, pageRequest, true);

        given(replyService.findAllByCommentId(user.getId(), commentId, time, pageRequest))
                .willReturn(res);

        mockMvc
                .perform(
                        get("/api/comments/" + commentId + "/replies")
                                .with(SecurityMockMvcRequestPostProcessors.user(new CustomDetails(user)))
                                .param("size", String.valueOf(size))
                                .param("page", String.valueOf(page))
                                .param("time", String.valueOf(time))
                                .session(session))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @DisplayName("답글 조회 실패_시간 파라미터 없음")
    @Test
    void findALlReplyByCommentIdFailWithNoTimeParam() throws Exception {
        MockHttpSession session = new MockHttpSession();
        User user =
                User.builder()
                        .id(1L)
                        .email("gyu@naver.com")
                        .password("AAbb11!!")
                        .monthBudget(100000L)
                        .age(20)
                        .userCategory(UserCategory.JOB_SEEKER)
                        .gender(1)
                        .nickname("규준")
                        .regionId(1)
                        .build();

        long commentId = 1L;
        int size = 3;
        int page = 0;

        mockMvc
                .perform(
                        get("/api/comments/" + commentId + "/replies")
                                .with(SecurityMockMvcRequestPostProcessors.user(new CustomDetails(user)))
                                .param("size", String.valueOf(size))
                                .param("page", String.valueOf(page))
                                .session(session))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errorName").value(REQUIRED_PARAM_NOT_FOUND.getErrorName()))
                .andExpect(jsonPath("$.errorMessage").value(REQUIRED_PARAM_NOT_FOUND.getErrorMessage()))
                .andDo(print());
    }

    @DisplayName("답글 수정 성공")
    @Test
    void modifyReply() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        MockHttpSession session = new MockHttpSession();
        User user =
                User.builder()
                        .id(1L)
                        .email("gyu@naver.com")
                        .password("AAbb11!!")
                        .monthBudget(100000L)
                        .age(20)
                        .userCategory(UserCategory.JOB_SEEKER)
                        .gender(1)
                        .nickname("규준")
                        .regionId(1)
                        .build();
        long commentId = 1L;

        ReplyRequest.Modify reply = ReplyRequest.Modify.builder().content("답글을 수정해볼거야").build();

        Reply modifiedReply =
                Reply.builder()
                        .id(1L)
                        .userId(user.getId())
                        .commentId(1L)
                        .content(reply.getContent())
                        .createdAt(Instant.now())
                        .updatedAt(Instant.now())
                        .build();

        this.mockMvc
                .perform(
                        patch("/api/comments/" + commentId + "/replies/" + modifiedReply.getId())
                                .with(SecurityMockMvcRequestPostProcessors.user(new CustomDetails(user)))
                                .content(mapper.writeValueAsString(reply))
                                .contentType(MediaType.APPLICATION_JSON)
                                .session(session))
                .andExpect(status().isOk())
                .andDo(print());
    }
}
