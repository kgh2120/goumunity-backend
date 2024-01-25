package com.ssafy.goumunity.domain.feed.controller;

import static com.ssafy.goumunity.common.exception.CustomErrorCode.COMMENT_NOT_FOUND;
import static com.ssafy.goumunity.common.exception.GlobalErrorCode.BIND_ERROR;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.goumunity.common.exception.CustomException;
import com.ssafy.goumunity.common.exception.GlobalExceptionHandler;
import com.ssafy.goumunity.config.SecurityConfig;
import com.ssafy.goumunity.config.security.CustomDetails;
import com.ssafy.goumunity.domain.feed.domain.Reply;
import com.ssafy.goumunity.domain.feed.service.ReplyService;
import com.ssafy.goumunity.domain.user.domain.User;
import com.ssafy.goumunity.domain.user.domain.UserCategory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityFilterAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
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
                Reply.builder().replyId(1L).userId(1L).commentId(commentId).content("규준 거준 구준표").build();

        given(replyService.saveReply(any(), any(), any())).willReturn(reply);

        mockMvc
                .perform(
                        post("/api/comments/" + commentId + "/replies")
                                .with(SecurityMockMvcRequestPostProcessors.user(new CustomDetails(user)))
                                .content(mapper.writeValueAsString(reply))
                                .contentType(MediaType.APPLICATION_JSON)
                                .session(session))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.content").value(reply.getContent()))
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

        Reply reply = Reply.builder().replyId(1L).userId(1L).commentId(commentId).build();

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

        Reply reply =
                Reply.builder().replyId(1L).userId(1L).content(content).commentId(commentId).build();

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
                Reply.builder().replyId(1L).userId(1L).commentId(commentId).content("규준 거준 구준표").build();

        given(replyService.saveReply(any(), any(), any()))
                .willThrow(new CustomException(COMMENT_NOT_FOUND));

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
}
