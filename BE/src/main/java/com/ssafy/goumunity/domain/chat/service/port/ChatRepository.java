package com.ssafy.goumunity.domain.chat.service.port;

import com.ssafy.goumunity.domain.chat.domain.Chat;

public interface ChatRepository {
    void save(Chat chat);
}
