package com.ssafy.goumunity.domain.chat.service;

import com.ssafy.goumunity.domain.chat.controller.response.Message;
import com.ssafy.goumunity.domain.user.domain.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface ChatService {
    void saveChat(Long chatRoomId, Message.Request message, User user);

    Slice<Message.Response> findPreviousMessage(
            Long chatroomId, Long time, Pageable pageable, User user);
}
