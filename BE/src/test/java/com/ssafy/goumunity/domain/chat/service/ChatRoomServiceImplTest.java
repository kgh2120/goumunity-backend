package com.ssafy.goumunity.domain.chat.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;

import com.ssafy.goumunity.common.exception.CustomException;
import com.ssafy.goumunity.common.exception.GlobalErrorCode;
import com.ssafy.goumunity.domain.chat.controller.request.ChatRoomRequest;
import com.ssafy.goumunity.domain.chat.controller.response.ChatRoomUserResponse;
import com.ssafy.goumunity.domain.chat.domain.ChatRoom;
import com.ssafy.goumunity.domain.chat.domain.UserChatRoom;
import com.ssafy.goumunity.domain.chat.exception.ChatErrorCode;
import com.ssafy.goumunity.domain.chat.exception.ChatException;
import com.ssafy.goumunity.domain.chat.service.port.ChatRoomRepository;
import com.ssafy.goumunity.domain.chat.service.port.ImageUploadService;
import com.ssafy.goumunity.domain.chat.service.port.RegionFindService;
import com.ssafy.goumunity.domain.user.domain.User;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.mock.web.MockMultipartFile;

@ExtendWith(MockitoExtension.class)
class ChatRoomServiceImplTest {

    @Mock HashtagService hashtagService;
    @Mock ChatRoomRepository chatRoomRepository;
    @Mock ImageUploadService uploadService;
    @Mock RegionFindService regionFindService;
    @InjectMocks ChatRoomServiceImpl chatRoomService;

    @Test
    void 채팅방_생성_테스트_성공() throws Exception {
        // given

        // 채팅방 리퀘스트
        // 파일
        // 유저 정보
        List<ChatRoomRequest.HashtagRequest> hashtags = new ArrayList<>();
        for (int i = 1; i <= 3; i++) {
            hashtags.add(ChatRoomRequest.HashtagRequest.builder().id((long) i).build());
        }

        ChatRoomRequest.Create request =
                ChatRoomRequest.Create.builder()
                        .title("거지방")
                        .capability(10)
                        .regionId(1L)
                        .hashtags(hashtags)
                        .build();
        MockMultipartFile image = new MockMultipartFile("image", new byte[0]);
        User user = User.builder().id(2L).build();

        given(hashtagService.existsOneByHashtagId(any())).willReturn(true);
        given(uploadService.uploadImage(any())).willReturn("image");
        given(regionFindService.isExistsRegion(any())).willReturn(true);
        // when

        assertThatNoException().isThrownBy(() -> chatRoomService.createChatRoom(request, image, user));

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
            hashtags.add(ChatRoomRequest.HashtagRequest.builder().id((long) i).build());
        }

        ChatRoomRequest.Create request =
                ChatRoomRequest.Create.builder()
                        .title("거지방")
                        .capability(10)
                        .regionId(1L)
                        .hashtags(hashtags)
                        .build();
        MockMultipartFile image = new MockMultipartFile("image", new byte[0]);
        User user = User.builder().id(2L).build();

