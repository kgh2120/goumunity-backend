package com.ssafy.goumunity.domain.chat.infra.chat;

import static com.ssafy.goumunity.domain.chat.infra.QChatEntity.chatEntity;
import static com.ssafy.goumunity.domain.user.infra.QUserEntity.userEntity;

import com.querydsl.core.types.Projections;
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

    public Slice<MessageResponse> findPreviousMessage(Long chatRoomId, Long time, Pageable pageable) {
        List<MessageResponse> result =
                jpaQueryFactory
                        .select(Projections.constructor(MessageResponse.class, chatEntity))
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
