package com.ssafy.goumunity.domain.feed.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.goumunity.config.SecurityConfig;
import com.ssafy.goumunity.domain.feed.controller.request.FeedLikeCountRequest;
import com.ssafy.goumunity.domain.feed.controller.request.FeedLikeRequest;
import com.ssafy.goumunity.domain.feed.controller.response.FeedLikeCountResponse;
import com.ssafy.goumunity.domain.feed.service.FeedLikeService;
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

@WebMvcTest(
        controllers = {FeedLikeController.class},
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

    private ObjectMapper mapper = new ObjectMapper();

    @Nested
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    class 좋아요클릭 {

        FeedLikeRequest feedLikeRequest;

        @BeforeAll
        void 요청DTO장전() {

            feedLikeRequest =
                    FeedLikeRequest.builder().userId(Long.valueOf(1)).feedId(Long.valueOf(1)).build();
        }

        @Test
        @DisplayName("좋아요UP_성공")
        void 좋아요UP테스트() throws Exception {

            BDDMockito.given(feedLikeService.pushLikeButton(any(), any())).willReturn(true);

            mockMvc
                    .perform(
                            post("/api/feeds/1/feedlikes")
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content(mapper.writeValueAsString(feedLikeRequest)))
                    .andExpect(status().isCreated());
        }

        @Test
        @DisplayName("좋아요DOWN성공")
        void 좋아요DOWN테스트() throws Exception {

            BDDMockito.given(feedLikeService.pushLikeButton(any(), any())).willReturn(false);

            mockMvc
                    .perform(
                            post("/api/feeds/1/feedlikes")
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content(mapper.writeValueAsString(feedLikeRequest)))
                    .andExpect(status().isOk());
        }
    }

    @Nested
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    class 좋아요개수확인 {

        FeedLikeCountRequest feedLikeCountRequest;
        FeedLikeCountResponse feedLikeCountResponse;

        @BeforeAll
        void 좋아요개수요청응답장전() {

            feedLikeCountRequest = FeedLikeCountRequest.builder().feedId(Long.valueOf(1)).build();

            feedLikeCountResponse = FeedLikeCountResponse.builder().likeCount(30).build();
        }

        @Test
        @DisplayName("좋아요갯수확인_성공")
        void 좋아요갯수확인테스트() throws Exception {

            BDDMockito.given(feedLikeService.countFeedLikeByFeedId(any()))
                    .willReturn(feedLikeCountResponse);

            ResultActions resultActions =
                    mockMvc
                            .perform(get("/api/feeds/35/feedlikes").contentType(MediaType.APPLICATION_JSON))
                            .andExpect(status().isOk());

            FeedLikeCountResponse result =
                    mapper.readValue(
                            resultActions.andReturn().getResponse().getContentAsString(),
                            FeedLikeCountResponse.class);

            assertEquals(result.getLikeCount(), 30);
        }
    }
}
