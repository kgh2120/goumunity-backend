package com.ssafy.goumunity.domain.feed.infra.commentlike;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface CommentLikeJpaRepository extends JpaRepository<CommentLikeEntity, Long> {

    Optional<CommentLikeEntity> findByUserEntity_IdAndCommentEntity_Id(Long userId, Long commentId);

    boolean existsByUserEntity_IdAndCommentEntity_Id(Long userId, Long commentId);

    @Modifying
    @Query("delete from CommentLikeEntity c where c.commentEntity.id in :commentIds")
    void deleteAllByCommentsIds(List<Long> commentIds);
}
