package com.ssafy.goumunity.domain.feed.infra.feedimg;

import com.ssafy.goumunity.domain.feed.domain.FeedImg;
import com.ssafy.goumunity.domain.feed.service.post.FeedImgRepository;
import java.util.List;
import java.util.Optional;
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
    public Optional<FeedImg> findOneByFeedImgId(Long feedImgId) {
        return feedImgJpaRepository.findOneByFeedImgId(feedImgId).map(FeedImgEntity::to);
    }

    @Override
    public List<FeedImg> findAllByFeedId(Long feedId) {
        return feedImgJpaRepository.findAllByFeedEntity_FeedIdOrderBySequenceAsc(feedId).stream()
                .map(FeedImgEntity::to)
                .toList();
    }
}
