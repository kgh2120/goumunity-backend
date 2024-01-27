package com.ssafy.goumunity.domain.feed.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.goumunity.common.exception.GlobalExceptionHandler;
import com.ssafy.goumunity.config.SecurityConfig;
import com.ssafy.goumunity.domain.feed.controller.request.CommentLikeCountRequest;
import com.ssafy.goumunity.domain.feed.controller.request.CommentLikeRequest;
import com.ssafy.goumunity.domain.feed.controller.response.CommentLikeCountResponse;
import com.ssafy.goumunity.domain.feed.service.CommentLikeService;
import org.junit.jupiter.api.*;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityFilterAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

    @MockBean
    private CommentLikeService commentLikeService;

    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper mapper = new ObjectMapper();

    @Nested
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    class 좋아요클릭{
        CommentLikeRequest commentLikeRequest;

        @BeforeAll
        void 요청DTO장전(){
            commentLikeRequest = CommentLikeRequest.builder()
                    .userId(Long.valueOf(1))
                    .commentId(Long.valueOf(1))
                    .build();
        }

        @Test
        @DisplayName("좋아요UP_성공")
        void 좋아요UP테스트() throws Exception{

            BDDMockito.given(commentLikeService.pushLikeButton(any(), any()))
                    .willReturn(true);

            mockMvc.perform(
                    post("/api/comments/1/commentlikes")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(mapper.writeValueAsString(commentLikeRequest))
            ).andExpect(status().isCreated());
        }

        @Test
        @DisplayName("좋아요DOWN_성공")
        void 좋아요DOWN테스트() throws Exception{

            BDDMockito.given(commentLikeService.pushLikeButton(any(), any()))
                    .willReturn(false);

            mockMvc.perform(
                    post("/api/comments/1/commentlikes")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(mapper.writeValueAsString(commentLikeRequest))
            ).andExpect(status().isOk());
        }

    }

    @Nested
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    class 좋아요개수확인{
        CommentLikeCountRequest commentLikeCountRequest;
        CommentLikeCountResponse commentLikeCountResponse;

        @BeforeAll
        void 좋아요개수요청응답장전(){

            commentLikeCountRequest = CommentLikeCountRequest.builder()
                    .commentId(Long.valueOf(1))
                    .build();

            commentLikeCountResponse = CommentLikeCountResponse.builder()
                    .likeCount(20)
                    .build();
        }

        @Test
        @DisplayName("좋아요개수확인_성공")
        void 좋아요개수확인테스트() throws Exception{

            BDDMockito.given(
                    commentLikeService.countCommentLikeByCommentId(any())
            ).willReturn(commentLikeCountResponse);

            ResultActions resultActions = mockMvc.perform(
                    get("/api/comments/20/commentlikes")
                            .contentType(MediaType.APPLICATION_JSON)
            ).andExpect(status().isOk());

            CommentLikeCountResponse result = mapper.readValue(resultActions.andReturn().getResponse().getContentAsString(), CommentLikeCountResponse.class);

            assertEquals(result.getLikeCount(), 20);

        }

    }

}
