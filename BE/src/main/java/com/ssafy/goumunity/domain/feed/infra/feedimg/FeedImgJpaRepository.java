package com.ssafy.goumunity.domain.feed.infra.feedimg;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeedImgJpaRepository extends JpaRepository<FeedImgEntity, Long> {
    List<FeedImgEntity> findAllByFeedEntity_FeedId(Long feedId);
}
