package com.ssafy.goumunity.domain.feed.infra.reply;

import static com.ssafy.goumunity.domain.feed.infra.reply.QReplyEntity.replyEntity;
import static com.ssafy.goumunity.domain.feed.infra.replylike.QReplyLikeEntity.replyLikeEntity;
import static com.ssafy.goumunity.domain.user.infra.QUserEntity.userEntity;

import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.goumunity.common.util.QueryDslSliceUtils;
import com.ssafy.goumunity.domain.feed.controller.response.QReplyResponse;
import com.ssafy.goumunity.domain.feed.controller.response.ReplyResponse;
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

    public Slice<ReplyResponse> findAllByCommentId(Long commentId, Instant time, Pageable pageable) {
        final List<ReplyResponse> result =
                queryFactory
                        .query()
                        .select(
                                new QReplyResponse(
                                        replyEntity,
                                        JPAExpressions.select(replyLikeEntity.count())
                                                .from(replyLikeEntity)
                                                .where(replyEntity.eq(replyLikeEntity.replyEntity))))
                        .from(replyEntity)
                        .leftJoin(replyEntity.userEntity, userEntity)
                        .where(replyEntity.commentEntity.commentId.eq(commentId))
                        .where(replyEntity.createdAt.before(time))
                        .orderBy(replyEntity.createdAt.desc())
                        .offset(pageable.getOffset())
                        .limit(pageable.getPageSize() + 1)
                        .fetch();

        return new SliceImpl<>(result, pageable, QueryDslSliceUtils.hasNext(result, pageable));
    }
}
