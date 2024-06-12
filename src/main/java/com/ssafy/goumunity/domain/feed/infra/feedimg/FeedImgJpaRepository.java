package com.ssafy.goumunity.domain.feed.infra.feedimg;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface FeedImgJpaRepository extends JpaRepository<FeedImgEntity, Long> {
    List<FeedImgEntity> findAllByFeedEntity_Id(Long feedId);

    @Modifying
    @Query("delete from FeedImgEntity f where f.feedEntity.id in :feedIds")
    void deleteAllByFeedIds(List<Long> feedIds);
}
