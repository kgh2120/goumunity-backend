package com.ssafy.goumunity.domain.feed.infra.feed;

import com.ssafy.goumunity.domain.feed.controller.response.FeedResponse;
import com.ssafy.goumunity.domain.feed.domain.Feed;
import com.ssafy.goumunity.domain.feed.domain.FeedRecommendResource;
import com.ssafy.goumunity.domain.feed.domain.FeedSearchResource;
import com.ssafy.goumunity.domain.feed.domain.SavingResource;
import com.ssafy.goumunity.domain.feed.service.post.FeedRepository;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class FeedRepositoryImpl implements FeedRepository {

    private final FeedJpaRepository feedJpaRepository;
    private final FeedQueryDslRepository feedQueryDslRepository;
    private final JdbcTemplate jdbcTemplate;

    @Override
    public Feed create(Feed feed) {
        return feedJpaRepository.save(FeedEntity.from(feed)).to();
    }

    @Override
    public List<FeedRecommendResource> findFeed(Long userId, Long regionId) {
        return feedQueryDslRepository.findFeed(userId, regionId);
    }

    @Override
    public FeedResponse findOneFeed(Long userId, Long feedId) {
        return feedQueryDslRepository.findOneFeed(userId, feedId);
    }

    @Override
    public Optional<Feed> findOneById(Long feedId) {
        return feedJpaRepository.findById(feedId).map(FeedEntity::to);
    }

    @Override
    public List<FeedSearchResource> findAllFeedByUserId(Long userId) {
        return feedQueryDslRepository.findAllFeedByUserId(userId);
    }

    @Override
    public List<SavingResource> findAllSavingByUserId(Long userId) {
        return feedJpaRepository
                .findAllByUserEntity_IdAndPriceIsNotNullAndAfterPriceIsNotNull(userId)
                .stream()
                .map(SavingResource::from)
                .toList();
    }

    @Override
    public List<FeedSearchResource> findAllScrappedFeedByUserId(Long userId) {
        return feedQueryDslRepository.findAllScrappedFeedByUserId(userId);
    }

    @Override
    public List<FeedScrapRankingInterface> findFeedScrapRanking(Instant startTime, Instant endTime) {
        return feedJpaRepository.findFeedScrapRanking(startTime, endTime);
    }

    @Override
    public void modify(Feed feed) {
        feedJpaRepository.save(FeedEntity.from(feed)).to();
    }

    @Override
    public void delete(Long feedId) {
        feedJpaRepository.deleteById(feedId);
    }

    @Override
    public boolean existsByFeedId(Long feedId) {
        return feedJpaRepository.existsById(feedId);
    }

    @Override
    public Long countByUserId(Long userId) {
        return feedJpaRepository.countByUserEntity_Id(userId);
    }

    @Override
    public void deleteAllFeedByUserId(Long userId) {


        /*
            1. userId에 해당하는 feedIds 찾기.
            2. 1차
         */


        feedJpaRepository.deleteAllById(feedJpaRepository.findAllFeedIdsByUserId(userId));
        //        feedQueryDslRepository.deleteAllByUserId(userId);
    }

    @Override
    public List<Long> findAllFeedIdsByUserId(Long userId) {
//        return feedJpaRepository.findAllFeedIdsByUserId(userId);
        return jdbcTemplate.queryForList("select f.feed_id from Feed f where f.user_id = ?",new Object[]{userId}, Long.class);
    }

    @Override
    public void deleteAllByFeedIds(List<Long> feedIds) {
        feedJpaRepository.deleteAllByFeedIds(feedIds);
    }

    @Override
    public void deleteAllByUserId(Long userId) {
        jdbcTemplate.update("delete from Feed f where f.user_id = ?", userId);
//        feedJpaRepository.deleteAllByUserId(userId);
    }
}
