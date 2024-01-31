package com.ssafy.goumunity.domain.chat.domain;

import com.ssafy.goumunity.domain.chat.controller.response.Message;
import com.ssafy.goumunity.domain.chat.infra.ChatType;
import com.ssafy.goumunity.domain.user.domain.User;
import jakarta.persistence.*;
import java.time.Instant;
import lombok.*;

@EqualsAndHashCode(exclude = {"createdAt", "updatedAt"})
@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class Chat {
    private Long id;
    private String content;
    private ChatType chatType;
    private Instant createdAt;
    private Instant updatedAt;
    private Long chatRoomId;
    private Long userId;

    public static Chat create(Message.Request messageRequest, Long chatRoomId, User user) {
        return Chat.builder()
                .content(messageRequest.getContent())
                .chatType(messageRequest.getChatType())
                .createdAt(Instant.now())
                .userId(user.getId())
                .chatRoomId(chatRoomId)
                .build();
    }
}
