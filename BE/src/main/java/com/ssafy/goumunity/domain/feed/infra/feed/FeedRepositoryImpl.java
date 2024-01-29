package com.ssafy.goumunity.domain.feed.infra.feed;

import com.ssafy.goumunity.domain.feed.domain.Feed;
import com.ssafy.goumunity.domain.feed.service.post.FeedRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class FeedRepositoryImpl implements FeedRepository {

    private final FeedJpaRepository feedJpaRepository;

    @Override
    public Optional<Feed> findOneByFeedId(Long feedId) {
        return feedJpaRepository.findOneByFeedId(feedId).map(FeedEntity::to);
    }

    @Override
    public List<Feed> findAllByUserId(Long userId) {
        return feedJpaRepository.findAllByUserId(userId).stream().map(FeedEntity::to).toList();
    }

    @Override
    public void save(FeedEntity feedEntity) {
        feedJpaRepository.save(feedEntity).to();
    }

    @Override
    public boolean existsByFeedId(Long feedId) {
        return feedJpaRepository.existsById(feedId);
    }
}
