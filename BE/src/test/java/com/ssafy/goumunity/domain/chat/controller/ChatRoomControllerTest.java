package com.ssafy.goumunity.domain.chat.controller;

import static com.ssafy.goumunity.common.exception.CustomErrorCode.*;
import static com.ssafy.goumunity.domain.chat.exception.ChatErrorCode.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.goumunity.common.exception.CustomException;
import com.ssafy.goumunity.common.exception.GlobalExceptionHandler;
import com.ssafy.goumunity.domain.chat.controller.request.ChatRoomRequest;
import com.ssafy.goumunity.domain.chat.exception.ChatException;
import com.ssafy.goumunity.domain.chat.service.ChatRoomService;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
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

        doThrow(new CustomException(FILE_IS_NOT_IMAGE_TYPE))
                .when(chatRoomService)
                .createChatRoom(any(), any(), any());

        // when
        mockMvc
                .perform(multipart(CHAT_ROOM_API_PREFIX).file(image).part(data).with(csrf()))
                // then
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errorName").value(FILE_IS_NOT_IMAGE_TYPE.name()))
                .andExpect(jsonPath("$.errorMessage").value(FILE_IS_NOT_IMAGE_TYPE.getErrorMessage()))
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
}
