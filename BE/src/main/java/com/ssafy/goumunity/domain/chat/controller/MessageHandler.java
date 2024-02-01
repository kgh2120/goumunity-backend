package com.ssafy.goumunity.domain.chat.controller;

import com.ssafy.goumunity.common.util.SliceResponse;
import com.ssafy.goumunity.domain.chat.controller.response.Message;
import com.ssafy.goumunity.domain.chat.service.ChatService;
import com.ssafy.goumunity.domain.user.domain.User;
import lombok.RequiredArgsConstructor;
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

@RequiredArgsConstructor
@RestController
public class MessageHandler {

    private final ChatService chatService;

    @GetMapping("/api/chat-room/{chatroomId}/messages")
    public ResponseEntity<SliceResponse<Message.Response>> findPreviousMessages(
            @PathVariable Long chatroomId,
            @RequestParam Long time,
            Pageable pageable,
            @AuthenticationPrincipal User user) {
        Slice<Message.Response> responses =
                chatService.findPreviousMessage(chatroomId, time, pageable, user);
        return ResponseEntity.ok(SliceResponse.from(responses.getContent(), responses.hasNext()));
    }

    @MessageMapping("/messages/{chatRoomId}")
    @SendTo("/topic/{chatRoomId}")
    public Message.Response sendMessage(
            @DestinationVariable Long chatRoomId,
            @Payload Message.Request message,
            Authentication principal) {
        // chatRoomId와 principal의 관계 검증하기. -> 너무 자주 발생하면 캐싱 고려해야 할 듯.
        User user = (User) principal.getPrincipal();
        chatService.saveChat(chatRoomId, message, user);
        return Message.Response.builder()
                .chatType(message.getChatType())
                .content(message.getContent())
                .nickname(user.getNickname())
                .userId(user.getId())
                .profileImageSrc(user.getImgSrc())
                .build();
    }
}
