package com.ssafy.goumunity.domain.chat.controller;

import static com.ssafy.goumunity.common.exception.GlobalErrorCode.FORBIDDEN;
import static com.ssafy.goumunity.domain.chat.exception.ChatErrorCode.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.doThrow;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.goumunity.common.exception.CustomException;
import com.ssafy.goumunity.common.exception.GlobalErrorCode;
import com.ssafy.goumunity.common.exception.GlobalExceptionHandler;
import com.ssafy.goumunity.domain.chat.controller.request.ChatRoomRequest;
import com.ssafy.goumunity.domain.chat.controller.response.ChatRoomHashtagResponse;
import com.ssafy.goumunity.domain.chat.controller.response.ChatRoomSearchResponse;
import com.ssafy.goumunity.domain.chat.controller.response.ChatRoomUserResponse;
import com.ssafy.goumunity.domain.chat.exception.ChatException;
import com.ssafy.goumunity.domain.chat.service.ChatRoomService;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.SliceImpl;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.mock.web.MockPart;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = {ChatRoomController.class, GlobalExceptionHandler.class})
class ChatRoomControllerTest {

    @Autowired MockMvc mockMvc;

    @MockBean ChatRoomService chatRoomService;

    @InjectMocks ChatRoomController controller;

    private static final String CHAT_ROOM_API_PREFIX = "/api/chat-rooms";

    private ObjectMapper mapper = new ObjectMapper();

    @WithMockUser
    @Test
    void 채팅방생성테스트() throws Exception {
        // given
        ChatRoomRequest.Create dto =
                ChatRoomRequest.Create.builder()
                        .title("거지방")
                        .capability(10)
                        .regionId(1L)
                        .hashtags(
                                List.of(
                                        ChatRoomRequest.HashtagRequest.builder().id(1L).build(),
                                        ChatRoomRequest.HashtagRequest.builder().id(2L).build(),
                                        ChatRoomRequest.HashtagRequest.builder().id(3L).build()))
                        .build();

        MockPart data =
                new MockPart("data", "", mapper.writeValueAsBytes(dto), MediaType.APPLICATION_JSON);
        MockMultipartFile image = new MockMultipartFile("image", new byte[0]);
        // when
        mockMvc
                .perform(multipart(CHAT_ROOM_API_PREFIX).file(image).part(data).with(csrf()))
                // then
                .andExpect(status().isCreated())
                .andDo(print());
    }

