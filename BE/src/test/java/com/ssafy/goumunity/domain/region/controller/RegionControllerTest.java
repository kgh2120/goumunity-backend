package com.ssafy.goumunity.domain.region.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.goumunity.common.exception.feed.ResourceNotFoundException;
import com.ssafy.goumunity.domain.region.controller.response.RegionResponse;
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
class RegionControllerTest {

    @Autowired
    ObjectMapper objectMapper;
    
    @Autowired
    MockMvc mockMvc;

    @Test
    void 전체지역조회_테스트() throws Exception{
        /*
        
            2024.01.24 13:17 통과
        
         */
        MockHttpServletResponse mockHttpServletResponse = mockMvc.perform(
                get("http://127.0.0.1:8080/api/regions").accept(MediaType.APPLICATION_JSON_UTF8).contentType("charset=UTF-8")
                )
                .andExpect(status().isOk())
                .andReturn().getResponse();

        RegionResponse[] data = objectMapper.readValue(mockHttpServletResponse.getContentAsString(), RegionResponse[].class);

        // null 테스트
        assertNotNull(data);
        
        // 사이즈 테스트
        assertEquals(data.length, 2);

        // 내부데이터 검증
        assertEquals(data[0].getRegionId(), 1);
        assertEquals(data[0].getSi(), "서울시");
        assertEquals(data[0].getGungu(), "관악구");

        assertEquals(data[1].getRegionId(), 2);
        assertEquals(data[1].getSi(), "서울시");
        assertEquals(data[1].getGungu(), "서초구");
    }

    @Test
    void 단건지역조회_ID_테스트(){

        /*
        
            2024.01.24 13:23 통과
        
         */

        // 있는 데이터 조회
        
        try {
            MockHttpServletResponse mockHttpServletResponse = mockMvc.perform(
                            get("http://127.0.0.1:8080/api/regions/1").accept(MediaType.APPLICATION_JSON_UTF8).contentType("charset=UTF-8")
                    )
                    .andExpect(status().isOk())
                    .andReturn().getResponse();
            
            RegionResponse data = objectMapper.readValue(mockHttpServletResponse.getContentAsString(), RegionResponse.class);
            
            // 있는 데이터 null 테스트
            assertNotNull(data);
            
            // 내부 데이터 테스트
            assertEquals(data.getRegionId(), 1);
            assertEquals(data.getSi(), "서울시");
            assertEquals(data.getGungu(), "관악구");
            
        } catch (Exception e){
            assertEquals(e.getClass(), ResourceNotFoundException.class);
        }

        // 예외 케이스 테스트 - 없는 데이터 조회
        
        try {
            MockHttpServletResponse mockHttpServletResponse = mockMvc.perform(
                            get("http://127.0.0.1:8080/api/regions/3").accept(MediaType.APPLICATION_JSON_UTF8).contentType("charset=UTF-8")
                    )
                    .andExpect(status().isInternalServerError())
                    .andReturn().getResponse();

        } catch (Exception e){
            // 예외 테스트
            assertEquals(e.getClass(), ResourceNotFoundException.class);
        }
    }
}