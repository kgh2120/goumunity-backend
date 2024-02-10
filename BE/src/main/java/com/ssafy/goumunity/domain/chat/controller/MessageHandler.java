package com.ssafy.goumunity.domain.chat.controller;

import com.ssafy.goumunity.common.util.SliceResponse;
import com.ssafy.goumunity.domain.chat.controller.request.MessageRequest;
import com.ssafy.goumunity.domain.chat.controller.response.MessageResponse;
import com.ssafy.goumunity.domain.chat.service.ChatService;
import com.ssafy.goumunity.domain.user.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
public class MessageHandler {

    private final ChatService chatService;

    @GetMapping("/api/chat-room/{chatroomId}/messages")
    public ResponseEntity<SliceResponse<MessageResponse.Previous>> findPreviousMessages(
            @PathVariable Long chatroomId,
            @RequestParam Long time,
            Pageable pageable,
            @AuthenticationPrincipal User user) {
        Slice<MessageResponse.Previous> responses =
                chatService.findPreviousMessage(chatroomId, time, pageable, user);
        return ResponseEntity.ok(SliceResponse.from(responses.getContent(), responses.hasNext()));
    }

    @MessageMapping("/messages/{chatRoomId}")
    @SendTo("/topic/{chatRoomId}")
    public MessageResponse.Live sendMessage(
            @DestinationVariable Long chatRoomId,
            @Payload MessageRequest.Create message,
            Authentication principal) {
        User user = (User) principal.getPrincipal();
        chatService.saveChat(chatRoomId, message, user);
        return MessageResponse.Live.create(message, user);
    }
}
