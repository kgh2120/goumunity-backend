package com.ssafy.goumunity.domain.feed.infra.replylike;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface ReplyLikeJpaRepository extends JpaRepository<ReplyLikeEntity, Long> {

    Optional<ReplyLikeEntity> findByUserEntity_IdAndReplyEntity_Id(Long userId, Long replyId);

    boolean existsByUserEntity_IdAndReplyEntity_Id(Long userId, Long replyId);

    @Modifying
    @Query("delete from ReplyLikeEntity r where r.replyEntity.id in :replyIds")
    void deleteAllByReplyIds(List<Long> replyIds);
}
