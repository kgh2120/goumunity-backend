package com.ssafy.goumunity.domain.feed.infra.comment;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentJpaRepository extends JpaRepository<CommentEntity, Long> {
    Optional<CommentEntity> findByCommentId(Long id);

    void delete(CommentEntity comment);
}
