package com.ssafy.goumunity.domain.feed.infra.reply;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.goumunity.common.util.SliceUtils;
import com.ssafy.goumunity.domain.feed.controller.response.QReplyResponse;
import com.ssafy.goumunity.domain.feed.controller.response.ReplyResponse;
import com.ssafy.goumunity.domain.user.infra.QUserEntity;
import java.time.Instant;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ReplyQueryDslRepository {

    private final JPAQueryFactory queryFactory;
    private final QReplyEntity reply = QReplyEntity.replyEntity;
    private final QUserEntity user = QUserEntity.userEntity;

    public Slice<ReplyResponse> findAllByCommentId(Long commentId, Instant time, Pageable pageable) {
        final List<ReplyResponse> result =
                queryFactory
                        .query()
                        .select(new QReplyResponse(reply))
                        .from(reply)
                        .leftJoin(user)
                        .on(reply.userEntity.eq(user))
                        .where(reply.commentEntity.commentId.eq(commentId))
                        .where(reply.createdAt.before(time))
                        .orderBy(reply.createdAt.desc())
                        .offset(pageable.getOffset())
                        .limit(pageable.getPageSize() + 1)
                        .fetch();
        return new SliceImpl<>(result, pageable, SliceUtils.hasNext(result, pageable));
    }
}
