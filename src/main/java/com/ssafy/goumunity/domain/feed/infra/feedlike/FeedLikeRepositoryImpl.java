package com.ssafy.goumunity.domain.feed.infra.feedlike;

import com.ssafy.goumunity.domain.feed.domain.FeedLike;
import com.ssafy.goumunity.domain.feed.service.post.FeedLikeRepository;
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
public class FeedLikeRepositoryImpl implements FeedLikeRepository {

    private final FeedLikeJpaRepository feedLikeJpaRepository;
    private final JdbcTemplate jdbcTemplate;

    @Override
    public void create(FeedLike feedLike) {
        feedLikeJpaRepository.save(FeedLikeEntity.from(feedLike));
    }

    @Override
    public Optional<FeedLike> findOneByUserIdAndFeedId(Long userId, Long feedId) {
        return feedLikeJpaRepository
                .findByUserEntity_IdAndAndFeedEntity_Id(userId, feedId)
                .map(FeedLikeEntity::to);
    }

    @Override
    public void delete(Long feedLikeId) {
        feedLikeJpaRepository.deleteById(feedLikeId);
    }

    @Override
    public boolean existsByFeedLike(FeedLike feedLike) {
        return feedLikeJpaRepository.existsByUserEntity_IdAndFeedEntity_Id(
                feedLike.getUserId(), feedLike.getFeedId());
    }

    @Override
    public void deleteAllByFeedIds(List<Long> feedIds) {
        jdbcTemplate.batchUpdate(
                "delete from Feed_Like f where f.feed_id = ? ",
                new BatchPreparedStatementSetter() {
                    @Override
                    public void setValues(PreparedStatement preparedStatement, int i) throws SQLException {
                        preparedStatement.setLong(1, feedIds.get(i));
                    }

                    @Override
                    public int getBatchSize() {
                        return feedIds.size();
                    }
                });
    }
}
