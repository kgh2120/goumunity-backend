package com.ssafy.goumunity.domain.chat.infra.chatroom;

import static com.ssafy.goumunity.domain.chat.infra.chat.QChatEntity.chatEntity;
import static com.ssafy.goumunity.domain.chat.infra.chatroom.QChatRoomEntity.chatRoomEntity;
import static com.ssafy.goumunity.domain.chat.infra.chatroom.QUserChatRoomEntity.userChatRoomEntity;
import static com.ssafy.goumunity.domain.chat.infra.hashtag.QChatRoomHashtagEntity.chatRoomHashtagEntity;
import static com.ssafy.goumunity.domain.chat.infra.hashtag.QHashtagEntity.hashtagEntity;
import static com.ssafy.goumunity.domain.user.infra.QUserEntity.userEntity;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.goumunity.common.util.QueryDslSliceUtils;
import com.ssafy.goumunity.domain.chat.controller.response.ChatRoomUserResponse;
import com.ssafy.goumunity.domain.chat.controller.response.MyChatRoomResponse;
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

    public Slice<MyChatRoomResponse> findMyChatRoom(Long userId, Long time, Pageable pageable) {
        List<MyChatRoomResponse> res =
                jpaQueryFactory
                        .select(
                                Projections.constructor(
                                        MyChatRoomResponse.class,
                                        chatRoomEntity,
                                        JPAExpressions.select(chatEntity.count())
                                                .from(chatEntity)
                                                .where(
                                                        chatEntity
                                                                .chatRoomEntity
                                                                .eq(chatRoomEntity)
                                                                .and(
                                                                        chatEntity.createdAt.after(
                                                                                JPAExpressions.select(userChatRoomEntity.lastAccessTime)
                                                                                        .from(userChatRoomEntity)
                                                                                        .where(
                                                                                                userChatRoomEntity
                                                                                                        .chatRoom
                                                                                                        .eq(chatRoomEntity)
                                                                                                        .and(
                                                                                                                userChatRoomEntity.user.id.eq(
                                                                                                                        Expressions.constant(userId)))))))))
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
                                        .eq(userId)
                                        .and(chatRoomEntity.createdAt.before(Instant.ofEpochMilli(time))))
                        //                        .groupBy(chatRoomEntity)
                        .orderBy(chatRoomEntity.id.desc())
                        .offset(pageable.getOffset())
                        .limit(pageable.getPageSize() + 1)
                        .fetch();

        return new SliceImpl<>(res, pageable, QueryDslSliceUtils.hasNext(res, pageable));
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
        return new SliceImpl<>(result, pageable, QueryDslSliceUtils.hasNext(result, pageable));
    }
}
