package com.ssafy.goumunity.domain.chat.infra;

import com.ssafy.goumunity.domain.chat.infra.chatroom.UserChatRoomJpaRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class UserChatRoomJpaRepositoryTest {

    @Autowired UserChatRoomJpaRepository userChatRoomJpaRepository;

    @Test
    void exist테스틑() throws Exception {
        // given
        long chatRoomId = 1L;
        long userId = 10L;

        // when
        userChatRoomJpaRepository.existsByChatRoom_IdAndUser_Id(chatRoomId, userId);

        // then

    }
}
