package com.ssafy.goumunity.domain.chat.infra;

import static org.assertj.core.api.Assertions.assertThat;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.goumunity.domain.chat.controller.response.MyChatRoomResponse;
import com.ssafy.goumunity.domain.chat.infra.chat.ChatEntity;
import com.ssafy.goumunity.domain.chat.infra.chat.ChatType;
import com.ssafy.goumunity.domain.chat.infra.chatroom.ChatRoomEntity;
import com.ssafy.goumunity.domain.chat.infra.chatroom.ChatRoomJpaRepository;
import com.ssafy.goumunity.domain.chat.infra.chatroom.ChatRoomQueryDslRepository;
import com.ssafy.goumunity.domain.chat.infra.chatroom.UserChatRoomEntity;
import com.ssafy.goumunity.domain.chat.infra.hashtag.ChatRoomHashtagEntity;
import com.ssafy.goumunity.domain.chat.infra.hashtag.HashtagEntity;
import com.ssafy.goumunity.domain.user.infra.UserEntity;
import jakarta.persistence.EntityManager;
import java.time.Instant;
import java.util.List;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@SpringBootTest
class ChatRoomSearchQueryTest {

    @Autowired EntityManager em;

    @Autowired ChatRoomJpaRepository chatRoomJpaRepository;
    @Autowired JPAQueryFactory jpaQueryFactory;
    @Autowired ChatRoomQueryDslRepository chatRoomQueryDslRepository;

    @Test
    void 거지방_검색_아이디로_검색() throws Exception {
        // given

        UserEntity users =
                UserEntity.builder().nickname("1234").email("ssafy@gmail.com").password("1234").build();

        em.persist(users);

        HashtagEntity h1 = HashtagEntity.builder().name("20대").build();
        em.persist(h1);
        HashtagEntity h2 = HashtagEntity.builder().name("관악구").build();
        em.persist(h2);
        HashtagEntity h3 = HashtagEntity.builder().name("10만원 미만").build();
        em.persist(h3);

        for (int i = 0; i < 10; i++) {
            ChatRoomEntity chatRoom =
                    ChatRoomEntity.builder()
                            .title("거지방" + i)
                            .capability(10)
                            .host(users)
                            .createdAt(Instant.ofEpochMilli(100L))
                            .build();
            em.persist(chatRoom);

            em.persist(
                    ChatRoomHashtagEntity.builder().chatRoom(chatRoom).hashtag(h1).sequence(1).build());

            em.persist(
                    ChatRoomHashtagEntity.builder().chatRoom(chatRoom).hashtag(h2).sequence(2).build());

            em.persist(
                    ChatRoomHashtagEntity.builder().chatRoom(chatRoom).hashtag(h3).sequence(3).build());
        }

        em.flush();
        em.clear();
        // when

        Slice<ChatRoomEntity> slice =
                chatRoomJpaRepository.searchChatRoom("거지방", Instant.now(), PageRequest.of(0, 10));
        assertThat(slice.getContent().size()).isSameAs(10);
    }

    @Test
    void 거지방_검색_해시태그로_검색() throws Exception {
        // given
        UserEntity users =
                UserEntity.builder().nickname("1234").email("ssafy@gmail.com").password("1234").build();

        em.persist(users);

        HashtagEntity h1 = HashtagEntity.builder().name("20대").build();
        em.persist(h1);
        HashtagEntity h2 = HashtagEntity.builder().name("관악구").build();
        em.persist(h2);
        HashtagEntity h3 = HashtagEntity.builder().name("10만원 미만").build();
        em.persist(h3);

        for (int i = 0; i < 10; i++) {
            ChatRoomEntity chatRoom =
                    ChatRoomEntity.builder()
                            .title("거지방" + i)
                            .capability(10)
                            .host(users)
                            .createdAt(Instant.ofEpochMilli(100L))
                            .build();
            em.persist(chatRoom);

            em.persist(
                    ChatRoomHashtagEntity.builder().chatRoom(chatRoom).hashtag(h1).sequence(1).build());

            em.persist(
                    ChatRoomHashtagEntity.builder().chatRoom(chatRoom).hashtag(h2).sequence(2).build());

            em.persist(
                    ChatRoomHashtagEntity.builder().chatRoom(chatRoom).hashtag(h3).sequence(3).build());
        }

        em.flush();
        em.clear();
        // when

        Slice<ChatRoomEntity> slice =
                chatRoomJpaRepository.searchChatRoom("20대", Instant.now(), PageRequest.of(0, 10));
        assertThat(slice.getContent().size()).isSameAs(10);
    }

