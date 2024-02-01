package com.ssafy.goumunity.domain.chat.infra.hashtag;

import java.time.Instant;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface HashtagJpaRepository extends JpaRepository<HashtagEntity, Long> {

    @Query(
            "select h from HashtagEntity h where h.name like :hashtagName% and h.createdAt < :retrieveTime")
    Slice<HashtagEntity> findAllByKeyword(
            String hashtagName, Instant retrieveTime, Pageable pageable);

    boolean existsOneByName(String name);
}
