package com.ssafy.goumunity.domain.chat.service;

import com.ssafy.goumunity.domain.chat.controller.response.Message;
import com.ssafy.goumunity.domain.user.domain.User;

public interface ChatService {
    void saveChat(Long chatRoomId, Message.Request message, User user);
}
