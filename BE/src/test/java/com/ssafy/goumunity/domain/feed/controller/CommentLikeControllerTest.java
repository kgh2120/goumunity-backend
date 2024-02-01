package com.ssafy.goumunity.domain.feed.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.ssafy.goumunity.common.config.SecurityConfig;
import com.ssafy.goumunity.domain.feed.service.CommentLikeService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityFilterAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(
        controllers = {CommentLikeController.class},
        excludeFilters = {
            @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = SecurityConfig.class)
        },
        excludeAutoConfiguration = {
            SecurityAutoConfiguration.class,
            SecurityFilterAutoConfiguration.class
        })
class CommentLikeControllerTest {

    @Autowired private MockMvc mockMvc;

    @MockBean private CommentLikeService commentLikeService;

    @Nested
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    class 좋아요클릭 {
        @Test
        @DisplayName("좋아요UP_성공")
        void 좋아요UP테스트() throws Exception {

            mockMvc.perform(post("/api/comments/1/like")).andExpect(status().isCreated());
        }

        @Test
        @DisplayName("좋아요DOWN_성공")
        void 좋아요DOWN테스트() throws Exception {

            mockMvc.perform(delete("/api/comments/1/unlike")).andExpect(status().isOk());
        }
    }
}