    @Test
    void 내_채팅방_조회_테스트() throws Exception {
        // given
        UserEntity users =
                UserEntity.builder().nickname("1234").email("ssafy@gmail.com").password("1234").build();

        em.persist(users);

        HashtagEntity h1 = HashtagEntity.builder().name("20대").build();
        em.persist(h1);
        HashtagEntity h2 = HashtagEntity.builder().name("관악구").build();
        em.persist(h2);
        HashtagEntity h3 = HashtagEntity.builder().name("10만원 미만").build();
        em.persist(h3);

        ChatRoomEntity chatRoom = null;

        for (int i = 0; i < 15; i++) {
            chatRoom =
                    ChatRoomEntity.builder()
                            .title("거지방" + i)
                            .capability(10)
                            .host(users)
                            .createdAt(Instant.ofEpochMilli(1000L))
                            .build();
            em.persist(chatRoom);

            em.persist(
                    ChatRoomHashtagEntity.builder().chatRoom(chatRoom).hashtag(h1).sequence(1).build());

            em.persist(
                    ChatRoomHashtagEntity.builder().chatRoom(chatRoom).hashtag(h2).sequence(2).build());

            em.persist(
                    ChatRoomHashtagEntity.builder().chatRoom(chatRoom).hashtag(h3).sequence(3).build());

            em.persist(
                    UserChatRoomEntity.builder()
                            .chatRoom(chatRoom)
                            .user(users)
                            .lastAccessTime(Instant.ofEpochMilli(100L))
                            .build());

            for (int j = 0; j < 10; j++) {
                em.persist(
                        ChatEntity.builder()
                                .chatRoom(chatRoom)
                                .user(users)
                                .chatType(ChatType.MESSAGE)
                                .content("hi")
                                .createdAt(Instant.now())
                                .build());
            }
            for (int j = 0; j < 10; j++) {
                em.persist(
                        ChatEntity.builder()
                                .chatRoom(chatRoom)
                                .user(users)
                                .chatType(ChatType.MESSAGE)
                                .content("hi")
                                .createdAt(Instant.ofEpochMilli(10L))
                                .build());
            }
        }

        chatRoom =
                ChatRoomEntity.builder()
                        .title("거지방---111")
                        .capability(10)
                        .host(users)
                        .createdAt(Instant.ofEpochMilli(1000L))
                        .build();
        em.persist(chatRoom);

        em.persist(ChatRoomHashtagEntity.builder().chatRoom(chatRoom).hashtag(h1).sequence(1).build());

        em.persist(ChatRoomHashtagEntity.builder().chatRoom(chatRoom).hashtag(h2).sequence(2).build());

        em.persist(ChatRoomHashtagEntity.builder().chatRoom(chatRoom).hashtag(h3).sequence(3).build());

        em.persist(
                UserChatRoomEntity.builder()
                        .chatRoom(chatRoom)
                        .user(users)
                        .lastAccessTime(Instant.ofEpochMilli(100L))
                        .build());

        for (int j = 0; j < 30; j++) {
            em.persist(
                    ChatEntity.builder()
                            .chatRoom(chatRoom)
                            .user(users)
                            .chatType(ChatType.MESSAGE)
                            .content("hi")
                            .createdAt(Instant.now())
                            .build());
        }

        em.flush();
        em.clear();

        Pageable pageable = PageRequest.of(0, 10);
        Slice<MyChatRoomResponse> res =
                chatRoomQueryDslRepository.findMyChatRoom(users.getId(), 100000L, pageable);

        List<MyChatRoomResponse> content = res.getContent();
        assertThat(content.size()).isSameAs(10);
        assertThat(content.get(0).getCurrentUserCount()).isEqualTo(1);
        assertThat(content.get(0).getUnReadMessageCount()).isEqualTo(30);
        assertThat(content.get(1).getUnReadMessageCount()).isEqualTo(10);

        assertThat(res.hasNext()).isTrue();
    }

