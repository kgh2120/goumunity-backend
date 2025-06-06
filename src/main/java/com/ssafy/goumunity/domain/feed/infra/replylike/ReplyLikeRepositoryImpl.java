package com.ssafy.goumunity.domain.feed.infra.replylike;

import com.ssafy.goumunity.domain.feed.domain.ReplyLike;
import com.ssafy.goumunity.domain.feed.service.post.ReplyLikeRepository;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Slf4j
@Repository
@RequiredArgsConstructor
public class ReplyLikeRepositoryImpl implements ReplyLikeRepository {

    private final ReplyLikeJpaRepository replyLikeJpaRepository;
    private final JdbcTemplate jdbcTemplate;

    @Override
    public void create(ReplyLike replyLike) {
        replyLikeJpaRepository.save(ReplyLikeEntity.from(replyLike));
    }

    @Override
    public Optional<ReplyLike> findOneByUserIdAndReplyId(Long userId, Long replyId) {
        return replyLikeJpaRepository
                .findByUserEntity_IdAndReplyEntity_Id(userId, replyId)
                .map(ReplyLikeEntity::to);
    }

    @Override
    public void delete(Long replyLikeId) {
        replyLikeJpaRepository.deleteById(replyLikeId);
    }

    @Override
    public boolean existsByReplyLike(ReplyLike replyLike) {
        return replyLikeJpaRepository.existsByUserEntity_IdAndReplyEntity_Id(
                replyLike.getUserId(), replyLike.getReplyId());
    }

    @Override
    public void deleteAllByReplyIds(List<Long> replyIds) {

        jdbcTemplate.batchUpdate(
                "delete from Reply_Like r where r.reply_id = ? ",
                new BatchPreparedStatementSetter() {
                    @Override
                    public void setValues(PreparedStatement preparedStatement, int i) throws SQLException {
                        preparedStatement.setLong(1, replyIds.get(i));
                    }

                    @Override
                    public int getBatchSize() {
                        return replyIds.size();
                    }
                });

    }

    @Override
    public int countReplyLikeInReplyId(List<Long> replyIds) {
        String inSql = String.join(",", Collections.nCopies(replyIds.size(), "?"));
        return jdbcTemplate.queryForObject(
                String.format("select count(*) from reply_like as rl where rl.reply_id in (%s)", inSql),
                replyIds.toArray(),
                Integer.class
        );
    }
}
