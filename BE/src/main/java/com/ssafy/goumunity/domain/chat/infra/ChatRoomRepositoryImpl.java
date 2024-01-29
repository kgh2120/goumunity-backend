package com.ssafy.goumunity.domain.chat.infra;

import com.ssafy.goumunity.domain.chat.controller.response.ChatRoomSearchResponse;
import com.ssafy.goumunity.domain.chat.domain.ChatRoom;
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
    public void connectChatRoom(Long chatRoomId, Long userId) {
        userChatRoomJpaRepository.save(
                UserChatRoomEntity.create(
                        UserEntity.userEntityOnlyWithId(userId),
                        ChatRoomEntity.chatRoomEntityOnlyWithId(chatRoomId)));
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
}
