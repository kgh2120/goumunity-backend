package com.ssafy.goumunity.domain.chat.infra;

import static com.ssafy.goumunity.domain.chat.infra.chatroom.QUserChatRoomEntity.userChatRoomEntity;
import static org.assertj.core.api.Assertions.assertThat;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.goumunity.domain.chat.infra.chatroom.*;
import com.ssafy.goumunity.domain.user.infra.UserEntity;
import jakarta.persistence.EntityManager;
import java.time.Instant;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class UserChatRoomJpaRepositoryTest {

    @Autowired UserChatRoomJpaRepository userChatRoomJpaRepository;
    @Autowired EntityManager em;

    @Test
    void exist테스틑() throws Exception {
        // given
        long chatRoomId = 1L;
        long userId = 10L;

        // when
        userChatRoomJpaRepository.existsByChatRoom_IdAndUser_Id(chatRoomId, userId);

        // then

    }

    @Test
    void 가장_오래된_아이디_획득하기_테스트() throws Exception {
        // given
        UserEntity user = UserEntity.builder().build();
        em.persist(user);
        ChatRoomEntity chatRoom = ChatRoomEntity.builder().host(user).build();
        em.persist(chatRoom);
        em.persist(
                UserChatRoomEntity.builder()
                        .user(user)
                        .chatRoom(chatRoom)
                        .createdAt(Instant.ofEpochMilli(100))
                        .build());
        for (int i = 0; i < 10; i++) {
            UserEntity user2 = UserEntity.builder().createdAt(Instant.ofEpochMilli(1000 * i)).build();
            em.persist(user2);
            em.persist(
                    UserChatRoomEntity.builder()
                            .user(user2)
                            .chatRoom(chatRoom)
                            .createdAt(Instant.ofEpochMilli(1000 * i))
                            .build());
        }
        // when
        Long id =
                new JPAQueryFactory(em)
                        .select(QUserChatRoomEntity.userChatRoomEntity.user.id)
                        .from(QUserChatRoomEntity.userChatRoomEntity)
                        .where(
                                QUserChatRoomEntity.userChatRoomEntity
                                        .chatRoom
                                        .id
                                        .eq(chatRoom.getId())
                                        .and(userChatRoomEntity.user.id.ne(user.getId())))
                        .orderBy(QUserChatRoomEntity.userChatRoomEntity.createdAt.asc())
                        .offset(0)
                        .limit(1)
                        .fetchFirst();
        // then
        assertThat(id).isEqualTo(2L);
    }
}
