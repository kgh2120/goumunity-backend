package com.ssafy.goumunity.domain.chat.infra;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.goumunity.common.util.QueryDslSliceUtils;
import com.ssafy.goumunity.domain.chat.controller.response.Message;
import com.ssafy.goumunity.domain.user.infra.QUserEntity;
import java.time.Instant;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class ChatQueryDslRepository {

    private final JPAQueryFactory jpaQueryFactory;

    private final QChatEntity chat = QChatEntity.chatEntity;
    private final QUserEntity user = QUserEntity.userEntity;

    public Slice<Message.Response> findPreviousMessage(
            Long chatRoomId, Long time, Pageable pageable) {
        List<Message.Response> result =
                jpaQueryFactory
                        .select(Projections.constructor(Message.Response.class, chat))
                        .distinct()
                        .from(chat)
                        .leftJoin(chat.user, user)
                        .fetchJoin()
                        .where(
                                chat.chatRoomEntity
                                        .id
                                        .eq(chatRoomId)
                                        .and(chat.createdAt.before(Instant.ofEpochMilli(time))))
                        .orderBy(chat.createdAt.desc())
                        .offset(pageable.getOffset())
                        .limit(pageable.getPageSize() + 1)
                        .fetch();
        return new SliceImpl<>(result, pageable, QueryDslSliceUtils.hasNext(result, pageable));
    }
}
