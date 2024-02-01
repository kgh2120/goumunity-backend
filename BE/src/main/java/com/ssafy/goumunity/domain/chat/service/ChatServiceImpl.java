package com.ssafy.goumunity.domain.chat.service;

import com.ssafy.goumunity.domain.chat.controller.response.Message;
import com.ssafy.goumunity.domain.chat.domain.Chat;
import com.ssafy.goumunity.domain.chat.exception.ChatErrorCode;
import com.ssafy.goumunity.domain.chat.exception.ChatException;
import com.ssafy.goumunity.domain.chat.service.port.ChatRepository;
import com.ssafy.goumunity.domain.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ChatServiceImpl implements ChatService {

    private final ChatRepository chatRepository;
    private final ChatRoomService chatRoomService;

    @Override
    public void saveChat(Long chatRoomId, Message.Request message, User user) {
        if (!chatRoomService.verifyAccessChat(chatRoomId, user)) {
            throw new ChatException(ChatErrorCode.CANT_ACCESS_MESSAGE);
        }
        chatRepository.save(Chat.create(message, chatRoomId, user));
    }

    @Override
    public Slice<Message.Response> findPreviousMessage(
            Long chatroomId, Long time, Pageable pageable, User user) {
        if (!chatRoomService.verifyAccessChat(chatroomId, user)) {
            throw new ChatException(ChatErrorCode.CANT_ACCESS_MESSAGE);
        }
        return chatRepository.findPreviousMessage(chatroomId, time, pageable);
    }
}