    @Test
    void 내_채팅방_하나_테스트_null반환() throws Exception {
        // given
        UserEntity users =
                UserEntity.builder().nickname("1234").email("ssafy@gmail.com").password("1234").build();

        em.persist(users);

        HashtagEntity h1 = HashtagEntity.builder().name("20대").build();
        em.persist(h1);
        HashtagEntity h2 = HashtagEntity.builder().name("관악구").build();
        em.persist(h2);
        HashtagEntity h3 = HashtagEntity.builder().name("10만원 미만").build();
        em.persist(h3);

        ChatRoomEntity chatRoom = null;

        for (int i = 0; i < 15; i++) {
            chatRoom =
                    ChatRoomEntity.builder()
                            .title("거지방" + i)
                            .capability(10)
                            .host(users)
                            .createdAt(Instant.ofEpochMilli(1000L))
                            .build();
            em.persist(chatRoom);

            em.persist(
                    ChatRoomHashtagEntity.builder().chatRoom(chatRoom).hashtag(h1).sequence(1).build());

            em.persist(
                    ChatRoomHashtagEntity.builder().chatRoom(chatRoom).hashtag(h2).sequence(2).build());

            em.persist(
                    ChatRoomHashtagEntity.builder().chatRoom(chatRoom).hashtag(h3).sequence(3).build());

            em.persist(
                    UserChatRoomEntity.builder()
                            .chatRoom(chatRoom)
                            .user(users)
                            .lastAccessTime(Instant.ofEpochMilli(100L))
                            .build());

            for (int j = 0; j < 10; j++) {
                em.persist(
                        ChatEntity.builder()
                                .chatRoom(chatRoom)
                                .user(users)
                                .chatType(ChatType.MESSAGE)
                                .content("hi")
                                .createdAt(Instant.now())
                                .build());
            }
            for (int j = 0; j < 10; j++) {
                em.persist(
                        ChatEntity.builder()
                                .chatRoom(chatRoom)
                                .user(users)
                                .chatType(ChatType.MESSAGE)
                                .content("hi")
                                .createdAt(Instant.ofEpochMilli(10L))
                                .build());
            }
        }

        chatRoom =
                ChatRoomEntity.builder()
                        .title("거지방---111")
                        .capability(10)
                        .host(users)
                        .createdAt(Instant.ofEpochMilli(1000L))
                        .build();
        em.persist(chatRoom);

        em.persist(ChatRoomHashtagEntity.builder().chatRoom(chatRoom).hashtag(h1).sequence(1).build());

        em.persist(ChatRoomHashtagEntity.builder().chatRoom(chatRoom).hashtag(h2).sequence(2).build());

        em.persist(ChatRoomHashtagEntity.builder().chatRoom(chatRoom).hashtag(h3).sequence(3).build());

        em.persist(
                UserChatRoomEntity.builder()
                        .chatRoom(chatRoom)
                        .user(users)
                        .lastAccessTime(Instant.ofEpochMilli(100L))
                        .build());

        for (int j = 0; j < 30; j++) {
            em.persist(
                    ChatEntity.builder()
                            .chatRoom(chatRoom)
                            .user(users)
                            .chatType(ChatType.MESSAGE)
                            .content("hi")
                            .createdAt(Instant.now())
                            .build());
        }

        em.flush();
        em.clear();

        MyChatRoomResponse res =
                chatRoomQueryDslRepository.findOneMyChatRoomByChatRoomId(chatRoom.getId(), users.getId());

        SoftAssertions sa = new SoftAssertions();
        sa.assertThat(res.getTitle()).isEqualTo(chatRoom.getTitle());
        sa.assertThat(res.getCurrentUserCount()).isEqualTo(1);
        sa.assertThat(res.getUnReadMessageCount()).isEqualTo(30);
    }

