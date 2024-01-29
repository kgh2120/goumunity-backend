package com.ssafy.goumunity.domain.chat.infra;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import com.ssafy.goumunity.domain.chat.domain.ChatRoom;
import com.ssafy.goumunity.domain.user.infra.UserEntity;
import jakarta.persistence.EntityManager;
import java.time.Instant;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class ChatRoomJpaRepositoryTest {

    @Autowired ChatRoomJpaRepository chatRoomJpaRepository;

    @Autowired EntityManager em;

    @Test
    void 채팅방_조회_시_Count쿼리_체크() throws Exception {
        // given

        ChatRoomEntity chatRoom =
                ChatRoomEntity.builder().title("해윙").createdAt(Instant.now()).capability(10).build();

        em.persist(chatRoom);

        for (int i = 0; i < 5; i++) {
            UserEntity user = UserEntity.builder().build();
            em.persist(user);
            em.persist(UserChatRoomEntity.create(user, chatRoom));
        }
        em.flush();
        em.clear();
        // when
        ChatRoomEntity chatRoomEntity = chatRoomJpaRepository.findById(chatRoom.getId()).get();
        ChatRoom sut = chatRoomEntity.to();
        // then
        assertThat(sut.getTitle()).isEqualTo("해윙");
        assertThat(sut.getCapability()).isSameAs(10);
        assertThat(sut.getCurrentUserCount()).isSameAs(5);
    }
}
