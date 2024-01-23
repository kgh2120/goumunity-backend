package com.ssafy.goumunity.domain.chat.domain;

import com.ssafy.goumunity.domain.chat.infra.ChatType;
import com.ssafy.goumunity.domain.user.domain.User;
import jakarta.persistence.*;
import java.time.Instant;
import lombok.*;

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
    private ChatRoom chatRoom;
    private User user;
}
