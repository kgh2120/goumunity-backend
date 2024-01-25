package com.ssafy.goumunity.domain.chat.infra;

import com.ssafy.goumunity.domain.chat.domain.ChatRoom;
import com.ssafy.goumunity.domain.chat.service.port.ChatRoomRepository;
import com.ssafy.goumunity.domain.region.infra.RegionEntity;
import com.ssafy.goumunity.domain.user.infra.UserEntity;
import java.util.List;
import lombok.RequiredArgsConstructor;
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
}
