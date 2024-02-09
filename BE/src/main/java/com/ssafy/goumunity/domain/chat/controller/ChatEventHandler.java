package com.ssafy.goumunity.domain.chat.controller;

import com.ssafy.goumunity.domain.chat.service.ChatRoomService;
import com.ssafy.goumunity.domain.user.event.UserDeletedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionalEventListener;

@RequiredArgsConstructor
@Component
public class ChatEventHandler {

    private final ChatRoomService chatRoomService;

    @Async
    @TransactionalEventListener
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @EventListener
    public void handleUserDeletedEvent(UserDeletedEvent event) {
        chatRoomService.clearChatRoom(event.getUserId());
    }
}
