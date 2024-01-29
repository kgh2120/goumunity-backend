package com.ssafy.goumunity.domain.chat.service.port;

import com.ssafy.goumunity.domain.chat.domain.ChatRoom;
import java.util.Optional;

public interface ChatRoomRepository {
    void save(ChatRoom chatRoom);

    Optional<ChatRoom> findOneByChatRoomId(Long chatRoomId);

    boolean isAlreadyJoinedUser(Long chatRoomId, Long userId);

    void connectChatRoom(Long chatRoomId, Long userId);

    void deleteChatRoom(ChatRoom chatRoom);

    void disconnectChatRoom(Long chatRoomId, Long userId);
}
