package com.ssafy.goumunity.domain.feed.infra.feed;

import org.springframework.data.jpa.repository.JpaRepository;

public interface FeedJpaRepository extends JpaRepository<FeedEntity, Long> {}
