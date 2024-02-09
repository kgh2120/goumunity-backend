package com.ssafy.goumunity.domain.chat.service;

import static com.ssafy.goumunity.common.exception.GlobalErrorCode.BIND_ERROR;
import static com.ssafy.goumunity.domain.chat.exception.ChatErrorCode.*;

import com.ssafy.goumunity.common.exception.CustomException;
import com.ssafy.goumunity.common.exception.GlobalErrorCode;
import com.ssafy.goumunity.domain.chat.controller.request.ChatRoomRequest;
import com.ssafy.goumunity.domain.chat.controller.response.ChatRoomSearchResponse;
import com.ssafy.goumunity.domain.chat.controller.response.ChatRoomUserResponse;
import com.ssafy.goumunity.domain.chat.controller.response.MyChatRoomResponse;
import com.ssafy.goumunity.domain.chat.domain.ChatRoom;
import com.ssafy.goumunity.domain.chat.domain.UserChatRoom;
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
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class ChatRoomServiceImpl implements ChatRoomService {

    private final HashtagService hashtagService;
    private final ChatRoomRepository chatRoomRepository;
    private final ImageUploadService imageUploadService;
    private final RegionFindService regionFindService;

    @Transactional
    @Override
    public void createChatRoom(ChatRoomRequest.Create dto, MultipartFile multipartFile, User user) {
        // 해시 검색
        verifyRegionId(dto);
        // 채팅방 생성
        chatRoomRepository.save(
                ChatRoom.create(
                        dto,
                        dto.getRegionId(),
                        user.getId(),
                        imageUploadService.uploadImage(multipartFile),
                        convertHashtagIds(dto.getHashtags())));
    }

    @Transactional
    @Override
    public void connectChatRoom(Long chatRoomId, User user) {
        // 채팅방이 있는지 체크
        // 채팅방 유저 수 체크
        // 유저가 이미 가입했는지 체크
        verifyConnectChatRoom(chatRoomId, user);
        chatRoomRepository.connectChatRoom(chatRoomId, user.getId());
    }

    @Transactional
    @Override
    public void exitChatRoom(Long chatRoomId, User user) {
        ChatRoom chatRoom = verifyExitChatRoom(chatRoomId, user);
        exit(chatRoomId, user, chatRoom);
    }

    private void exit(Long chatRoomId, User user, ChatRoom chatRoom) {
        if (chatRoom.isHost(user)) {
            exitByHost(chatRoom);
        } else {
            chatRoomRepository.exitChatRoom(chatRoomId, user.getId());
        }
    }

    private void exitByHost(ChatRoom chatRoom) {
        if (chatRoom.isHostAlone()) {
            chatRoomRepository.deleteChatRoom(chatRoom);
        } else {
            throw new ChatException(HOST_CANT_OUT);
        }
    }

    private ChatRoom verifyExitChatRoom(Long chatRoomId, User user) {
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
        return chatRoomRepository.findMyChatRoom(user.getId(), time, pageable);
    }

    @Transactional
    @Override
    public void disconnectChatRoom(Long chatRoomId, User user) {
        UserChatRoom userChatRoom = verifyDisconnectChatRoom(chatRoomId, user);
        userChatRoom.disconnect();
        chatRoomRepository.disconnectChatRoom(userChatRoom);
    }

    @Override
    public Slice<ChatRoomUserResponse> findChatRoomUsers(
            Long chatRoomId, Pageable pageable, Long time, User user) {
        if (!(chatRoomRepository.isExistChatRoom(chatRoomId)
                & chatRoomRepository.isAlreadyJoinedUser(chatRoomId, user.getId()))) {
            throw new CustomException(GlobalErrorCode.FORBIDDEN);
        }
        return chatRoomRepository.findChatRoomUsers(chatRoomId, pageable, time, user.getId());
    }

    @Transactional
    @Override
    public void modifyChatRoom(
            Long chatRoomId, User user, ChatRoomRequest.Modify dto, MultipartFile multipartFile) {

        if ((dto.getImage() == null && (multipartFile == null || multipartFile.isEmpty()))
                || StringUtils.hasText(dto.getImage())
                        && (multipartFile != null && !multipartFile.isEmpty())) {
            throw new CustomException(BIND_ERROR);
        }

        ChatRoom chatRoom =
                chatRoomRepository
                        .findOneByChatRoomId(chatRoomId)
                        .orElseThrow(() -> new ChatException(CHAT_ROOM_NOT_FOUND));

        if (!chatRoom.isHost(user)) {
            throw new CustomException(GlobalErrorCode.FORBIDDEN);
        }

        String imageSource = dto.getImage();
        if (!StringUtils.hasText(dto.getImage())) {
            imageSource = imageUploadService.uploadImage(multipartFile);
        }

        chatRoom.modify(dto, convertHashtagIds(dto.getHashtagRequests()), imageSource);

        chatRoomRepository.update(chatRoom);
    }

    @Override
    public MyChatRoomResponse findOneMyChatRoomByChatRoomId(Long chatRoomId, User user) {
        if (!chatRoomRepository.isExistChatRoom(chatRoomId)) {
            throw new ChatException(CHAT_ROOM_NOT_FOUND);
        }
        return chatRoomRepository
                .findOneMyChatRoomByChatRoomId(chatRoomId, user.getId())
                .orElseThrow(() -> new CustomException(GlobalErrorCode.FORBIDDEN));
    }

    @Override
    public void clearChatRoom(Long userId) {
        List<ChatRoom> chatRooms = chatRoomRepository.findAllMyChatRoomWhereIAmHost(userId);
        chatRooms.forEach(
                chatRoom -> {
                    Long oldestUserId = chatRoomRepository.getOldestUserInChatRoom(chatRoom.getId(), userId);
                    if (oldestUserId == null) {
                        chatRoomRepository.deleteChatRoom(chatRoom);
                    } else {
                        chatRoom.modifyHost(oldestUserId);
                        chatRoomRepository.update(chatRoom);
                    }
                });
        chatRoomRepository.deleteAllUserChatRoomByUserId(userId);
    }

    private UserChatRoom verifyDisconnectChatRoom(Long chatRoomId, User user) {
        if (!chatRoomRepository.isExistChatRoom(chatRoomId))
            throw new ChatException(CHAT_ROOM_NOT_FOUND);

        return chatRoomRepository
                .findOneUserChatRoomByUserIdAndChatRoomId(chatRoomId, user.getId())
                .orElseThrow(() -> new CustomException(GlobalErrorCode.FORBIDDEN));
    }

    @Override
    public boolean verifyAccessChat(Long chatRoomId, User user) {
        return chatRoomRepository.isExistChatRoom(chatRoomId)
                && chatRoomRepository.isAlreadyJoinedUser(chatRoomId, user.getId());
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

    private void verifyRegionId(ChatRoomRequest.Create dto) {
        if (!regionFindService.isExistsRegion(dto.getRegionId())) {
            throw new ChatException(ChatErrorCode.REGION_ID_NOT_MATCHED);
        }
    }

    private List<Long> convertHashtagIds(List<ChatRoomRequest.HashtagRequest> dto) {
        return dto.stream()
                .map(hashtagRequest -> hashtagService.getHashtag(hashtagRequest.getName()).getId())
                .toList();
    }
}
