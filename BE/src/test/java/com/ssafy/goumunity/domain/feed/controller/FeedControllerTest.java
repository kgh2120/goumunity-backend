package com.ssafy.goumunity.domain.feed.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.goumunity.common.exception.feed.ResourceNotFoundException;
import com.ssafy.goumunity.domain.feed.controller.response.FeedResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class FeedControllerTest {

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    MockMvc mockMvc;

    @Test
    void 피드_ID단건조회_테스트() {
        /*
            2024.01.24 16:10 통과
         */
        try {
            MockHttpServletResponse mockHttpServletResponse = mockMvc.perform(
                            get("http://127.0.0.1:8080/api/feeds/1").accept(MediaType.APPLICATION_JSON_UTF8).contentType("charset=UTF-8")
                    )
                    .andExpect(status().isOk())
                    .andReturn().getResponse();

            FeedResponse feedResponse = objectMapper.readValue(mockHttpServletResponse.getContentAsString(), FeedResponse.class);

            // null테스트
            assertNotNull(feedResponse);

            // 내부 데이터 테스트
            assertEquals(feedResponse.getFeedId(), 1);
            assertEquals(feedResponse.getContent(), "이게 내용이다");
            assertEquals(feedResponse.getRegionId(), 1);

        } catch (Exception e){
            assertEquals(e.getClass(), ResourceNotFoundException.class);
        }
    }

    @Test
    void 피드_유저ID_전체조회_테스트(){
        /*
            2024.01.24 16:10 통과
         */
        try{
            MockHttpServletResponse mockHttpServletResponse = mockMvc.perform(
                            get("http://127.0.0.1:8080/api/users/1/feeds").accept(MediaType.APPLICATION_JSON_UTF8).contentType("charset=UTF-8")
                    )
                    .andExpect(status().isOk())
                    .andReturn().getResponse();

            FeedResponse[] feedResponses = objectMapper.readValue(mockHttpServletResponse.getContentAsString(), FeedResponse[].class);

            // null 테스트
            assertNotNull(feedResponses);

            // 사이즈 테스트
            assertEquals(feedResponses.length, 3);

            // 내부데이터 검증
            assertEquals(feedResponses[0].getRegionId(), 1);
            assertEquals(feedResponses[0].getContent(), "이게 내용이다");
            assertEquals(feedResponses[0].getUserId(), 1);

        } catch (Exception e){
            assertEquals(e.getClass(), ResourceNotFoundException.class);
        }
    }
}