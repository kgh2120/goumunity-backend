package com.ssafy.goumunity.domain.feed.infra.feed;

import com.ssafy.goumunity.domain.feed.controller.response.FeedResponse;
import com.ssafy.goumunity.domain.feed.domain.Feed;
import com.ssafy.goumunity.domain.feed.service.post.FeedRepository;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class FeedRepositoryImpl implements FeedRepository {

    private final FeedJpaRepository feedJpaRepository;
    private final FeedQueryDslRepository feedQueryDslRepository;

    @Override
    public Feed save(Feed feed) {
        return feedJpaRepository.save(FeedEntity.from(feed)).to();
    }

    @Override
    public Slice<FeedResponse> find(Instant time, Pageable pageable) {
        return feedQueryDslRepository.find(time, pageable);
    }

    @Override
    public Optional<Feed> findOneByFeedId(Long feedId) {
        return feedJpaRepository.findOneByFeedId(feedId).map(FeedEntity::to);
    }

    @Override
    public List<Feed> findAllByUserId(Long userId) {
        return feedJpaRepository.findAllByUserId(userId).stream().map(FeedEntity::to).toList();
    }

    @Override
    public void delete(FeedEntity feedEntity) {
        feedJpaRepository.delete(feedEntity);
    }

    @Override
    public boolean existsByFeedId(Long feedId) {
        return feedJpaRepository.existsById(feedId);
    }
}
