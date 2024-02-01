package com.ssafy.goumunity.domain.chat.infra;

import static com.ssafy.goumunity.domain.chat.infra.QChatRoomEntity.chatRoomEntity;
import static com.ssafy.goumunity.domain.chat.infra.QChatRoomHashtagEntity.chatRoomHashtagEntity;
import static com.ssafy.goumunity.domain.chat.infra.QHashtagEntity.hashtagEntity;
import static com.ssafy.goumunity.domain.chat.infra.QUserChatRoomEntity.userChatRoomEntity;
import static com.ssafy.goumunity.domain.user.infra.QUserEntity.userEntity;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.goumunity.common.util.SliceUtils;
import com.ssafy.goumunity.domain.chat.controller.response.ChatRoomUserResponse;
import com.ssafy.goumunity.domain.chat.controller.response.MyChatRoomResponse;
import com.ssafy.goumunity.domain.user.domain.User;
import java.time.Instant;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class ChatRoomQueryDslRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public Slice<MyChatRoomResponse> findMyChatRoom(User user, Long time, Pageable pageable) {
        // TODO 채팅방 접속 or 나가기 기록 API가 추가된다면, 마지막으로 읽은 채팅개수 세기,
        List<MyChatRoomResponse> res =
                jpaQueryFactory
                        .select(Projections.constructor(MyChatRoomResponse.class, chatRoomEntity))
                        .distinct()
                        .from(chatRoomEntity)
                        .leftJoin(chatRoomEntity.chatRoomHashtags, chatRoomHashtagEntity)
                        .leftJoin(chatRoomHashtagEntity.hashtag, hashtagEntity)
                        .leftJoin(chatRoomEntity.userChatRooms, userChatRoomEntity)
                        .leftJoin(userChatRoomEntity.user, userEntity)
                        .where(
                                userChatRoomEntity
                                        .user
                                        .id
                                        .eq(user.getId())
                                        .and(chatRoomEntity.createdAt.before(Instant.ofEpochMilli(time))))
                        .orderBy(chatRoomEntity.id.desc())
                        .offset(pageable.getOffset())
                        .limit(pageable.getPageSize() + 1)
                        .fetch();

        return new SliceImpl<>(res, pageable, SliceUtils.hasNext(res, pageable));
    }

    public Slice<ChatRoomUserResponse> findChatRoomUsers(
            Long chatRoomId, Pageable pageable, Long time, Long userId) {
        List<ChatRoomUserResponse> result =
                jpaQueryFactory
                        .select(
                                Projections.constructor(
                                        ChatRoomUserResponse.class, userEntity, Expressions.constant(userId)))
                        .from(chatRoomEntity)
                        .join(chatRoomEntity.userChatRooms, userChatRoomEntity)
                        .join(userChatRoomEntity.user, userEntity)
                        .where(
                                chatRoomEntity
                                        .id
                                        .eq(chatRoomId)
                                        .and(userEntity.createdAt.before(Instant.ofEpochMilli(time))))
                        .orderBy(userChatRoomEntity.id.asc())
                        .offset(pageable.getOffset())
                        .limit(pageable.getPageSize() + 1)
                        .fetch();
        return new SliceImpl<>(result, pageable, SliceUtils.hasNext(result, pageable));
    }
}
