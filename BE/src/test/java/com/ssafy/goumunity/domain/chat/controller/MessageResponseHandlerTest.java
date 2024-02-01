package com.ssafy.goumunity.domain.chat.controller;

import static com.ssafy.goumunity.domain.chat.exception.ChatErrorCode.CANT_ACCESS_MESSAGE;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.ssafy.goumunity.domain.chat.exception.ChatException;
import com.ssafy.goumunity.domain.chat.service.ChatService;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.SliceImpl;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

@WebMvcTest(controllers = {MessageHandler.class})
class MessageResponseHandlerTest {

    @MockBean ChatService chatService;

    @Autowired MockMvc mockMvc;

    @WithMockUser
    @Test
    void 이전_채팅_목록_조회_테스트_성공() throws Exception {
        // given
        given(chatService.findPreviousMessage(any(), any(), any(), any()))
                .willReturn(new SliceImpl<>(List.of(), PageRequest.of(0, 12), false));

        Long chatRoomId = 1L;
        int page = 0;
        int size = 12;
        Long time = 1000000L;
        String url = "/api/chat-room/" + chatRoomId + "/messages";
        // when
        ResultActions sut =
                mockMvc.perform(
                        get(url)
                                .queryParam("page", Integer.toString(page))
                                .queryParam("size", Integer.toString(size))
                                .queryParam("time", Long.toString(time))
                                .with(csrf()));
        // then
        sut.andExpectAll(
                        status().isOk(),
                        jsonPath("$.contents.length()").value(0),
                        jsonPath("$.hasNext").value(false))
                .andDo(print());
    }

    @WithMockUser
    @Test
    void 이전_채팅_목록_조회_테스트_실패_채팅방에_접근할_수_없는_경우() throws Exception {
        // given
        given(chatService.findPreviousMessage(any(), any(), any(), any()))
                .willThrow(new ChatException(CANT_ACCESS_MESSAGE));
        Long chatRoomId = 1L;
        int page = 0;
        int size = 12;
        Long time = 1000000L;
        String url = "/api/chat-room/" + chatRoomId + "/messages";
        // when
        ResultActions sut =
                mockMvc.perform(
                        get(url)
                                .queryParam("page", Integer.toString(page))
                                .queryParam("size", Integer.toString(size))
                                .queryParam("time", Long.toString(time))
                                .with(csrf()));
        // then
        sut.andExpectAll(
                        status().isUnauthorized(),
                        jsonPath("$.errorName").value(CANT_ACCESS_MESSAGE.getErrorName()),
                        jsonPath("$.errorMessage").value(CANT_ACCESS_MESSAGE.getErrorMessage()))
                .andDo(print());
    }
}
