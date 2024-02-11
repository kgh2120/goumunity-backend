package com.ssafy.goumunity.domain.chat.infra;

import static com.ssafy.goumunity.domain.chat.infra.chatroom.QChatRoomEntity.chatRoomEntity;
import static com.ssafy.goumunity.domain.chat.infra.chatroom.QUserChatRoomEntity.userChatRoomEntity;
import static com.ssafy.goumunity.domain.user.infra.QUserEntity.userEntity;
import static org.assertj.core.api.Assertions.assertThat;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.goumunity.domain.chat.controller.request.ChatRoomRequest;
import com.ssafy.goumunity.domain.chat.controller.response.ChatRoomDetailResponse;
import com.ssafy.goumunity.domain.chat.domain.ChatRoom;
import com.ssafy.goumunity.domain.chat.domain.Hashtag;
import com.ssafy.goumunity.domain.chat.infra.chatroom.ChatRoomEntity;
import com.ssafy.goumunity.domain.chat.infra.chatroom.ChatRoomJpaRepository;
import com.ssafy.goumunity.domain.chat.infra.chatroom.UserChatRoomEntity;
import com.ssafy.goumunity.domain.chat.infra.hashtag.ChatRoomHashtagEntity;
import com.ssafy.goumunity.domain.chat.infra.hashtag.HashtagEntity;
import com.ssafy.goumunity.domain.chat.service.port.ChatRoomRepository;
import com.ssafy.goumunity.domain.region.infra.RegionEntity;
import com.ssafy.goumunity.domain.user.infra.QUserEntity;
import com.ssafy.goumunity.domain.user.infra.UserEntity;
import jakarta.persistence.EntityManager;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@SpringBootTest
class ChatRoomJpaRepositoryTest {

    @Autowired ChatRoomJpaRepository chatRoomJpaRepository;

    @Autowired EntityManager em;

    @Autowired ChatRoomRepository chatRoomRepository;

    @Test
    void 채팅방_조회_시_Count쿼리_체크() throws Exception {
        // given

        RegionEntity region = RegionEntity.builder().si("서울").gungu("강남구").build();

        em.persist(region);
        ChatRoomEntity chatRoom =
                ChatRoomEntity.builder()
                        .title("해윙")
                        .createdAt(Instant.now())
                        .region(region)
                        .capability(10)
                        .build();

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

    @Test
    void 채팅방_수정_테스트() throws Exception {
        // given
        RegionEntity region = RegionEntity.builder().si("서울").gungu("강남구").build();
        UserEntity user = UserEntity.builder().build();
        em.persist(user);
        em.persist(region);
        ChatRoomEntity chatRoom =
                ChatRoomEntity.builder()
                        .title("해윙")
                        .createdAt(Instant.now())
                        .region(region)
                        .capability(10)
                        .userChatRooms(new ArrayList<>())
                        .chatRoomHashtags(new ArrayList<>())
                        .host(user)
                        .build();

        em.persist(chatRoom);

        for (int i = 0; i < 4; i++) {
            HashtagEntity hashtag = HashtagEntity.from(Hashtag.create("해시태그"));
            em.persist(hashtag);
            em.persist(ChatRoomHashtagEntity.create(hashtag, chatRoom, i));
        }

        Long id = chatRoom.getId();
        ChatRoom model = chatRoom.to();

        ChatRoomRequest.Modify modify =
                ChatRoomRequest.Modify.builder()
                        .leaderId(user.getId())
                        .title("거거지지")
                        .hashtagRequests(
                                List.of(
                                        ChatRoomRequest.HashtagRequest.builder().name("1").build(),
                                        ChatRoomRequest.HashtagRequest.builder().name("2").build()))
                        .capability(20)
                        .build();
        model.modify(
                modify,
                modify.getHashtagRequests().stream()
                        .map(request -> Long.valueOf(request.getName()))
                        .toList(),
                "");
        chatRoomRepository.update(model);
        em.flush();
        em.clear();
        // when

        ChatRoomEntity sut = chatRoomJpaRepository.findById(id).orElseThrow();
        // then
        SoftAssertions sa = new SoftAssertions();
        sa.assertThat(sut.getTitle()).isEqualTo("거거지지");
        sa.assertThat(sut.getChatRoomHashtags().size()).isEqualTo(2);
        sa.assertThat(sut.getCapability()).isEqualTo(20);

        sa.assertAll();
    }

    @Test
    void 내_채팅방_목록_전체_획득() throws Exception {
        // given
        UserEntity user = UserEntity.builder().build();
        em.persist(user);

        for (int i = 0; i < 10; i++) {
            ChatRoomEntity chatRoom = ChatRoomEntity.builder().host(user).build();
            em.persist(chatRoom);
            UserEntity u2 = UserEntity.builder().build();
            em.persist(u2);

            em.persist(
                    UserChatRoomEntity.builder()
                            .user(user)
                            .chatRoom(chatRoom)
                            .createdAt(Instant.ofEpochMilli(100))
                            .build());
            em.persist(
                    UserChatRoomEntity.builder()
                            .user(u2)
                            .chatRoom(chatRoom)
                            .createdAt(Instant.ofEpochMilli(100))
                            .build());
        }
        // when
        List<ChatRoomEntity> sut = chatRoomJpaRepository.findAllByUserId(user.getId());
        // then
        assertThat(sut.size()).isEqualTo(10);
    }

    @Test
    void 거지방_상세_조회_테스트() throws Exception {
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
        chatRoom =
                ChatRoomEntity.builder()
                        .title("거지방")
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

        em.flush();
        em.clear();

        // when
        ChatRoomDetailResponse sut =
                new JPAQueryFactory(em)
                        .select(
                                Projections.constructor(
                                        ChatRoomDetailResponse.class,
                                        chatRoomEntity,
                                        Expressions.constant(users.getId())))
                        .distinct()
                        .from(chatRoomEntity)
                        .join(chatRoomEntity.host, new QUserEntity("h"))
                        .fetchJoin()
                        .join(chatRoomEntity.userChatRooms, userChatRoomEntity)
                        .join(userChatRoomEntity.user, userEntity)
                        .where(chatRoomEntity.id.eq(chatRoom.getId()))
                        .fetchOne();

        // then
        SoftAssertions sa = new SoftAssertions();
        sa.assertThat(sut.getChatRoomId()).isEqualTo(1L);
        sa.assertThat(sut.getTitle()).isEqualTo("거지방");
        sa.assertThat(sut.getImgSrc()).isNull();
        sa.assertThat(sut.getCapability()).isEqualTo(10);
        sa.assertThat(sut.getCurrentUserCount()).isEqualTo(1);
        sa.assertThat(sut.getHashtags().size()).isEqualTo(3);
        sa.assertThat(sut.getHost().getUserId()).isEqualTo(1L);
        sa.assertThat(sut.getHost().getNickname()).isEqualTo("1234");
        sa.assertThat(sut.getHost().getProfileImageSrc()).isNull();
        sa.assertThat(sut.getHost().getIsCurrentUser()).isTrue();
        sa.assertThat(sut.getIsHost()).isTrue();
        sa.assertThat(sut.getMembers().size()).isEqualTo(1);
    }
}
