package com.ssafy.goumunity.domain.feed.infra.feed;

import static com.ssafy.goumunity.domain.feed.infra.comment.QCommentEntity.commentEntity;
import static com.ssafy.goumunity.domain.feed.infra.feed.QFeedEntity.feedEntity;
import static com.ssafy.goumunity.domain.feed.infra.feedimg.QFeedImgEntity.feedImgEntity;
import static com.ssafy.goumunity.domain.feed.infra.feedlike.QFeedLikeEntity.feedLikeEntity;
import static com.ssafy.goumunity.domain.region.infra.QRegionEntity.regionEntity;
import static com.ssafy.goumunity.domain.user.infra.QUserEntity.userEntity;

import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.goumunity.common.util.SliceUtils;
import com.ssafy.goumunity.domain.feed.controller.response.FeedResponse;
import com.ssafy.goumunity.domain.feed.controller.response.QFeedResponse;
import java.time.Instant;
import java.util.List;

import com.ssafy.goumunity.domain.feed.domain.FeedRecommendResource;
import com.ssafy.goumunity.domain.feed.domain.QFeedRecommendResource;
import com.ssafy.goumunity.domain.region.infra.QRegionEntity;
import com.ssafy.goumunity.domain.region.infra.RegionEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class FeedQueryDslRepository {

    private final JPAQueryFactory queryFactory;

    public List<FeedRecommendResource> findFeed(Long userId, Instant time, Long regionId) {
        return queryFactory
                        .query()
                        .select(
                                new QFeedRecommendResource(
                                        feedEntity,
                                        JPAExpressions.select(commentEntity.count())
                                                .from(commentEntity)
                                                .where(feedEntity.eq(commentEntity.feedEntity)),
                                        JPAExpressions.select(feedLikeEntity.count())
                                                .from(feedLikeEntity)
                                                .where(feedEntity.eq(feedLikeEntity.feedEntity)),
                                        JPAExpressions.selectFrom(feedLikeEntity)
                                                .where(feedLikeEntity.userEntity.id.eq(userId))
                                                .where(feedLikeEntity.feedEntity.feedId.eq(feedEntity.feedId))
                                                .exists()
                                        ))
                        .from(feedEntity)
                        .leftJoin(feedEntity.images, feedImgEntity)
                        .leftJoin(feedEntity.userEntity, userEntity)
                        .leftJoin(feedEntity.regionEntity, regionEntity).on(regionEntity.regionId.eq(regionId))
                        .groupBy(feedEntity)
                        .having(feedEntity.createdAt.before(time))
                        .orderBy(feedEntity.createdAt.desc(), feedImgEntity.sequence.asc())
                        .fetch();
    }

    public FeedResponse findOneFeed(Long feedId) {
        return queryFactory
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
                .where(feedEntity.feedId.eq(feedId))
                .groupBy(feedEntity)
                .fetchOne();
    }
}
