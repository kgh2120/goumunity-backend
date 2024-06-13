package com.ssafy.goumunity.domain.feed.infra.comment;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface CommentJpaRepository extends JpaRepository<CommentEntity, Long> {
    @Query("select c.id from CommentEntity c where c.feedEntity.id in :feedIds")
    List<Long> findAllCommentIdsInFeedIds(List<Long> feedIds);

    @Modifying
    @Query("delete from CommentEntity c where c.id in :commentIds")
    void deleteAllByIds(List<Long> commentIds);

    @Modifying
    @Query("delete from CommentEntity c where c.feedEntity.id in :feedIds")
    void deleteAllByFeedIds(List<Long> feedIds);
}
