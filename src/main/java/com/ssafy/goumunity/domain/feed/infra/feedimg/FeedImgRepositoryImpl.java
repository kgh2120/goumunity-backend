package com.ssafy.goumunity.domain.feed.infra.feedimg;

import com.ssafy.goumunity.domain.feed.domain.FeedImg;
import com.ssafy.goumunity.domain.feed.service.post.FeedImgRepository;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class FeedImgRepositoryImpl implements FeedImgRepository {

    private final FeedImgJpaRepository feedImgJpaRepository;
    private final JdbcTemplate jdbcTemplate;

    @Override
    public void save(FeedImg feedImg) {
        feedImgJpaRepository.save(FeedImgEntity.from(feedImg));
    }

    @Override
    public List<FeedImg> findAllFeedImgByFeedId(Long feedId) {
        return feedImgJpaRepository.findAllByFeedEntity_Id(feedId).stream()
                .map(FeedImgEntity::to)
                .toList();
    }

    @Override
    public void delete(Long feedImgId) {
        feedImgJpaRepository.deleteById(feedImgId);
    }

    @Override
    public void deleteAllByFeedIds(List<Long> feedIds) {

        jdbcTemplate.batchUpdate(
                "delete from Feed_Img f where f.feed_id = ? ",
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
        //        feedImgJpaRepository.deleteAllByFeedIds(feedIds);
    }
}
