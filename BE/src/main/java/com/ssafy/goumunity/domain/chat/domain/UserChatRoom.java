package com.ssafy.goumunity.domain.chat.domain;

import java.time.Instant;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"lastAccessTime"})
public class UserChatRoom {

    private Long userChatRoomId;
    private Long userId;
    private Long chatRoomId;
    private Instant lastAccessTime;

    public void disconnect() {
        lastAccessTime = Instant.now();
    }
}
