package com.ssafy.goumunity.domain.chat.service;

import static com.ssafy.goumunity.domain.chat.exception.ChatErrorCode.*;

import com.ssafy.goumunity.common.exception.CustomException;
import com.ssafy.goumunity.common.exception.GlobalErrorCode;
import com.ssafy.goumunity.domain.chat.controller.request.ChatRoomRequest;
import com.ssafy.goumunity.domain.chat.controller.response.ChatRoomSearchResponse;
import com.ssafy.goumunity.domain.chat.controller.response.MyChatRoomResponse;
import com.ssafy.goumunity.domain.chat.domain.ChatRoom;
import com.ssafy.goumunity.domain.chat.exception.ChatErrorCode;
import com.ssafy.goumunity.domain.chat.exception.ChatException;
import com.ssafy.goumunity.domain.chat.service.port.ChatRoomRepository;
import com.ssafy.goumunity.domain.chat.service.port.ImageUploadService;
import com.ssafy.goumunity.domain.chat.service.port.RegionFindService;
import com.ssafy.goumunity.domain.user.domain.User;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class ChatRoomServiceImpl implements ChatRoomService {

    private final HashtagService hashtagService;
    private final ChatRoomRepository chatRoomRepository;
    private final ImageUploadService imageUploadService;
    private final RegionFindService regionFindService;

    @Override
    public void createChatRoom(ChatRoomRequest.Create dto, MultipartFile multipartFile, User user) {
        // 해시 검색
        verifyCreateChatRoom(dto);
        // 채팅방 생성
        chatRoomRepository.save(
                ChatRoom.create(
                        dto,
                        dto.getRegionId(),
                        user.getId(),
                        imageUploadService.uploadImage(multipartFile),
                        convertHashtagIds(dto)));
    }

    @Override
    public void connectChatRoom(Long chatRoomId, User user) {
        // 채팅방이 있는지 체크
        // 채팅방 유저 수 체크
        // 유저가 이미 가입했는지 체크
        verifyConnectChatRoom(chatRoomId, user);
        chatRoomRepository.connectChatRoom(chatRoomId, user.getId());
    }

    @Override
    public void disconnectChatRoom(Long chatRoomId, User user) {
        ChatRoom chatRoom = verifyDisconnectChatRoom(chatRoomId, user);
        disconnect(chatRoomId, user, chatRoom);
    }

    private void disconnect(Long chatRoomId, User user, ChatRoom chatRoom) {
        if (chatRoom.isHost(user)) {
            disconnectByHost(chatRoom);
        } else {
            chatRoomRepository.disconnectChatRoom(chatRoomId, user.getId());
        }
    }

    private void disconnectByHost(ChatRoom chatRoom) {
        if (chatRoom.isHostAlone()) {
            chatRoomRepository.deleteChatRoom(chatRoom);
        } else {
            throw new ChatException(HOST_CANT_OUT);
        }
    }

    private ChatRoom verifyDisconnectChatRoom(Long chatRoomId, User user) {
        ChatRoom chatRoom =
                chatRoomRepository
                        .findOneByChatRoomId(chatRoomId)
                        .orElseThrow(() -> new ChatException(CHAT_ROOM_NOT_FOUND));
        // user가 채팅방에 속해있는지
        if (!chatRoomRepository.isAlreadyJoinedUser(chatRoomId, user.getId())) {
            throw new CustomException(GlobalErrorCode.FORBIDDEN);
        }
        return chatRoom;
    }

    @Override
    public Slice<ChatRoomSearchResponse> searchChatRoom(
            String keyword, Long time, Pageable pageable) {
        return chatRoomRepository.searchChatRoom(keyword, time, pageable);
    }

    @Override
    public Slice<MyChatRoomResponse> findMyChatRoom(User user, Long time, Pageable pageable) {
        return chatRoomRepository.findMyChatRoom(user, time, pageable);
    }

    @Override
    public boolean verifySendChat(Long chatRoomId, User user) {
        return chatRoomRepository.isExistChatRoom(chatRoomId)
                && !chatRoomRepository.isAlreadyJoinedUser(chatRoomId, user.getId());
    }

    private void verifyConnectChatRoom(Long chatRoomId, User user) {
        ChatRoom chatRoom =
                chatRoomRepository
                        .findOneByChatRoomId(chatRoomId)
                        .orElseThrow(() -> new ChatException(CHAT_ROOM_NOT_FOUND));
        if (!chatRoom.canConnect()) {
            throw new ChatException(CHAT_ROOM_FULL);
        }

        if (chatRoomRepository.isAlreadyJoinedUser(chatRoomId, user.getId())) {
            throw new ChatException(ALREADY_JOINED_CHAT_ROOM);
        }
    }

    private void verifyCreateChatRoom(ChatRoomRequest.Create dto) {
        verifyRegionId(dto);
        verifyHashtagIds(dto);
    }

    private void verifyRegionId(ChatRoomRequest.Create dto) {
        if (!regionFindService.isExistsRegion(dto.getRegionId())) {
            throw new ChatException(ChatErrorCode.REGION_ID_NOT_MATCHED);
        }
    }

    private List<Long> convertHashtagIds(ChatRoomRequest.Create dto) {
        return dto.getHashtags().stream().map(ChatRoomRequest.HashtagRequest::getId).toList();
    }

    private void verifyHashtagIds(ChatRoomRequest.Create dto) {
        List<ChatRoomRequest.HashtagRequest> list =
                dto.getHashtags().stream()
                        .filter(h -> hashtagService.existsOneByHashtagId(h.getId()))
                        .toList();

        if (list.size() != dto.getHashtags().size()) {
            throw new ChatException(ChatErrorCode.HASHTAG_NOT_FOUND);
        }
    }
}
