package com.ssafy.goumunity.domain.chat.infra.chatroom;

import com.ssafy.goumunity.domain.chat.controller.response.ChatRoomSearchResponse;
import com.ssafy.goumunity.domain.chat.controller.response.ChatRoomUserResponse;
import com.ssafy.goumunity.domain.chat.controller.response.MyChatRoomResponse;
import com.ssafy.goumunity.domain.chat.domain.ChatRoom;
import com.ssafy.goumunity.domain.chat.domain.UserChatRoom;
import com.ssafy.goumunity.domain.chat.infra.hashtag.ChatRoomHashtagEntity;
import com.ssafy.goumunity.domain.chat.infra.hashtag.ChatRoomHashtagJpaRepository;
import com.ssafy.goumunity.domain.chat.infra.hashtag.HashtagEntity;
import com.ssafy.goumunity.domain.chat.service.port.ChatRoomRepository;
import com.ssafy.goumunity.domain.region.infra.RegionEntity;
import com.ssafy.goumunity.domain.user.infra.UserEntity;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class ChatRoomRepositoryImpl implements ChatRoomRepository {

    private final ChatRoomJpaRepository chatRoomJpaRepository;
    private final ChatRoomHashtagJpaRepository chatRoomHashtagJpaRepository;
    private final UserChatRoomJpaRepository userChatRoomJpaRepository;
    private final ChatRoomQueryDslRepository chatRoomQueryDslRepository;

    @Override
    public void save(ChatRoom chatRoom) {
        ChatRoomEntity chatRoomEntity = ChatRoomEntity.from(chatRoom);
        UserEntity userEntity = UserEntity.userEntityOnlyWithId(chatRoom.getUserId());
        chatRoomEntity.associatedWithUserEntity(userEntity);
        chatRoomEntity.associatedWithRegionEntity(
                RegionEntity.regionEntityOnlyWithId(chatRoom.getRegionId()));
        chatRoomJpaRepository.save(chatRoomEntity);

        List<HashtagEntity> hashtagEntities =
                chatRoom.getHashtagsIds().stream().map(HashtagEntity::hashtagEntityOnlyWithId).toList();

        for (int i = 0; i < hashtagEntities.size(); i++) {
            chatRoomHashtagJpaRepository.save(
                    ChatRoomHashtagEntity.create(hashtagEntities.get(i), chatRoomEntity, i + 1));
        }
        userChatRoomJpaRepository.save(UserChatRoomEntity.create(userEntity, chatRoomEntity));
    }

    @Override
    public Optional<ChatRoom> findOneByChatRoomId(Long chatRoomId) {
        return chatRoomJpaRepository.findById(chatRoomId).map(ChatRoomEntity::to);
    }

    @Override
    public boolean isAlreadyJoinedUser(Long chatRoomId, Long userId) {
        return userChatRoomJpaRepository.existsByChatRoom_IdAndUser_Id(chatRoomId, userId);
    }

    @Override
    public boolean isExistChatRoom(Long chatRoomId) {
        return chatRoomJpaRepository.existsById(chatRoomId);
    }

    @Override
    public void connectChatRoom(Long chatRoomId, Long userId) {
        userChatRoomJpaRepository.save(
                UserChatRoomEntity.create(
                        UserEntity.userEntityOnlyWithId(userId),
                        ChatRoomEntity.chatRoomEntityOnlyWithId(chatRoomId)));
    }

    @Override
    public void deleteChatRoom(ChatRoom chatRoom) {
        chatRoomJpaRepository.deleteById(chatRoom.getId());
    }

    @Override
    public void exitChatRoom(Long chatRoomId, Long userId) {
        userChatRoomJpaRepository.deleteOneByChatRoom_IdAndUser_Id(chatRoomId, userId);
    }

    @Override
    public Slice<ChatRoomSearchResponse> searchChatRoom(
            String keyword, Long time, Pageable pageable) {
        Slice<ChatRoomEntity> slice =
                chatRoomJpaRepository.searchChatRoom(keyword, Instant.ofEpochMilli(time), pageable);
        return new SliceImpl<>(
                slice.getContent().stream().map(ChatRoomSearchResponse::from).toList(),
                pageable,
                slice.hasNext());
    }

    @Override
    public Slice<MyChatRoomResponse> findMyChatRoom(Long userId, Long time, Pageable pageable) {
        return chatRoomQueryDslRepository.findMyChatRoom(userId, time, pageable);
    }

    @Override
    public Optional<UserChatRoom> findOneUserChatRoomByUserIdAndChatRoomId(
            Long userId, Long chatRoomId) {
        return userChatRoomJpaRepository
                .findOnyByUser_IdAndChatRoom_Id(userId, chatRoomId)
                .map(UserChatRoomEntity::to);
    }

    @Override
    public void disconnectChatRoom(UserChatRoom userChatRoom) {
        userChatRoomJpaRepository.save(UserChatRoomEntity.from(userChatRoom));
    }

    @Override
    public Slice<ChatRoomUserResponse> findChatRoomUsers(
            Long chatRoomId, Pageable pageable, Long time, Long userId) {
        return chatRoomQueryDslRepository.findChatRoomUsers(chatRoomId, pageable, time, userId);
    }
}
