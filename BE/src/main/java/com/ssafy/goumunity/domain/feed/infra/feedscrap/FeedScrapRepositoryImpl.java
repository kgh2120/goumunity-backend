package com.ssafy.goumunity.domain.feed.infra.feedscrap;

import com.ssafy.goumunity.domain.feed.domain.FeedScrap;
import com.ssafy.goumunity.domain.feed.service.post.FeedScrapRepository;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class FeedScrapRepositoryImpl implements FeedScrapRepository {

    private final FeedScrapJpaRepository feedScrapJpaRepository;
    private final JdbcTemplate jdbcTemplate;

    @Override
    public void create(FeedScrap feedScrap) {
        feedScrapJpaRepository.save(FeedScrapEntity.from(feedScrap));
    }

    @Override
    public void delete(Long feedScrapId) {
        feedScrapJpaRepository.deleteById(feedScrapId);
    }

    @Override
    public Optional<FeedScrap> findOneByUserIdAndFeedId(FeedScrap feedScrap) {
        return feedScrapJpaRepository
                .findByUserEntityIdAndFeedEntityId(feedScrap.getUserId(), feedScrap.getFeedId())
                .map(FeedScrapEntity::to);
    }

    @Override
    public boolean existByUserIdAndFeedId(Long userId, Long feedId) {
        return feedScrapJpaRepository.existsByUserEntityIdAndFeedEntityId(userId, feedId);
    }

    @Override
    public void deleteAllByFeedIds(List<Long> feedIds) {

        jdbcTemplate.batchUpdate(
                "delete from scrap f where f.feed_id = ? ",
                new BatchPreparedStatementSetter() {
                    @Override
                    public void setValues(PreparedStatement preparedStatement, int i) throws SQLException {
                        preparedStatement.setLong(1, feedIds.get(i));
                    }

                    @Override
                    public int getBatchSize() {
                        return feedIds.size();
                        //                return 100;
                    }
                });

        //        feedScrapJpaRepository.deleteAllByFeedIds(feedIds);
    }
}
