package com.ssafy.goumunity.domain.feed.infra.comment;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.goumunity.domain.feed.controller.response.CommentResponse;
import com.ssafy.goumunity.domain.feed.controller.response.QCommentResponse;
import com.ssafy.goumunity.domain.user.infra.QUserEntity;
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

    public Slice<CommentResponse> findAllByFeedId(Long feedId, Pageable pageable) {

        final List<CommentResponse> result =
                queryFactory
                        .query()
                        .select(new QCommentResponse(comment))
                        .from(comment)
                        .leftJoin(user)
                        .on(comment.userEntity.eq(user))
                        .where(comment.feedEntity.feedId.eq(feedId))
                        .offset(pageable.getOffset())
                        .limit(pageable.getPageSize() + 1)
                        .fetch();

        boolean hasNext = false;
        if (result.size() > pageable.getPageSize()) {
            result.remove(pageable.getPageSize());
            hasNext = true;
        }

        return new SliceImpl<>(result, pageable, hasNext);
    }
}
