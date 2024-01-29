package com.ssafy.goumunity.domain.feed.infra.comment;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.goumunity.common.util.SliceUtils;
import com.ssafy.goumunity.domain.feed.controller.response.CommentResponse;
import com.ssafy.goumunity.domain.feed.controller.response.QCommentResponse;
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
public class CommentQueryDslRepository {

    private final JPAQueryFactory queryFactory;
    private final QCommentEntity comment = QCommentEntity.commentEntity;
    private final QUserEntity user = QUserEntity.userEntity;

    public Slice<CommentResponse> findAllByFeedId(Long feedId, Instant time, Pageable pageable) {

        final List<CommentResponse> result =
                queryFactory
                        .query()
                        .select(new QCommentResponse(comment))
                        .from(comment)
                        .leftJoin(user)
                        .on(comment.userEntity.eq(user))
                        .where(comment.feedEntity.feedId.eq(feedId))
                        .where(comment.createdAt.before(time))
                        .orderBy(comment.createdAt.desc())
                        .offset(pageable.getOffset())
                        .limit(pageable.getPageSize() + 1)
                        .fetch();

        return new SliceImpl<>(result, pageable, SliceUtils.hasNext(result, pageable));
    }
}
