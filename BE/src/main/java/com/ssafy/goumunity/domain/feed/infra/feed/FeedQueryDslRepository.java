package com.ssafy.goumunity.domain.feed.infra.feed;

import static com.ssafy.goumunity.domain.feed.infra.comment.QCommentEntity.commentEntity;
import static com.ssafy.goumunity.domain.feed.infra.feed.QFeedEntity.feedEntity;
import static com.ssafy.goumunity.domain.feed.infra.feedimg.QFeedImgEntity.feedImgEntity;
import static com.ssafy.goumunity.domain.feed.infra.feedlike.QFeedLikeEntity.feedLikeEntity;
import static com.ssafy.goumunity.domain.region.infra.QRegionEntity.regionEntity;
import static com.ssafy.goumunity.domain.user.infra.QUserEntity.userEntity;

import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.goumunity.common.util.QueryDslSliceUtils;
import com.ssafy.goumunity.domain.feed.controller.response.FeedResponse;
import com.ssafy.goumunity.domain.feed.controller.response.QFeedResponse;
import java.time.Instant;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class FeedQueryDslRepository {

    private final JPAQueryFactory queryFactory;

    public Slice<FeedResponse> findFeed(Instant time, Pageable pageable) {
        final List<FeedResponse> res =
                queryFactory
                        .query()
                        .select(
                                new QFeedResponse(
                                        feedEntity,
                                        JPAExpressions.select(commentEntity.count())
                                                .from(commentEntity)
                                                .where(feedEntity.eq(commentEntity.feedEntity)),
                                        JPAExpressions.select(feedLikeEntity.count())
                                                .from(feedLikeEntity)
                                                .where(feedEntity.eq(feedLikeEntity.feedEntity))))
                        .from(feedEntity)
                        .leftJoin(feedEntity.images, feedImgEntity)
                        .leftJoin(feedEntity.userEntity, userEntity)
                        .leftJoin(feedEntity.regionEntity, regionEntity)
                        .groupBy(feedEntity)
                        .having(feedEntity.createdAt.before(time))
                        .orderBy(feedEntity.createdAt.desc())
                        .offset(pageable.getOffset())
                        .limit(pageable.getPageSize() + 1)
                        .fetch();

        return new SliceImpl<>(res, pageable, QueryDslSliceUtils.hasNext(res, pageable));
    }
}
