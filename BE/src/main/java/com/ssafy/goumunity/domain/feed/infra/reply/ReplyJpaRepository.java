package com.ssafy.goumunity.domain.feed.infra.reply;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface ReplyJpaRepository extends JpaRepository<ReplyEntity, Long> {

    @Query("select r.id from ReplyEntity r where r.commentEntity.id in :commentsIds")
    List<Long> findAllReplyIdsInCommentIds(List<Long> commentsIds);

    @Modifying
    @Query("delete from ReplyEntity r where r.id in :replyIds")
    void deleteAllByReplyIds(List<Long> replyIds);

    @Modifying
    @Query("delete from ReplyEntity r where r.commentEntity.id in :commentIds")
    void deleteAllByCommentIds(List<Long> commentIds);
}
