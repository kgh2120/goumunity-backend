package com.ssafy.goumunity.domain.feed.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.goumunity.common.exception.feed.ResourceNotFoundException;
import com.ssafy.goumunity.domain.feed.controller.response.FeedImgResponse;
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
class FeedImgControllerTest {

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    MockMvc mockMvc;

    @Test
    void 피드사진_ID단건조회_테스트(){
        /*
            2024.01.24 17.11 통과
         */

        try{
            MockHttpServletResponse mockHttpServletResponse = mockMvc.perform(
                            get("http://127.0.0.1:8080/api/feedimgs/1").accept(MediaType.APPLICATION_JSON_UTF8).contentType("charset=UTF-8")
                    )
                    .andExpect(status().isOk())
                    .andReturn().getResponse();

            FeedImgResponse feedImgResponse = objectMapper.readValue(mockHttpServletResponse.getContentAsString(), FeedImgResponse.class);

            // null테스트
            assertNotNull(feedImgResponse);

            // 내부 데이터 테스트
            assertEquals(feedImgResponse.getFeedImgId(), 1);
            assertEquals(feedImgResponse.getImgSrc(), "111111");
            assertEquals(feedImgResponse.getSequence(), 1);
        } catch (Exception e){
            assertEquals(e.getClass(), ResourceNotFoundException.class);
        }

    }

    @Test
    void 피드사진_피드ID별전체조회_테스트(){
        /*
            2024.01.24 17.14 통과
         */

        try {
            MockHttpServletResponse mockHttpServletResponse = mockMvc.perform(
                            get("http://127.0.0.1:8080/api/feeds/1/feedimgs").accept(MediaType.APPLICATION_JSON_UTF8).contentType("charset=UTF-8")
                    )
                    .andExpect(status().isOk())
                    .andReturn().getResponse();

            FeedImgResponse[] feedImgResponse = objectMapper.readValue(mockHttpServletResponse.getContentAsString(), FeedImgResponse[].class);

            // null 확인
            assertNotNull(feedImgResponse);

            // 내부 데이터 확인
            assertEquals(feedImgResponse[0].getSequence(), 1);
            assertEquals(feedImgResponse[0].getImgSrc(), "111111");
            assertEquals(feedImgResponse[1].getImgSrc(), "122222");

        } catch (Exception e){
            assertEquals(e.getClass(), ResourceNotFoundException.class);
        }
    }

}