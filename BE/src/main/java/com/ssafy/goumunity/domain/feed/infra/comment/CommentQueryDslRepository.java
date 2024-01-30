package com.ssafy.goumunity.domain.feed.infra.comment;

import static com.ssafy.goumunity.domain.feed.infra.comment.QCommentEntity.commentEntity;
import static com.ssafy.goumunity.domain.feed.infra.commentlike.QCommentLikeEntity.commentLikeEntity;
import static com.ssafy.goumunity.domain.feed.infra.reply.QReplyEntity.replyEntity;
import static com.ssafy.goumunity.domain.user.infra.QUserEntity.userEntity;

import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.goumunity.common.util.SliceUtils;
import com.ssafy.goumunity.domain.feed.controller.response.CommentResponse;
import com.ssafy.goumunity.domain.feed.controller.response.QCommentResponse;
import java.time.Instant;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CommentQueryDslRepository {

    private final JPAQueryFactory queryFactory;

    public Slice<CommentResponse> findAllByFeedId(Long feedId, Instant time, Pageable pageable) {
        final List<CommentResponse> result =
                queryFactory
                        .query()
                        .select(
                                new QCommentResponse(
                                        commentEntity,
                                        JPAExpressions.select(replyEntity.count())
                                                .from(replyEntity)
                                                .where(commentEntity.eq(replyEntity.commentEntity)),
                                        JPAExpressions.select(commentLikeEntity.count())
                                                .from(commentLikeEntity)
                                                .where(commentEntity.eq(commentLikeEntity.commentEntity))))
                        .from(commentEntity)
                        .leftJoin(commentEntity.userEntity, userEntity)
                        .where(commentEntity.feedEntity.feedId.eq(feedId))
                        .where(commentEntity.createdAt.before(time))
                        .orderBy(commentEntity.createdAt.desc())
                        .offset(pageable.getOffset())
                        .limit(pageable.getPageSize() + 1)
                        .fetch();

        return new SliceImpl<>(result, pageable, SliceUtils.hasNext(result, pageable));
    }
}
