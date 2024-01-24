package com.ssafy.goumunity.domain.chat.controller;

import static com.ssafy.goumunity.common.exception.GlobalErrorCode.BIND_ERROR;
import static com.ssafy.goumunity.domain.chat.exception.ChatErrorCode.ALREADY_CREATED_HASHTAG;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.goumunity.common.exception.GlobalExceptionHandler;
import com.ssafy.goumunity.common.util.SliceResponse;
import com.ssafy.goumunity.domain.chat.controller.request.HashtagCreateRequest;
import com.ssafy.goumunity.domain.chat.domain.Hashtag;
import com.ssafy.goumunity.domain.chat.exception.ChatException;
import com.ssafy.goumunity.domain.chat.service.HashtagService;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = {HashtagController.class, GlobalExceptionHandler.class})
class HashtagControllerTest {

    @Autowired MockMvc mockMvc;

    @MockBean HashtagService hashtagService;

    @InjectMocks HashtagController hashtagController;

    private static final String HASHTAG_API_PREFIX = "/api/hashtags";

    private final ObjectMapper mapper = new ObjectMapper();

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

    @WithMockUser
    @Test
    void 해시태그_추가_성공() throws Exception {
        // given

        String content = mapper.writeValueAsString(HashtagCreateRequest.builder().name("해시태그").build());

        given(hashtagService.createHashtag(any()))
                .willReturn(Hashtag.builder().id(1L).name("tag1").build());
        // when         // then
        mockMvc
                .perform(
                        post(HASHTAG_API_PREFIX)
                                .content(content)
                                .contentType(MediaType.APPLICATION_JSON)
                                .with(csrf()))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("tag1"))
                .andDo(print());
    }

    @WithMockUser
    @Test
    void 해시태그_추가_실패_이름이_공백인_경우() throws Exception {
        // given
        String content = mapper.writeValueAsString(HashtagCreateRequest.builder().name("").build());

        given(hashtagService.createHashtag(any()))
                .willReturn(Hashtag.builder().id(1L).name("tag1").build());
        // when         // then

        String hashtagCreateRequestUrl = HASHTAG_API_PREFIX;
        mockMvc
                .perform(
                        post(hashtagCreateRequestUrl)
                                .content(content)
                                .contentType(MediaType.APPLICATION_JSON)
                                .with(csrf()))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errorName").value(BIND_ERROR.name()))
                .andExpect(jsonPath("$.errorMessage").value(BIND_ERROR.getErrorMessage()))
                .andExpect(jsonPath("$.path").value(hashtagCreateRequestUrl))
                .andDo(print());
    }

    @WithMockUser
    @Test
    void 해시태그_추가_실패_이름이_null인_경우() throws Exception {
        // given
        String content = mapper.writeValueAsString(HashtagCreateRequest.builder().name(null).build());

        // when         // then
        String hashtagCreateRequestUrl = HASHTAG_API_PREFIX;
        mockMvc
                .perform(
                        post(hashtagCreateRequestUrl)
                                .content(content)
                                .contentType(MediaType.APPLICATION_JSON)
                                .with(csrf()))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errorName").value(BIND_ERROR.name()))
                .andExpect(jsonPath("$.errorMessage").value(BIND_ERROR.getErrorMessage()))
                .andExpect(jsonPath("$.path").value(hashtagCreateRequestUrl))
                .andDo(print());
    }

    @WithMockUser
    @Test
    void 해시태그_추가_실패_이름이_10글자를_초과한_경우() throws Exception {
        // given
        String content =
                mapper.writeValueAsString(HashtagCreateRequest.builder().name("1234567890101010").build());

        // when         // then
        String hashtagCreateRequestUrl = HASHTAG_API_PREFIX;
        mockMvc
                .perform(
                        post(hashtagCreateRequestUrl)
                                .content(content)
                                .contentType(MediaType.APPLICATION_JSON)
                                .with(csrf()))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errorName").value(BIND_ERROR.name()))
                .andExpect(jsonPath("$.errorMessage").value(BIND_ERROR.getErrorMessage()))
                .andExpect(jsonPath("$.path").value(hashtagCreateRequestUrl))
                .andDo(print());
    }

    @WithMockUser
    @Test
    void 해시태그_추가_실패_이미_등록된_해시태그인_경우() throws Exception {
        // given
        String content =
                mapper.writeValueAsString(HashtagCreateRequest.builder().name("해시태그 ㅎ").build());

        given(hashtagService.createHashtag(any()))
                .willThrow(new ChatException(ALREADY_CREATED_HASHTAG));

        // when         // then
        String hashtagCreateRequestUrl = HASHTAG_API_PREFIX;
        mockMvc
                .perform(
                        post(hashtagCreateRequestUrl)
                                .content(content)
                                .contentType(MediaType.APPLICATION_JSON)
                                .with(csrf()))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.errorName").value(ALREADY_CREATED_HASHTAG.name()))
                .andExpect(jsonPath("$.errorMessage").value(ALREADY_CREATED_HASHTAG.getErrorMessage()))
                .andExpect(jsonPath("$.path").value(hashtagCreateRequestUrl))
                .andDo(print());
    }
}
