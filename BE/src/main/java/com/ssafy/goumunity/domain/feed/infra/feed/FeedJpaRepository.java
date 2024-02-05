package com.ssafy.goumunity.domain.feed.infra.feed;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeedJpaRepository extends JpaRepository<FeedEntity, Long> {
    List<FeedEntity> findAllByUserEntity_Id(Long userId);
}