        given(hashtagService.existsOneByHashtagId(any())).willReturn(false);
        given(regionFindService.isExistsRegion(any())).willReturn(true);
        // when // then
        assertThatThrownBy(() -> chatRoomService.createChatRoom(request, image, user))
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
            hashtags.add(ChatRoomRequest.HashtagRequest.builder().id((long) i).build());
        }

        ChatRoomRequest.Create request =
                ChatRoomRequest.Create.builder()
                        .title("거지방")
                        .capability(10)
                        .regionId(1L)
                        .hashtags(hashtags)
                        .build();
        MockMultipartFile image = new MockMultipartFile("image", new byte[0]);
        User user = User.builder().id(2L).build();

        given(regionFindService.isExistsRegion(any())).willReturn(false);
        // when // then
        assertThatThrownBy(() -> chatRoomService.createChatRoom(request, image, user))
                .isInstanceOf(ChatException.class)
                .hasFieldOrPropertyWithValue("errorCode", ChatErrorCode.REGION_ID_NOT_MATCHED);
    }

    @Test
    void 채팅방_추가_테스트_성공() throws Exception {
        // given
        given(chatRoomRepository.findOneByChatRoomId(any()))
                .willReturn(
                        Optional.ofNullable(ChatRoom.builder().capability(10).currentUserCount(2).build()));

        given(chatRoomRepository.isAlreadyJoinedUser(any(), anyLong())).willReturn(false);

        Long chatRoomId = 1L;
        User user = User.builder().id(10L).build();
        // when //then
        assertThatNoException().isThrownBy(() -> chatRoomService.connectChatRoom(chatRoomId, user));
    }

    @Test
    void 채팅방_추가_테스트_실패_채팅방이_가득_찬_경우() throws Exception {
        // given
        given(chatRoomRepository.findOneByChatRoomId(any()))
                .willReturn(
                        Optional.ofNullable(ChatRoom.builder().capability(10).currentUserCount(10).build()));

        Long chatRoomId = 1L;
        User user = User.builder().id(10L).build();
        // when //then
        assertThatThrownBy(() -> chatRoomService.connectChatRoom(chatRoomId, user))
                .isInstanceOf(ChatException.class)
                .hasFieldOrPropertyWithValue("errorCode", ChatErrorCode.CHAT_ROOM_FULL);
    }

    @Test
    void 채팅방_추가_테스트_실패_채팅방이_존재하지_않는_경우() throws Exception {
        // given
        given(chatRoomRepository.findOneByChatRoomId(any())).willReturn(Optional.empty());

        Long chatRoomId = 1L;
        User user = User.builder().id(10L).build();
        // when //then
        assertThatThrownBy(() -> chatRoomService.connectChatRoom(chatRoomId, user))
                .isInstanceOf(ChatException.class)
                .hasFieldOrPropertyWithValue("errorCode", ChatErrorCode.CHAT_ROOM_NOT_FOUND);
    }

    @Test
    void 채팅방_추가_테스트_실패_유저가_이미_가입한_경우() throws Exception {
        // given
        given(chatRoomRepository.findOneByChatRoomId(any()))
                .willReturn(
                        Optional.ofNullable(ChatRoom.builder().capability(10).currentUserCount(2).build()));

        given(chatRoomRepository.isAlreadyJoinedUser(any(), anyLong())).willReturn(true);

        Long chatRoomId = 1L;
        User user = User.builder().id(10L).build();
        // when //then
        assertThatThrownBy(() -> chatRoomService.connectChatRoom(chatRoomId, user))
                .isInstanceOf(ChatException.class)
                .hasFieldOrPropertyWithValue("errorCode", ChatErrorCode.ALREADY_JOINED_CHAT_ROOM);
    }

    @Test
    void 거지방_나가기_테스트_성공_일반_유저_케이스() throws Exception {
        // given

        Long chatRoomId = 1L;
        User user = User.builder().id(2L).build();
        given(chatRoomRepository.findOneByChatRoomId(any()))
                .willReturn(
                        Optional.ofNullable(ChatRoom.builder().id(1L).currentUserCount(5).userId(5L).build()));
        given(chatRoomRepository.isAlreadyJoinedUser(any(), anyLong())).willReturn(true);
        // when
        chatRoomService.exitChatRoom(chatRoomId, user);
        // then
        verify(chatRoomRepository).exitChatRoom(chatRoomId, user.getId());
    }

    @Test
    void 거지방_나가기_테스트_성공_방장_케이스() throws Exception {
        // given

        Long chatRoomId = 1L;
        User user = User.builder().id(2L).build();
        ChatRoom chatRoom = ChatRoom.builder().id(1L).currentUserCount(1).userId(2L).build();
        given(chatRoomRepository.findOneByChatRoomId(any())).willReturn(Optional.ofNullable(chatRoom));
        given(chatRoomRepository.isAlreadyJoinedUser(any(), anyLong())).willReturn(true);
        // when
        chatRoomService.exitChatRoom(chatRoomId, user);
        // then
        verify(chatRoomRepository).deleteChatRoom(chatRoom);
    }

    @Test
    void 거지방_나가기_테스트_실패_채팅방이_존재하지_않는_케이스() throws Exception {
        // given

        Long chatRoomId = 1L;
        User user = User.builder().id(2L).build();
        given(chatRoomRepository.findOneByChatRoomId(any())).willReturn(Optional.empty());
        // when // then
        assertThatThrownBy(() -> chatRoomService.exitChatRoom(chatRoomId, user))
                .isInstanceOf(ChatException.class)
                .hasFieldOrPropertyWithValue("errorCode", ChatErrorCode.CHAT_ROOM_NOT_FOUND);
    }

    @Test
    void 거지방_나가기_테스트_실패_채팅방에_속하지_않는_경우() throws Exception {
        // given
        Long chatRoomId = 1L;
        User user = User.builder().id(2L).build();
        ChatRoom chatRoom = ChatRoom.builder().id(1L).currentUserCount(1).userId(2L).build();
        given(chatRoomRepository.findOneByChatRoomId(any())).willReturn(Optional.ofNullable(chatRoom));
        given(chatRoomRepository.isAlreadyJoinedUser(any(), anyLong())).willReturn(false);
        // when // then
        assertThatThrownBy(() -> chatRoomService.exitChatRoom(chatRoomId, user))
                .isInstanceOf(CustomException.class)
                .hasFieldOrPropertyWithValue("errorCode", GlobalErrorCode.FORBIDDEN);
    }

    @Test
    void 거지방_나가기_테스트_실패_회원이_방장인데_사람이_남아있는_경우() throws Exception {
        // given
        Long chatRoomId = 1L;
        User user = User.builder().id(2L).build();
        ChatRoom chatRoom = ChatRoom.builder().id(1L).currentUserCount(5).userId(2L).build();
        given(chatRoomRepository.findOneByChatRoomId(any())).willReturn(Optional.ofNullable(chatRoom));
        given(chatRoomRepository.isAlreadyJoinedUser(any(), anyLong())).willReturn(true);
        // when // then
        assertThatThrownBy(() -> chatRoomService.exitChatRoom(chatRoomId, user))
                .isInstanceOf(ChatException.class)
                .hasFieldOrPropertyWithValue("errorCode", ChatErrorCode.HOST_CANT_OUT);
    }

    @Test
    void 거지방_접속_종료_테스트_성공() throws Exception {
        // given
        Long chatRoomId = 1L;
        User user = User.builder().id(1L).build();
        UserChatRoom userChatRoom =
                UserChatRoom.builder()
                        .userChatRoomId(1L)
                        .chatRoomId(1L)
                        .userId(1L)
                        .lastAccessTime(Instant.ofEpochMilli(1000L))
                        .build();
        given(chatRoomRepository.isExistChatRoom(any())).willReturn(true);
        given(chatRoomRepository.findOneUserChatRoomByUserIdAndChatRoomId(any(), any()))
                .willReturn(Optional.of(userChatRoom));
        // when
        chatRoomService.disconnectChatRoom(chatRoomId, user);
        // then
        verify(chatRoomRepository).disconnectChatRoom(userChatRoom);
    }

    @Test
    void 거지방_접속_종료_테스트_실패_채팅방이_존재하지_않는_경우() throws Exception {
        // given
        Long chatRoomId = 1L;
        User user = User.builder().id(1L).build();
        given(chatRoomRepository.isExistChatRoom(any())).willReturn(false);

        // when // then
        assertThatThrownBy(() -> chatRoomService.disconnectChatRoom(chatRoomId, user))
                .isInstanceOf(ChatException.class)
                .hasFieldOrPropertyWithValue("errorCode", ChatErrorCode.CHAT_ROOM_NOT_FOUND);
    }

    @Test
    void 거지방_접속_종료_테스트_실패_유저가_채팅방에_속해있지_않는_경우() throws Exception {
        // given
        Long chatRoomId = 1L;
        User user = User.builder().id(1L).build();
        given(chatRoomRepository.isExistChatRoom(any())).willReturn(true);
        given(chatRoomRepository.findOneUserChatRoomByUserIdAndChatRoomId(any(), any()))
                .willReturn(Optional.empty());
        // when
        assertThatThrownBy(() -> chatRoomService.disconnectChatRoom(chatRoomId, user))
                .isInstanceOf(CustomException.class)
                .hasFieldOrPropertyWithValue("errorCode", GlobalErrorCode.FORBIDDEN);
        // then

    }

    @Test
    void 거지방_회원조회_테스트_성공() throws Exception {
        // given
        Long chatRoomId = 1L;
        Long time = 1000000000L;
        int page = 0;
        int size = 12;
        User user = User.builder().id(1L).build();

        given(chatRoomRepository.isExistChatRoom(any())).willReturn(true);

        given(chatRoomRepository.isAlreadyJoinedUser(any(), any())).willReturn(true);

        PageRequest pageable = PageRequest.of(page, size);
        given(chatRoomRepository.findChatRoomUsers(any(), any(), any(), any()))
                .willReturn(
                        new SliceImpl<>(
                                List.of(
                                        new ChatRoomUserResponse(
                                                user.getId(), user.getNickname(), user.getImgSrc(), true)),
                                pageable,
                                false));
        // when
        Slice<ChatRoomUserResponse> sut =
                chatRoomService.findChatRoomUsers(chatRoomId, pageable, time, user);
        List<ChatRoomUserResponse> res = sut.getContent();
        ChatRoomUserResponse chatRoomUserResponse = res.get(0);
        // then

        SoftAssertions sa = new SoftAssertions();

        sa.assertThat(sut.hasNext()).isFalse();
        sa.assertThat(res.size()).isOne();

        sa.assertThat(chatRoomUserResponse.getUserId()).isEqualTo(user.getId());
        sa.assertThat(chatRoomUserResponse.getNickname()).isEqualTo(user.getNickname());
        sa.assertThat(chatRoomUserResponse.getProfileImageSrc()).isEqualTo(user.getImgSrc());
        sa.assertThat(chatRoomUserResponse.getIsCurrentUser()).isTrue();
        sa.assertAll();
    }

    @Test
    void 거지방_회원조회_테스트_실패_채팅방이_존재하지_않는_경우() throws Exception {
        // given
        Long chatRoomId = 1L;
        Long time = 1000000000L;
        int page = 0;
        int size = 12;
        User user = User.builder().id(1L).build();

        given(chatRoomRepository.isExistChatRoom(any())).willReturn(false);

        //        given(chatRoomRepository.isAlreadyJoinedUser(any(), any()))
        //                .willReturn(true);

        PageRequest pageable = PageRequest.of(page, size);

        // when
        assertThatThrownBy(() -> chatRoomService.findChatRoomUsers(chatRoomId, pageable, time, user))
                .isInstanceOf(CustomException.class)
                .hasFieldOrPropertyWithValue("errorCode", GlobalErrorCode.FORBIDDEN);
        // then

    }

    @Test
    void 거지방_회원조회_테스트_실패_회원이_채팅방에_참가하지_않는_경우() throws Exception {
        // given
        Long chatRoomId = 1L;
        Long time = 1000000000L;
        int page = 0;
        int size = 12;
        User user = User.builder().id(1L).build();

        given(chatRoomRepository.isExistChatRoom(any())).willReturn(true);

        given(chatRoomRepository.isAlreadyJoinedUser(any(), any())).willReturn(false);

        PageRequest pageable = PageRequest.of(page, size);
        // when
        assertThatThrownBy(() -> chatRoomService.findChatRoomUsers(chatRoomId, pageable, time, user))
                .isInstanceOf(CustomException.class)
                .hasFieldOrPropertyWithValue("errorCode", GlobalErrorCode.FORBIDDEN);
        // then

    }
}
