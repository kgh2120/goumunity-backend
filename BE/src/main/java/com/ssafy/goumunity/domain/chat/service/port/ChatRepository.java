package com.ssafy.goumunity.domain.chat.service.port;

import com.ssafy.goumunity.domain.chat.controller.response.MessageResponse;
import com.ssafy.goumunity.domain.chat.domain.Chat;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface ChatRepository {
    void save(Chat chat);

    Slice<MessageResponse.Previous> findPreviousMessage(
            Long chatroomId, Long time, Pageable pageable, Long userId);
}
