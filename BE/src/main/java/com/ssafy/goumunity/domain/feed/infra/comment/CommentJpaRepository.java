package com.ssafy.goumunity.domain.feed.infra.comment;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentJpaRepository extends JpaRepository<CommentEntity, Long> {
    List<CommentEntity> findAllByFeedEntity_FeedId(Long feedId);
}
