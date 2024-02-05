package com.ssafy.goumunity.domain.chat.infra.hashtag;

import com.ssafy.goumunity.domain.chat.infra.chatroom.ChatRoomEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRoomHashtagJpaRepository extends JpaRepository<ChatRoomHashtagEntity, Long> {

    void deleteAllByChatRoom(ChatRoomEntity chatRoom);
}
