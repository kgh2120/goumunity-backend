package com.ssafy.goumunity.domain.chat.infra;

import com.ssafy.goumunity.domain.chat.controller.response.MessageResponse;
import com.ssafy.goumunity.domain.chat.infra.chat.ChatEntity;
import com.ssafy.goumunity.domain.chat.infra.chat.ChatQueryDslRepository;
import com.ssafy.goumunity.domain.chat.infra.chat.ChatType;
import com.ssafy.goumunity.domain.chat.infra.chatroom.ChatRoomEntity;
import com.ssafy.goumunity.domain.chat.infra.chatroom.UserChatRoomEntity;
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
class ChatQueryDslRepositoryTest {

    @Autowired EntityManager em;

    @Autowired ChatQueryDslRepository chatQueryDslRepository;

    @Test
    void 이전_채팅_목록_조회_테스트_성공_hasNext_True() throws Exception {
        // given
        UserEntity user = UserEntity.builder().nickname("김싸피").build();

        em.persist(user);

        ChatRoomEntity chatRoom = ChatRoomEntity.builder().title("거지방").capability(10).build();

        em.persist(chatRoom);

        UserChatRoomEntity ucr = UserChatRoomEntity.builder().user(user).chatRoom(chatRoom).build();

        em.persist(ucr);

        for (int i = 0; i < 10; i++) {
            em.persist(
                    ChatEntity.builder()
                            .user(user)
                            .chatRoom(chatRoom)
                            .chatType(ChatType.MESSAGE)
                            .content("zㅋㅋㅋㅋㅋㅋㅋ")
                            .createdAt(Instant.now())
                            .build());
        }
        // when
        Slice<MessageResponse.Previous> sut =
                chatQueryDslRepository.findPreviousMessage(
                        chatRoom.getId(),
                        Instant.now().toEpochMilli() + 1000000000L,
                        PageRequest.of(0, 5),
                        ucr.getId());
        // then
        SoftAssertions sa = new SoftAssertions();

        sa.assertThat(sut.hasNext()).isTrue();
        sa.assertThat(sut.getContent().size()).isSameAs(5);
        sa.assertAll();
    }

    @Test
    void 이전_채팅_목록_조회_테스트_성공_hasNext_False() throws Exception {
        // given

        UserEntity user = UserEntity.builder().nickname("김싸피").build();

        em.persist(user);

        ChatRoomEntity chatRoom = ChatRoomEntity.builder().title("거지방").capability(10).build();

        em.persist(chatRoom);

        UserChatRoomEntity ucr = UserChatRoomEntity.builder().user(user).chatRoom(chatRoom).build();

        em.persist(ucr);

        for (int i = 0; i < 5; i++) {
            em.persist(
                    ChatEntity.builder()
                            .user(user)
                            .chatRoom(chatRoom)
                            .chatType(ChatType.MESSAGE)
                            .content("zㅋㅋㅋㅋㅋㅋㅋ")
                            .createdAt(Instant.now())
                            .build());
        }
        // when
        Slice<MessageResponse.Previous> sut =
                chatQueryDslRepository.findPreviousMessage(
                        chatRoom.getId(),
                        Instant.now().toEpochMilli() + 1000000000L,
                        PageRequest.of(0, 5),
                        ucr.getId());
        // then
        SoftAssertions sa = new SoftAssertions();

        sa.assertThat(sut.hasNext()).isFalse();
        sa.assertThat(sut.getContent().size()).isSameAs(5);
        sa.assertThat(sut.getContent().get(0).getIsCurrentUser()).isTrue();
        sa.assertAll();
    }

    @Test
    void 이전_채팅_목록_조회_테스트_성공_시간_차이가_난다() throws Exception {
        // given

        UserEntity user = UserEntity.builder().nickname("김싸피").build();

        em.persist(user);

        ChatRoomEntity chatRoom = ChatRoomEntity.builder().title("거지방").capability(10).build();

        em.persist(chatRoom);

        UserChatRoomEntity ucr = UserChatRoomEntity.builder().user(user).chatRoom(chatRoom).build();

        em.persist(ucr);

        for (int i = 0; i < 5; i++) {
            em.persist(
                    ChatEntity.builder()
                            .user(user)
                            .chatRoom(chatRoom)
                            .chatType(ChatType.MESSAGE)
                            .content("zㅋㅋㅋㅋㅋㅋㅋ")
                            .createdAt(Instant.ofEpochMilli(10000000L + 10000000L * i * 10000))
                            .build());
        }
        // when
        Slice<MessageResponse.Previous> sut =
                chatQueryDslRepository.findPreviousMessage(
                        chatRoom.getId(),
                        Instant.now().toEpochMilli() + 1000000000L,
                        PageRequest.of(0, 5),
                        ucr.getId());
        // then
        SoftAssertions sa = new SoftAssertions();

        sa.assertThat(sut.hasNext()).isFalse();
        sa.assertThat(sut.getContent().size()).isSameAs(5);
        List<MessageResponse.Previous> content = sut.getContent();
        for (int i = 0; i < 4; i++) {
            sa.assertThat(content.get(i).getCreatedAt()).isGreaterThan(content.get(i + 1).getCreatedAt());
        }
        sa.assertAll();
    }

    @Test
    void 이전_채팅_목록_조회_테스트_성공_과거_데이터가_없으면_조회를_못한다() throws Exception {
        // given
        UserEntity user = UserEntity.builder().nickname("김싸피").build();

        em.persist(user);

        ChatRoomEntity chatRoom = ChatRoomEntity.builder().title("거지방").capability(10).build();

        em.persist(chatRoom);

        UserChatRoomEntity ucr = UserChatRoomEntity.builder().user(user).chatRoom(chatRoom).build();

        em.persist(ucr);

        for (int i = 0; i < 5; i++) {
            em.persist(
                    ChatEntity.builder()
                            .user(user)
                            .chatRoom(chatRoom)
                            .chatType(ChatType.MESSAGE)
                            .content("zㅋㅋㅋㅋㅋㅋㅋ")
                            .createdAt(Instant.ofEpochMilli(10000000L + 10000000L * i * 10000))
                            .build());
        }
        // when
        Slice<MessageResponse.Previous> sut =
                chatQueryDslRepository.findPreviousMessage(
                        chatRoom.getId(), 100L, PageRequest.of(0, 5), ucr.getId());
        // then
        SoftAssertions sa = new SoftAssertions();

        sa.assertThat(sut.hasNext()).isFalse();
        sa.assertThat(sut.getContent()).isEmpty();
        sa.assertAll();
    }
}