    @Test
    void 내_채팅방_하나_테스트() throws Exception {
        // given
        UserEntity users =
                UserEntity.builder().nickname("1234").email("ssafy@gmail.com").password("1234").build();

        em.persist(users);

        HashtagEntity h1 = HashtagEntity.builder().name("20대").build();
        em.persist(h1);
        HashtagEntity h2 = HashtagEntity.builder().name("관악구").build();
        em.persist(h2);
        HashtagEntity h3 = HashtagEntity.builder().name("10만원 미만").build();
        em.persist(h3);

        ChatRoomEntity chatRoom = null;

        for (int i = 0; i < 15; i++) {
            chatRoom =
                    ChatRoomEntity.builder()
                            .title("거지방" + i)
                            .capability(10)
                            .host(users)
                            .createdAt(Instant.ofEpochMilli(1000L))
                            .build();
            em.persist(chatRoom);

            em.persist(
                    ChatRoomHashtagEntity.builder().chatRoom(chatRoom).hashtag(h1).sequence(1).build());

            em.persist(
                    ChatRoomHashtagEntity.builder().chatRoom(chatRoom).hashtag(h2).sequence(2).build());

            em.persist(
                    ChatRoomHashtagEntity.builder().chatRoom(chatRoom).hashtag(h3).sequence(3).build());

            em.persist(
                    UserChatRoomEntity.builder()
                            .chatRoom(chatRoom)
                            .user(users)
                            .lastAccessTime(Instant.ofEpochMilli(100L))
                            .build());

            for (int j = 0; j < 10; j++) {
                em.persist(
                        ChatEntity.builder()
                                .chatRoom(chatRoom)
                                .user(users)
                                .chatType(ChatType.MESSAGE)
                                .content("hi")
                                .createdAt(Instant.now())
                                .build());
            }
            for (int j = 0; j < 10; j++) {
                em.persist(
                        ChatEntity.builder()
                                .chatRoom(chatRoom)
                                .user(users)
                                .chatType(ChatType.MESSAGE)
                                .content("hi")
                                .createdAt(Instant.ofEpochMilli(10L))
                                .build());
            }
        }

        chatRoom =
                ChatRoomEntity.builder()
                        .title("거지방---111")
                        .capability(10)
                        .host(users)
                        .createdAt(Instant.ofEpochMilli(1000L))
                        .build();
        em.persist(chatRoom);

        em.persist(ChatRoomHashtagEntity.builder().chatRoom(chatRoom).hashtag(h1).sequence(1).build());

        em.persist(ChatRoomHashtagEntity.builder().chatRoom(chatRoom).hashtag(h2).sequence(2).build());

        em.persist(ChatRoomHashtagEntity.builder().chatRoom(chatRoom).hashtag(h3).sequence(3).build());

        em.persist(
                UserChatRoomEntity.builder()
                        .chatRoom(chatRoom)
                        .user(users)
                        .lastAccessTime(Instant.ofEpochMilli(100L))
                        .build());

        for (int j = 0; j < 30; j++) {
            em.persist(
                    ChatEntity.builder()
                            .chatRoom(chatRoom)
                            .user(users)
                            .chatType(ChatType.MESSAGE)
                            .content("hi")
                            .createdAt(Instant.now())
                            .build());
        }

        em.flush();
        em.clear();

        MyChatRoomResponse sut =
                chatRoomQueryDslRepository.findOneMyChatRoomByChatRoomId(53259L, users.getId());
        assertThat(sut).isNull();
    }
}
