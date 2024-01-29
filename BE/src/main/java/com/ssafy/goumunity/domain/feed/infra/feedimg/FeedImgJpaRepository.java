package com.ssafy.goumunity.domain.feed.infra.feedimg;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface FeedImgJpaRepository extends JpaRepository<FeedImgEntity, Long> {

    @Query("select fi from FeedImgEntity fi where fi.feedImgId=:feedImgId")
    Optional<FeedImgEntity> findOneByFeedImgId(@Param("feedImgId") Long feedImgId);

    @Query("select fi from FeedImgEntity fi where fi.feedEntity.feedId=:feedId")
    List<FeedImgEntity> findAllByFeedId(@Param("feedId") Long feedId);

    List<FeedImgEntity> findAllByFeedEntity_FeedIdOrderBySequenceAsc(Long feedId);
}
