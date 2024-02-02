package com.ssafy.goumunity.domain.feed.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.ssafy.goumunity.common.config.SecurityConfig;
import com.ssafy.goumunity.common.config.security.CustomDetails;
import com.ssafy.goumunity.common.exception.GlobalExceptionHandler;
import com.ssafy.goumunity.domain.feed.service.FeedLikeService;
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
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(
        controllers = {FeedLikeController.class, GlobalExceptionHandler.class},
        excludeFilters = {
            @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = SecurityConfig.class)
        },
        excludeAutoConfiguration = {
            SecurityAutoConfiguration.class,
            SecurityFilterAutoConfiguration.class
        })
class FeedLikeControllerTest {

    @MockBean private FeedLikeService feedLikeService;

    @Autowired private MockMvc mockMvc;

    @DisplayName("피드 좋아요 성공")
    @Test
    void feedLike() throws Exception {
        MockHttpSession session = new MockHttpSession();
        long feedId = 1L;
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
                        .regionId(1L)
                        .build();

        mockMvc
                .perform(
                        post("/api/feeds/" + feedId + "/like")
                                .with(SecurityMockMvcRequestPostProcessors.user(new CustomDetails(user)))
                                .session(session))
                .andExpect(status().isCreated())
                .andDo(print());
    }

    @DisplayName("피드 좋아요 취소 성공")
    @Test
    void feedUnlike() throws Exception {
        MockHttpSession session = new MockHttpSession();
        long feedId = 1L;
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
                        .regionId(1L)
                        .build();

        mockMvc
                .perform(
                        delete("/api/feeds/" + feedId + "/unlike")
                                .with(SecurityMockMvcRequestPostProcessors.user(new CustomDetails(user)))
                                .session(session))
                .andExpect(status().isOk())
                .andDo(print());
    }
}
