package com.ssafy.goumunity.domain.chat.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.ssafy.goumunity.common.util.SliceResponse;
import com.ssafy.goumunity.domain.chat.domain.Hashtag;
import com.ssafy.goumunity.domain.chat.service.HashtagService;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = HashtagController.class)
class HashtagControllerTest {

    @Autowired MockMvc mockMvc;

    @MockBean HashtagService hashtagService;

    @InjectMocks HashtagController hashtagController;

    private static final String HASHTAG_API_PREFIX = "/api/hashtags";

    @WithMockUser
    @Test
    void 해시태그_검색_성공() throws Exception {
        // given

        String keyword = "test";
        int page = 0;
        int size = 12;

        given(hashtagService.findAllByHashtagName(any(), any(), any()))
                .willReturn(
                        SliceResponse.from(
                                List.of(
                                        Hashtag.builder().id(1L).name("tag1").build(),
                                        Hashtag.builder().id(2L).name("tag2").build(),
                                        Hashtag.builder().id(3L).name("tag3").build()),
                                false));
        // when         // then
        mockMvc
                .perform(
                        get(HASHTAG_API_PREFIX)
                                .queryParam("page", Integer.toString(page))
                                .queryParam("size", Integer.toString(size))
                                .queryParam("keyword", keyword)
                                .queryParam("time", Long.toString(1L)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.hasNext").value(false))
                .andExpect(jsonPath("$.contents.length()").value(3))
                .andExpect(jsonPath("$.contents[0].id").value(1))
                .andExpect(jsonPath("$.contents[0].name").value("tag1"))
                .andDo(print());
    }
}