    @WithMockUser
    @Test
    void 채팅방생성테스트_실패_해시태그_사이즈_초과() throws Exception {
        // given
        ChatRoomRequest.Create dto =
                ChatRoomRequest.Create.builder()
                        .title("거지방")
                        .capability(10)
                        .regionId(1L)
                        .hashtags(
                                List.of(
                                        ChatRoomRequest.HashtagRequest.builder().id(1L).build(),
                                        ChatRoomRequest.HashtagRequest.builder().id(2L).build(),
                                        ChatRoomRequest.HashtagRequest.builder().id(3L).build(),
                                        ChatRoomRequest.HashtagRequest.builder().id(3L).build(),
                                        ChatRoomRequest.HashtagRequest.builder().id(3L).build(),
                                        ChatRoomRequest.HashtagRequest.builder().id(3L).build(),
                                        ChatRoomRequest.HashtagRequest.builder().id(3L).build()))
                        .build();

        MockPart data =
                new MockPart("data", "", mapper.writeValueAsBytes(dto), MediaType.APPLICATION_JSON);
        MockMultipartFile image = new MockMultipartFile("image", new byte[0]);
        // when
        mockMvc
                .perform(multipart(CHAT_ROOM_API_PREFIX).file(image).part(data).with(csrf()))
                // then
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @WithMockUser
    @Test
    void 채팅방생성테스트_실패_이미지가_아닌_파일_업로드() throws Exception {
        // given
        ChatRoomRequest.Create dto =
                ChatRoomRequest.Create.builder()
                        .title("거지방")
                        .capability(10)
                        .regionId(1L)
                        .hashtags(
                                List.of(
                                        ChatRoomRequest.HashtagRequest.builder().id(1L).build(),
                                        ChatRoomRequest.HashtagRequest.builder().id(2L).build(),
                                        ChatRoomRequest.HashtagRequest.builder().id(3L).build()))
                        .build();

        MockPart data =
                new MockPart("data", "", mapper.writeValueAsBytes(dto), MediaType.APPLICATION_JSON);
        MockMultipartFile image = new MockMultipartFile("image", new byte[0]);

        doThrow(new CustomException(GlobalErrorCode.FILE_IS_NOT_IMAGE_TYPE))
                .when(chatRoomService)
                .createChatRoom(any(), any(), any());

        // when
        mockMvc
                .perform(multipart(CHAT_ROOM_API_PREFIX).file(image).part(data).with(csrf()))
                // then
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errorName").value(GlobalErrorCode.FILE_IS_NOT_IMAGE_TYPE.name()))
                .andExpect(
                        jsonPath("$.errorMessage")
                                .value(GlobalErrorCode.FILE_IS_NOT_IMAGE_TYPE.getErrorMessage()))
                .andExpect(jsonPath("$.path").value(CHAT_ROOM_API_PREFIX))
                .andDo(print());
    }

    @WithMockUser
    @Test
    void 채팅방생성테스트_실패_존재하지_않는_해시태그가_담긴_경우() throws Exception {
        // given
        ChatRoomRequest.Create dto =
                ChatRoomRequest.Create.builder()
                        .title("거지방")
                        .capability(10)
                        .regionId(1L)
                        .hashtags(
                                List.of(
                                        ChatRoomRequest.HashtagRequest.builder().id(1L).build(),
                                        ChatRoomRequest.HashtagRequest.builder().id(2L).build(),
                                        ChatRoomRequest.HashtagRequest.builder().id(3L).build()))
                        .build();

        MockPart data =
                new MockPart("data", "", mapper.writeValueAsBytes(dto), MediaType.APPLICATION_JSON);
        MockMultipartFile image = new MockMultipartFile("image", new byte[0]);

        doThrow(new ChatException(HASHTAG_NOT_FOUND))
                .when(chatRoomService)
                .createChatRoom(any(), any(), any());

        // when
        mockMvc
                .perform(multipart(CHAT_ROOM_API_PREFIX).file(image).part(data).with(csrf()))
                // then
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.errorName").value(HASHTAG_NOT_FOUND.name()))
                .andExpect(jsonPath("$.errorMessage").value(HASHTAG_NOT_FOUND.getErrorMessage()))
                .andExpect(jsonPath("$.path").value(CHAT_ROOM_API_PREFIX))
                .andDo(print());
    }

    @WithMockUser
    @Test
    void 채팅방_나가기_테스트_성공() throws Exception {
        // given
        Long chatRoomId = 1L;
        // when // then
        String deleteChatRoomUrl = CHAT_ROOM_API_PREFIX + "/" + chatRoomId;
        mockMvc
                .perform(delete(deleteChatRoomUrl).with(csrf()))
                .andExpectAll(status().isOk())
                .andDo(print());
    }

    @WithMockUser
    @Test
    void 채팅방_나가기_테스트_실패_채팅방이_존재하지_않는_경우() throws Exception {
        // given
        Long chatRoomId = 1L;
        // when // then
        String deleteChatRoomUrl = CHAT_ROOM_API_PREFIX + "/" + chatRoomId;

        doThrow(new ChatException(CHAT_ROOM_NOT_FOUND))
                .when(chatRoomService)
                .exitChatRoom(any(), any());
        mockMvc
                .perform(delete(deleteChatRoomUrl).with(csrf()))
                .andExpectAll(
                        status().isNotFound(),
                        jsonPath("$.errorName").value(CHAT_ROOM_NOT_FOUND.getErrorName()),
                        jsonPath("$.errorMessage").value(CHAT_ROOM_NOT_FOUND.getErrorMessage()))
                .andDo(print());
    }

    @WithMockUser
    @Test
    void 채팅방_나가기_테스트_실패_회원이_채팅방에_속하지_않는_경우() throws Exception {
        // given
        Long chatRoomId = 1L;
        // when // then
        String deleteChatRoomUrl = CHAT_ROOM_API_PREFIX + "/" + chatRoomId;

        doThrow(new CustomException(FORBIDDEN)).when(chatRoomService).exitChatRoom(any(), any());

        mockMvc
                .perform(delete(deleteChatRoomUrl).with(csrf()))
                .andExpectAll(
                        status().isForbidden(),
                        jsonPath("$.errorName").value(FORBIDDEN.getErrorName()),
                        jsonPath("$.errorMessage").value(FORBIDDEN.getErrorMessage()))
                .andDo(print());
    }

    @WithMockUser
    @Test
    void 채팅방_나가기_테스트_실패_방장이_나가려는_경우() throws Exception {
        // given
        Long chatRoomId = 1L;
        // when // then
        String deleteChatRoomUrl = CHAT_ROOM_API_PREFIX + "/" + chatRoomId;

        doThrow(new CustomException(HOST_CANT_OUT)).when(chatRoomService).exitChatRoom(any(), any());

        mockMvc
                .perform(delete(deleteChatRoomUrl).with(csrf()))
                .andExpectAll(
                        status().isConflict(),
                        jsonPath("$.errorName").value(HOST_CANT_OUT.getErrorName()),
                        jsonPath("$.errorMessage").value(HOST_CANT_OUT.getErrorMessage()))
                .andDo(print());
    }

    @WithMockUser
    @Test
    void 채팅방_검색_테스트() throws Exception {
        // given
        String keyword = "거지방";
        Long time = 10000L;
        int page = 0;
        int size = 12;

        List<ChatRoomSearchResponse> contents = new ArrayList<>();
        for (int i = 1; i < 3; i++) {
            contents.add(
                    ChatRoomSearchResponse.builder()
                            .title("거거지방")
                            .chatRoomId((long) i)
                            .imgSrc("/img.jpg")
                            .capability(10)
                            .currentUserCount(5)
                            .hashtags(
                                    List.of(
                                            ChatRoomHashtagResponse.builder().name("20대").build(),
                                            ChatRoomHashtagResponse.builder().name("거지방").build(),
                                            ChatRoomHashtagResponse.builder().name("관악구").build()))
                            .build());
        }
        given(chatRoomService.searchChatRoom(keyword, time, PageRequest.of(page, size)))
                .willReturn(new SliceImpl<>(contents, PageRequest.of(page, size), false));
        // when
        mockMvc
                .perform(
                        get(CHAT_ROOM_API_PREFIX + "/search")
                                .queryParam("keyword", keyword)
                                .queryParam("time", Long.toString(time))
                                .queryParam("page", String.valueOf(page))
                                .queryParam("size", String.valueOf(size)))
                // then
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$.hasNext").value(false),
                        jsonPath("$.contents.length()").value(2))
                .andDo(print());
    }

