package com.ssafy.goumunity.domain.chat.infra;

import static org.junit.jupiter.api.Assertions.*;

import com.ssafy.goumunity.domain.chat.controller.response.ChatRoomUserResponse;
import com.ssafy.goumunity.domain.user.infra.UserEntity;
import jakarta.persistence.EntityManager;
import java.time.Instant;
import java.util.List;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@SpringBootTest
class ChatRoomQueryDslRepositoryTest {

    @Autowired EntityManager em;

    @Autowired ChatRoomQueryDslRepository chatRoomQueryDslRepository;

    @Test
    void 거지방_회원_목록_조회_성공() throws Exception {
        // given
        ChatRoomEntity chatRoom = ChatRoomEntity.builder().title("거지들 모여러").build();

        long time = 1000L;
        UserEntity currentUser =
                UserEntity.builder().nickname("왕초").createdAt(Instant.ofEpochMilli(time)).build();

        em.persist(chatRoom);
        em.persist(currentUser);
        em.persist(UserChatRoomEntity.create(currentUser, chatRoom));
        em.flush();

        for (int i = 0; i < 20; i++) {
            UserEntity u =
                    UserEntity.builder()
                            .nickname("거지" + i)
                            .createdAt(Instant.ofEpochMilli(time + 10000 * i))
                            .build();
            em.persist(u);
            em.persist(UserChatRoomEntity.create(u, chatRoom));
        }
        // when
        Slice<ChatRoomUserResponse> sut =
                chatRoomQueryDslRepository.findChatRoomUsers(
                        chatRoom.getId(), PageRequest.of(0, 10), 1000000000L, currentUser.getId());
        List<ChatRoomUserResponse> content = sut.getContent();
        // then

        SoftAssertions sa = new SoftAssertions();

        sa.assertThat(sut.hasNext()).isTrue();
        sa.assertThat(content.size()).isSameAs(10);
        sa.assertThat(content.get(0).getIsCurrentUser()).isTrue();
        for (int i = 1; i < 10; i++) {
            sa.assertThat(content.get(i).getIsCurrentUser()).isFalse();
        }
        sa.assertAll();
    }
}
