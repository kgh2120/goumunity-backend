package com.ssafy.goumunity.domain.chat.infra;

import com.ssafy.goumunity.domain.chat.infra.chatroom.ChatRoomJpaRepository;
import com.ssafy.goumunity.domain.chat.infra.chatroom.ChatRoomRepositoryImpl;
import com.ssafy.goumunity.domain.chat.infra.chatroom.UserChatRoomJpaRepository;
import com.ssafy.goumunity.domain.chat.infra.hashtag.ChatRoomHashtagJpaRepository;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ChatRoomRepositoryImplTest {

    @Mock ChatRoomHashtagJpaRepository chatRoomHashtagJpaRepository;
    @Mock UserChatRoomJpaRepository userChatRoomJpaRepository;
    @Mock ChatRoomJpaRepository chatRoomJpaRepository;

    @InjectMocks ChatRoomRepositoryImpl chatRoomRepository;
}
