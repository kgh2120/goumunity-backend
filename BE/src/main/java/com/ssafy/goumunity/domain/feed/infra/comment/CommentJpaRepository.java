package com.ssafy.goumunity.domain.feed.infra.comment;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentJpaRepository extends JpaRepository<CommentEntity, Long> {}
