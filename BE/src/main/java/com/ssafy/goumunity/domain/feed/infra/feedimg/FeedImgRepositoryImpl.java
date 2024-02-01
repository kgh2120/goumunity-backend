package com.ssafy.goumunity.domain.feed.infra.feedimg;

import com.ssafy.goumunity.domain.feed.domain.FeedImg;
import com.ssafy.goumunity.domain.feed.service.post.FeedImgRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class FeedImgRepositoryImpl implements FeedImgRepository {

    private final FeedImgJpaRepository feedImgJpaRepository;

    @Override
    public void save(FeedImg feedImg) {
        feedImgJpaRepository.save(FeedImgEntity.from(feedImg));
    }

    @Override
    public List<FeedImg> findAllFeedImgByFeedId(Long feedId) {
        return feedImgJpaRepository.findAllByFeedEntity_FeedId(feedId).stream()
                .map(FeedImgEntity::to)
                .toList();
    }

    @Override
    public void delete(FeedImg feedImg) {
        feedImgJpaRepository.delete(FeedImgEntity.from(feedImg));
    }
}
