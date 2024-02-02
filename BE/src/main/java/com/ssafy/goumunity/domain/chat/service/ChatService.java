package com.ssafy.goumunity.domain.chat.service;

import com.ssafy.goumunity.domain.chat.controller.request.MessageRequest;
import com.ssafy.goumunity.domain.chat.controller.response.MessageResponse;
import com.ssafy.goumunity.domain.user.domain.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface ChatService {
    void saveChat(Long chatRoomId, MessageRequest.Create message, User user);

    Slice<MessageResponse.Previous> findPreviousMessage(
            Long chatroomId, Long time, Pageable pageable, User user);
}
