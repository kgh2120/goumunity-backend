package com.ssafy.goumunity.domain.chat.infra.chatroom;

import static com.ssafy.goumunity.domain.chat.infra.chatroom.QUserChatRoomEntity.*;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class UserChatRoomQueryDslRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public Long getOldestUserIdOnChatRoom(Long chatRoomId, Long userId) {
        return jpaQueryFactory
                .select(userChatRoomEntity.user.id)
                .from(userChatRoomEntity)
                .where(
                        userChatRoomEntity
                                .chatRoom
                                .id
                                .eq(chatRoomId)
                                .and(userChatRoomEntity.user.id.ne(userId)))
                .orderBy(userChatRoomEntity.createdAt.asc())
                .offset(0)
                .limit(1)
                .fetchFirst();
    }
}
