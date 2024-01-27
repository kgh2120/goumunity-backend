package com.ssafy.goumunity.domain.feed.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.goumunity.config.SecurityConfig;
import com.ssafy.goumunity.domain.feed.controller.request.FeedRegistRequest;
import com.ssafy.goumunity.domain.feed.controller.response.FeedResponse;
import com.ssafy.goumunity.domain.feed.domain.Feed;
import com.ssafy.goumunity.domain.feed.domain.FeedCategory;
import com.ssafy.goumunity.domain.feed.service.FeedImgService;
import com.ssafy.goumunity.domain.feed.service.FeedService;
import com.ssafy.goumunity.domain.region.controller.RegionController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(
        controllers = {FeedController.class},
        excludeFilters = {
                @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = SecurityConfig.class)
        },
        excludeAutoConfiguration = {
                SecurityAutoConfiguration.class,
                SecurityFilterAutoConfiguration.class
        })
class FeedControllerTest {

    @MockBean
    private FeedService feedService;

    @MockBean
    private FeedImgService feedImgService;

    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper mapper;

    private FeedRegistRequest feedRegistRequest;

    @BeforeEach
    void 매퍼등록() throws Exception{
        mapper = new ObjectMapper();
    }

    @Nested
    class 등록테스트{

        @Test
        @DisplayName("정상피드등록_성공")
        void 정상피드등록() throws Exception{

            feedRegistRequest = FeedRegistRequest.builder()
                    .feedCategory(FeedCategory.INFO)
                    .content("내용")
                    .price(10000)
                    .afterPrice(5000)
                    .profit(5000)
                    .regionId(Long.valueOf(1))
                    .userId(Long.valueOf(1))
                    .build();

            Feed feed = Feed.from(feedRegistRequest);

            FeedResponse feedResponse = FeedResponse.from(feed);

            BDDMockito.given(
                    feedService.findOneByFeedId(any())
            ).willReturn(feedResponse);

            mockMvc.perform(
                    post("/api/feeds").contentType(MediaType.APPLICATION_JSON)
                            .content(mapper.writeValueAsString(feedRegistRequest))
                            .accept(MediaType.APPLICATION_JSON)
            ).andExpect(status().isCreated());

        }

        @Test
        @DisplayName("널값등록_성공")
        void NULL등록() throws Exception{

            feedRegistRequest = FeedRegistRequest.builder()
                    .build();

            Feed feed = Feed.from(feedRegistRequest);

            FeedResponse feedResponse = FeedResponse.from(feed);

            BDDMockito.given(
                    feedService.findOneByFeedId(any())
            ).willReturn(feedResponse);

            mockMvc.perform(
                    post("/api/feeds").contentType(MediaType.APPLICATION_JSON)
                            .content(mapper.writeValueAsString(feedRegistRequest))
                            .accept(MediaType.APPLICATION_JSON)
            ).andExpect(status().isBadRequest());

        }

    }

}