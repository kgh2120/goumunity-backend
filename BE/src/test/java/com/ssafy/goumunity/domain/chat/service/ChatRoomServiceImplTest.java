package com.ssafy.goumunity.domain.chat.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.goumunity.domain.chat.controller.request.ChatRoomRequest;
import com.ssafy.goumunity.domain.chat.exception.ChatErrorCode;
import com.ssafy.goumunity.domain.chat.exception.ChatException;
import com.ssafy.goumunity.domain.chat.service.port.ChatRoomRepository;
import com.ssafy.goumunity.domain.chat.service.port.ImageUploadService;
import com.ssafy.goumunity.domain.chat.service.port.RegionFindService;
import com.ssafy.goumunity.domain.user.domain.User;
import jakarta.mail.Multipart;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.mock.web.MockPart;

import java.util.ArrayList;
import java.util.List;


import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class ChatRoomServiceImplTest {

    @Mock
    HashtagService hashtagService;
    @Mock
    ChatRoomRepository chatRoomRepository;
    @Mock
    ImageUploadService uploadService;
    @Mock
    RegionFindService regionFindService;
    @InjectMocks
    ChatRoomServiceImpl chatRoomService;

    @Test
    void 채팅방_생성_테스트_성공() throws Exception {
        // given

        // 채팅방 리퀘스트
        // 파일
        // 유저 정보
        List<ChatRoomRequest.HashtagRequest> hashtags = new ArrayList<>();
        for (int i = 1; i <= 3; i++) {
            hashtags.add(ChatRoomRequest.HashtagRequest.builder()
                    .id((long) i)
                    .build());
        }

        ChatRoomRequest.Create request = ChatRoomRequest.Create.builder()
                .title("거지방")
                .capability(10)
                .regionId(1L)
                .hashtags(hashtags)
                .build();
        MockMultipartFile image = new MockMultipartFile("image", new byte[0]);
        User user = User.builder()
                .id(2L)
                .build();

        given(hashtagService.existsOneByHashtagId(any()))
                .willReturn(true);
        given(uploadService.uploadImage(any()))
                .willReturn("image");
        given(regionFindService.isExistsRegion(any()))
                .willReturn(true);
        // when

        assertThatNoException().isThrownBy(()
                -> chatRoomService.createChatRoom(request, image, user));

        // then

    }


    /*
        생성 실패 케이스 1. 해시태그 아이디 검증 실패
        생성 실패 케이스 2. RegionId 검증 실패
     */
    @Test
    void 채팅방_생성_테스트_실패_해시태그_아이디가_없는_경우() throws Exception {
        // given

        // 채팅방 리퀘스트
        // 파일
        // 유저 정보
        List<ChatRoomRequest.HashtagRequest> hashtags = new ArrayList<>();
        for (int i = 1; i <= 3; i++) {
            hashtags.add(ChatRoomRequest.HashtagRequest.builder()
                    .id((long) i)
                    .build());
        }

        ChatRoomRequest.Create request = ChatRoomRequest.Create.builder()
                .title("거지방")
                .capability(10)
                .regionId(1L)
                .hashtags(hashtags)
                .build();
        MockMultipartFile image = new MockMultipartFile("image", new byte[0]);
        User user = User.builder()
                .id(2L)
                .build();

        given(hashtagService.existsOneByHashtagId(any()))
                .willReturn(false);
        given(regionFindService.isExistsRegion(any()))
                .willReturn(true);
        // when // then
        assertThatThrownBy(()
                -> chatRoomService.createChatRoom(request, image, user))
                .isInstanceOf(ChatException.class)
                .hasFieldOrPropertyWithValue("errorCode", ChatErrorCode.HASHTAG_NOT_FOUND);
    }

    @Test
    void 채팅방_생성_테스트_실패_지역_아이디가_없는_경우() throws Exception {
        // given

        // 채팅방 리퀘스트
        // 파일
        // 유저 정보
        List<ChatRoomRequest.HashtagRequest> hashtags = new ArrayList<>();
        for (int i = 1; i <= 3; i++) {
            hashtags.add(ChatRoomRequest.HashtagRequest.builder()
                    .id((long) i)
                    .build());
        }

        ChatRoomRequest.Create request = ChatRoomRequest.Create.builder()
                .title("거지방")
                .capability(10)
                .regionId(1L)
                .hashtags(hashtags)
                .build();
        MockMultipartFile image = new MockMultipartFile("image", new byte[0]);
        User user = User.builder()
                .id(2L)
                .build();


        given(regionFindService.isExistsRegion(any()))
                .willReturn(false);
        // when // then
        assertThatThrownBy(()
                -> chatRoomService.createChatRoom(request, image, user))
                .isInstanceOf(ChatException.class)
                .hasFieldOrPropertyWithValue("errorCode", ChatErrorCode.REGION_ID_NOT_MATCHED);
    }



}