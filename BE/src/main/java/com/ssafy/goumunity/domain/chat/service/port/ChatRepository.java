package com.ssafy.goumunity.domain.chat.service.port;

import com.ssafy.goumunity.domain.chat.controller.response.Message;
import com.ssafy.goumunity.domain.chat.domain.Chat;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface ChatRepository {
    void save(Chat chat);

    Slice<Message.Response> findPreviousMessage(Long chatroomId, Long time, Pageable pageable);
}