    @WithMockUser
    @Test
    void 거지방_종료_테스트_성공() throws Exception {
        // given
        Long chatRoomId = 1L;
        String url = CHAT_ROOM_API_PREFIX + "/" + chatRoomId + "/disconnect";
        // when
        mockMvc
                .perform(patch(url).with(csrf()))
                // then
                .andExpectAll(status().isOk())
                .andDo(print());
    }

    @WithMockUser
    @Test
    void 거지방_종료_테스트_실패_채팅방이_존재하지_않는_경우() throws Exception {
        // given
        Long chatRoomId = 1L;
        String url = CHAT_ROOM_API_PREFIX + "/" + chatRoomId + "/disconnect";
        doThrow(new ChatException(CHAT_ROOM_NOT_FOUND))
                .when(chatRoomService)
                .disconnectChatRoom(any(), any());
        // when
        mockMvc
                .perform(patch(url).with(csrf()))
                // then
                .andExpectAll(
                        status().isNotFound(),
                        jsonPath("$.errorMessage").value(CHAT_ROOM_NOT_FOUND.getErrorMessage()),
                        jsonPath("$.errorName").value(CHAT_ROOM_NOT_FOUND.getErrorName()))
                .andDo(print());
    }

    @WithMockUser
    @Test
    void 거지방_종료_테스트_실패_회원이_거지방에_참가하지_않은_경우() throws Exception {
        // given
        Long chatRoomId = 1L;
        String url = CHAT_ROOM_API_PREFIX + "/" + chatRoomId + "/disconnect";
        doThrow(new CustomException(FORBIDDEN)).when(chatRoomService).disconnectChatRoom(any(), any());
        // when
        mockMvc
                .perform(patch(url).with(csrf()))
                // then
                .andExpectAll(
                        status().isForbidden(),
                        jsonPath("$.errorMessage").value(FORBIDDEN.getErrorMessage()),
                        jsonPath("$.errorName").value(FORBIDDEN.getErrorName()))
                .andDo(print());
    }

    @WithMockUser
    @Test
    void 거지방_참가자_조회_테스트_성공() throws Exception {
        // given
        Long chatRoomId = 1L;
        Long time = 1000000000L;
        int page = 0;
        int size = 12;

        String url = CHAT_ROOM_API_PREFIX + "/" + chatRoomId + "/users";

        given(chatRoomService.findChatRoomUsers(any(), any(), any(), any()))
                .willReturn(
                        new SliceImpl<>(
                                List.of(
                                        ChatRoomUserResponse.builder().build(),
                                        ChatRoomUserResponse.builder().build(),
                                        ChatRoomUserResponse.builder().build()),
                                PageRequest.of(page, size),
                                false));
        // when
        mockMvc
                .perform(
                        get(url)
                                .queryParam("page", String.valueOf(page))
                                .queryParam("size", String.valueOf(size))
                                .queryParam("time", String.valueOf(time))
                                .with(csrf()))
                // then
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$.hasNext").value(false),
                        jsonPath("$.contents.length()").value(3))
                .andDo(print());
    }

    @WithMockUser
    @Test
    void 거지방_참가자_조회_테스트_실패_채팅방_접근_권한이_없는_경우() throws Exception {
        // given
        Long chatRoomId = 1L;
        Long time = 1000000000L;
        int page = 0;
        int size = 12;

        String url = CHAT_ROOM_API_PREFIX + "/" + chatRoomId + "/users";

        given(chatRoomService.findChatRoomUsers(any(), any(), any(), any()))
                .willThrow(new CustomException(FORBIDDEN));
        // when
        mockMvc
                .perform(
                        get(url)
                                .queryParam("page", String.valueOf(page))
                                .queryParam("size", String.valueOf(size))
                                .queryParam("time", String.valueOf(time))
                                .with(csrf()))
                // then
                .andExpectAll(
                        status().isForbidden(),
                        jsonPath("$.errorName").value(FORBIDDEN.getErrorName()),
                        jsonPath("$.errorMessage").value(FORBIDDEN.getErrorMessage()))
                .andDo(print());
    }
}
