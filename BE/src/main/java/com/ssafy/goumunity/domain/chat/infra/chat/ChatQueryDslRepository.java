package com.ssafy.goumunity.domain.chat.infra.chat;

import static com.ssafy.goumunity.domain.chat.infra.chat.QChatEntity.chatEntity;
import static com.ssafy.goumunity.domain.user.infra.QUserEntity.userEntity;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.goumunity.common.util.QueryDslSliceUtils;
import com.ssafy.goumunity.domain.chat.controller.response.MessageResponse;
import java.time.Instant;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ChatQueryDslRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public Slice<MessageResponse.Previous> findPreviousMessage(
            Long chatRoomId, Long time, Pageable pageable, Long userId) {
        List<MessageResponse.Previous> result =
                jpaQueryFactory
                        .select(
                                Projections.constructor(
                                        MessageResponse.Previous.class, chatEntity, Expressions.constant(userId)))
                        .distinct()
                        .from(chatEntity)
                        .leftJoin(chatEntity.user, userEntity)
                        .fetchJoin()
                        .where(
                                chatEntity
                                        .chatRoomEntity
                                        .id
                                        .eq(chatRoomId)
                                        .and(chatEntity.createdAt.before(Instant.ofEpochMilli(time))))
                        .orderBy(chatEntity.createdAt.desc())
                        .offset(pageable.getOffset())
                        .limit(pageable.getPageSize() + 1)
                        .fetch();
        return new SliceImpl<>(result, pageable, QueryDslSliceUtils.hasNext(result, pageable));
    }
}
