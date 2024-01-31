package com.ssafy.goumunity.domain.chat.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.time.Instant;
import org.junit.jupiter.api.Test;

class UserChatRoomTest {

    @Test
    void disconnect_테스트() throws Exception {
        // given
        Instant lastAccessTime = Instant.ofEpochMilli(10000L);
        UserChatRoom userChatRoom =
                UserChatRoom.builder()
                        .userChatRoomId(1L)
                        .userId(1L)
                        .chatRoomId(1L)
                        .lastAccessTime(lastAccessTime)
                        .build();
        // when
        userChatRoom.disconnect();
        Instant sut = userChatRoom.getLastAccessTime();
        // then
        assertThat(sut).isAfter(lastAccessTime);
    }
}
