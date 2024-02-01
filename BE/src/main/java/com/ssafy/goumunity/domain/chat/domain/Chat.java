package com.ssafy.goumunity.domain.chat.domain;

import com.ssafy.goumunity.domain.chat.controller.response.Message;
import com.ssafy.goumunity.domain.chat.infra.ChatType;
import java.time.Instant;
import lombok.*;

@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode(exclude = {"createdAt", "updatedAt"})
public class Chat {
    private Long id;
    private String content;
    private ChatType chatType;
    private Instant createdAt;
    private Instant updatedAt;
    private Long chatRoomId;
    private Long userId;

    public static Chat create(Message.Request messageRequest, Long chatRoomId, Long userId) {
        return Chat.builder()
                .content(messageRequest.getContent())
                .chatType(messageRequest.getChatType())
                .createdAt(Instant.now())
                .userId(userId)
                .chatRoomId(chatRoomId)
                .build();
    }
}
