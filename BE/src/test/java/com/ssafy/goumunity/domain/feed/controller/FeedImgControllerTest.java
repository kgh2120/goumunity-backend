package com.ssafy.goumunity.domain.feed.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.goumunity.common.exception.feed.ResourceNotFoundException;
import com.ssafy.goumunity.domain.feed.controller.response.FeedImgResponse;
import com.ssafy.goumunity.domain.feed.controller.response.FeedResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class FeedImgControllerTest {

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    MockMvc mockMvc;

    @Nested
    class 피드사진_CONTROLLER {
        @Test
        @DisplayName("피드사진단건조회_NULL확인_성공")
        void 피드사진_ID단건조회_NULL확인() throws Exception {
        /*
            2024.01.24 17.11 통과
         */
            MockHttpServletResponse mockHttpServletResponse = mockMvc.perform(
                            get("http://127.0.0.1:8080/api/feedimgs/1").accept(MediaType.APPLICATION_JSON_UTF8).contentType("charset=UTF-8")
                    )
                    .andExpect(status().isOk())
                    .andReturn().getResponse();

            FeedImgResponse feedImgResponse = objectMapper.readValue(mockHttpServletResponse.getContentAsString(), FeedImgResponse.class);

            // null테스트
            assertNotNull(feedImgResponse);
        }

        @Test
        @DisplayName("피드사진단건조회_내부데이터확인_성공")
        void 피드사진_ID단건조회_테스트_내부데이터() throws Exception {
        /*
            2024.01.24 17.11 통과
         */
            MockHttpServletResponse mockHttpServletResponse = mockMvc.perform(
                            get("http://127.0.0.1:8080/api/feedimgs/1").accept(MediaType.APPLICATION_JSON_UTF8).contentType("charset=UTF-8")
                    )
                    .andExpect(status().isOk())
                    .andReturn().getResponse();

            FeedImgResponse feedImgResponse = objectMapper.readValue(mockHttpServletResponse.getContentAsString(), FeedImgResponse.class);

            // 내부 데이터 테스트
            assertEquals(feedImgResponse.getFeedImgId(), 1);
            assertEquals(feedImgResponse.getImgSrc(), "111111");
            assertEquals(feedImgResponse.getSequence(), 1);
        }

        @Test
        @DisplayName("피드사진단건조회_예외테스트")
        void 피드사진_ID단건조회_예외테스트() throws Exception {
        /*
            2024.01.24 17.11 통과
         */

            ResultActions resultActions = mockMvc.perform(
                    get("http://127.0.0.1:8080/api/feedimgs/4").accept(MediaType.APPLICATION_JSON_UTF8).contentType("charset=UTF-8")
            );

            MvcResult mvcResult = resultActions
                    .andExpect(status().isInternalServerError())
                    .andReturn();

            assertTrue(mvcResult.getResolvedException() instanceof ResourceNotFoundException);

        }

        @Test
        @DisplayName("피드사진_피드별전체조회_NOTNULL확인_성공")
        void 피드사진_피드ID별전체조회_NULL테스트() throws Exception {
        /*
            2024.01.24 17.14 통과
         */
            MockHttpServletResponse mockHttpServletResponse = mockMvc.perform(
                            get("http://127.0.0.1:8080/api/feeds/1/feedimgs").accept(MediaType.APPLICATION_JSON_UTF8).contentType("charset=UTF-8")
                    )
                    .andExpect(status().isOk())
                    .andReturn().getResponse();

            FeedImgResponse[] feedImgResponse = objectMapper.readValue(mockHttpServletResponse.getContentAsString(), FeedImgResponse[].class);

            // null 확인
            assertNotNull(feedImgResponse);
        }

        @Test
        @DisplayName("피드사진_피드별전체조회_내부데이터테스트_성공")
        void 피드사진_피드ID별전체조회_내부데이터테스트() throws Exception {
        /*
            2024.01.24 17.14 통과
         */
            MockHttpServletResponse mockHttpServletResponse = mockMvc.perform(
                            get("http://127.0.0.1:8080/api/feeds/1/feedimgs").accept(MediaType.APPLICATION_JSON_UTF8).contentType("charset=UTF-8")
                    )
                    .andExpect(status().isOk())
                    .andReturn().getResponse();

            FeedImgResponse[] feedImgResponse = objectMapper.readValue(mockHttpServletResponse.getContentAsString(), FeedImgResponse[].class);

            // 내부 데이터 확인
            assertEquals(feedImgResponse[0].getSequence(), 1);
            assertEquals(feedImgResponse[0].getImgSrc(), "111111");
            assertEquals(feedImgResponse[1].getImgSrc(), "122222");
        }

        @Test
        @DisplayName("피드사진_피드별전체조회_없는데이터조회_성공")
        void 피드사진_피드ID별전체조회_없는데이터조회() throws Exception {
        /*
            2024.01.24 17.14 통과
         */

            MockHttpServletResponse mockHttpServletResponse = mockMvc.perform(
                            get("http://127.0.0.1:8080/api/feeds/50/feedimgs").accept(MediaType.APPLICATION_JSON_UTF8).contentType("charset=UTF-8")
                    )
                    .andExpect(status().isOk())
                    .andReturn().getResponse();

            FeedImgResponse[] feedImgResponse = objectMapper.readValue(mockHttpServletResponse.getContentAsString(), FeedImgResponse[].class);

            // 내부 데이터 확인
            assertEquals(feedImgResponse.length, 0);
        }
    }
}