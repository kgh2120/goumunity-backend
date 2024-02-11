package com.ssafy.goumunity.domain.feed.infra.feed;

import static com.ssafy.goumunity.domain.feed.infra.comment.QCommentEntity.commentEntity;
import static com.ssafy.goumunity.domain.feed.infra.feed.QFeedEntity.feedEntity;
import static com.ssafy.goumunity.domain.feed.infra.feedimg.QFeedImgEntity.feedImgEntity;
import static com.ssafy.goumunity.domain.feed.infra.feedlike.QFeedLikeEntity.feedLikeEntity;
import static com.ssafy.goumunity.domain.feed.infra.feedscrap.QFeedScrapEntity.feedScrapEntity;
import static com.ssafy.goumunity.domain.region.infra.QRegionEntity.regionEntity;
import static com.ssafy.goumunity.domain.user.infra.QUserEntity.userEntity;

import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.goumunity.domain.feed.controller.response.FeedResponse;
import com.ssafy.goumunity.domain.feed.controller.response.QFeedResponse;
import com.ssafy.goumunity.domain.feed.domain.*;
import java.time.Instant;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class FeedQueryDslRepository {

    private final JPAQueryFactory queryFactory;

    public List<FeedRecommendResource> findFeed(Long userId, Long regionId) {
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
                                        .where(feedLikeEntity.feedEntity.eq(feedEntity))
                                        .exists(),
                                JPAExpressions.selectFrom(feedScrapEntity)
                                        .where(feedScrapEntity.userEntity.id.eq(userId))
                                        .where(feedScrapEntity.feedEntity.id.eq(feedEntity.id))
                                        .exists()))
                .from(feedEntity)
                .leftJoin(feedEntity.images, feedImgEntity)
                .leftJoin(feedEntity.userEntity, userEntity)
                .leftJoin(feedEntity.regionEntity, regionEntity)
                .where(feedEntity.regionEntity.regionId.eq(regionId))
                .groupBy(feedEntity)
                .having(feedEntity.createdAt.before(Instant.now()))
                .orderBy(feedEntity.createdAt.desc(), feedImgEntity.sequence.asc())
                .fetch();
    }

    public List<FeedSearchResource> findAllFeedByUserId(Long userId) {
        return queryFactory
                .query()
                .select(
                        new QFeedSearchResource(
                                feedEntity,
                                JPAExpressions.select(commentEntity.count())
                                        .from(commentEntity)
                                        .where(feedEntity.eq(commentEntity.feedEntity)),
                                JPAExpressions.select(feedLikeEntity.count())
                                        .from(feedLikeEntity)
                                        .where(feedEntity.eq(feedLikeEntity.feedEntity)),
                                JPAExpressions.selectFrom(feedLikeEntity)
                                        .where(feedLikeEntity.userEntity.id.eq(userId))
                                        .where(feedLikeEntity.feedEntity.id.eq(feedEntity.id))
                                        .exists(),
                                JPAExpressions.selectFrom(feedScrapEntity)
                                        .where(feedScrapEntity.userEntity.id.eq(userId))
                                        .where(feedScrapEntity.feedEntity.id.eq(feedEntity.id))
                                        .exists()))
                .from(feedEntity)
                .leftJoin(feedEntity.images, feedImgEntity)
                .leftJoin(feedEntity.userEntity, userEntity)
                .leftJoin(feedEntity.regionEntity, regionEntity)
                .where(feedEntity.userEntity.id.eq(userId))
                .groupBy(feedEntity)
                .having(feedEntity.createdAt.before(Instant.now()))
                .orderBy(feedEntity.createdAt.desc())
                .fetch();
    }

    public List<FeedSearchResource> findAllScrappedFeedByUserId(Long userId) {
        return queryFactory
                .query()
                .select(
                        new QFeedSearchResource(
                                feedEntity,
                                JPAExpressions.select(commentEntity.count())
                                        .from(commentEntity)
                                        .where(feedEntity.eq(commentEntity.feedEntity)),
                                JPAExpressions.select(feedLikeEntity.count())
                                        .from(feedLikeEntity)
                                        .where(feedEntity.eq(feedLikeEntity.feedEntity)),
                                JPAExpressions.selectFrom(feedLikeEntity)
                                        .where(feedLikeEntity.userEntity.id.eq(userId))
                                        .where(feedLikeEntity.feedEntity.eq(feedEntity))
                                        .exists(),
                                JPAExpressions.selectFrom(feedScrapEntity)
                                        .where(feedScrapEntity.userEntity.id.eq(userId))
                                        .where(feedScrapEntity.feedEntity.eq(feedEntity))
                                        .exists()))
                .from(feedEntity, feedScrapEntity)
                .leftJoin(feedEntity.images, feedImgEntity)
                .leftJoin(feedEntity.userEntity, userEntity)
                .leftJoin(feedEntity.regionEntity, regionEntity)
                .where(feedEntity.feedCategory.eq(FeedCategory.INFO))
                .where(feedScrapEntity.userEntity.id.eq(userId))
                .where(feedScrapEntity.feedEntity.eq(feedEntity))
                .groupBy(feedEntity)
                .having(feedEntity.createdAt.before(Instant.now()))
                .orderBy(feedScrapEntity.createdAt.desc())
                .fetch();
    }

    public FeedResponse findOneFeed(Long userId, Long feedId) {
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
                                        .where(feedEntity.eq(feedLikeEntity.feedEntity)),
                                JPAExpressions.selectFrom(feedLikeEntity)
                                        .where(feedLikeEntity.userEntity.id.eq(userId))
                                        .where(feedLikeEntity.feedEntity.eq(feedEntity))
                                        .exists(),
                                JPAExpressions.selectFrom(feedScrapEntity)
                                        .where(feedScrapEntity.userEntity.id.eq(userId))
                                        .where(feedScrapEntity.feedEntity.id.eq(feedEntity.id))
                                        .exists()))
                .from(feedEntity)
                .leftJoin(feedEntity.images, feedImgEntity)
                .leftJoin(feedEntity.userEntity, userEntity)
                .leftJoin(feedEntity.regionEntity, regionEntity)
                .where(feedEntity.id.eq(feedId))
                .groupBy(feedEntity)
                .fetchOne();
    }

    public void deleteAllByUserId(Long userId) {
        queryFactory.delete(feedEntity).where(feedEntity.userEntity.id.eq(userId)).execute();
    }
}
