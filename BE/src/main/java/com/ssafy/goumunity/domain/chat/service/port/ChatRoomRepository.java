package com.ssafy.goumunity.domain.chat.service.port;

import com.ssafy.goumunity.domain.chat.domain.ChatRoom;

public interface ChatRoomRepository {
    void save(ChatRoom chatRoom);
}
