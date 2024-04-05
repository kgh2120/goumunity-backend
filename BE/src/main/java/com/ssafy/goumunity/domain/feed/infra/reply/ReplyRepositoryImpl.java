package com.ssafy.goumunity.domain.feed.infra.reply;

import com.ssafy.goumunity.domain.feed.controller.response.ReplyResponse;
import com.ssafy.goumunity.domain.feed.domain.Reply;
import com.ssafy.goumunity.domain.feed.service.post.ReplyRepository;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.Instant;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ReplyRepositoryImpl implements ReplyRepository {

    private final ReplyJpaRepository replyJpaRepository;
    private final ReplyQueryDslRepository replyQueryDslRepository;
    private final JdbcTemplate jdbcTemplate;

    @Override
    public Reply create(Reply reply) {
        return replyJpaRepository.save(ReplyEntity.from(reply)).to();
    }

    @Override
    public Slice<ReplyResponse> findAllByCommentId(
            Long userId, Long commentId, Instant instant, Pageable pageable) {
        return replyQueryDslRepository.findAllByCommentId(userId, commentId, instant, pageable);
    }

    @Override
    public ReplyResponse findOneReply(Long userId, Long replyId) {
        return replyQueryDslRepository.findOneReply(userId, replyId);
    }

    @Override
    public Optional<Reply> findOneById(Long replyId) {
        return replyJpaRepository.findById(replyId).map(ReplyEntity::to);
    }

    @Override
    public void modify(Reply reply) {
        replyJpaRepository.save(ReplyEntity.from(reply));
    }

    @Override
    public void delete(Long replyId) {
        replyJpaRepository.deleteById(replyId);
    }

    @Override
    public boolean existsByReplyId(Long replyId) {
        return replyJpaRepository.existsById(replyId);
    }

    @Override
    public List<Long> findAllReplyIdsInFeedIds(List<Long> commentsIds) {
        String inSql = String.join(",", Collections.nCopies(commentsIds.size(), "?"));
        return jdbcTemplate.queryForList(
                String.format("select r.reply_id from Reply r where r.comment_id in (%s)", inSql),
                commentsIds.toArray(),
                Long.class);

        //        return replyJpaRepository.findAllReplyIdsInCommentIds(commentsIds);
    }

    @Override
    public void deleteAllByReplyIds(List<Long> replyIds) {

        jdbcTemplate.batchUpdate(
                "delete from Reply r where r.comment_id = ? ",
                new BatchPreparedStatementSetter() {
                    @Override
                    public void setValues(PreparedStatement preparedStatement, int i) throws SQLException {
                        preparedStatement.setLong(1, replyIds.get(i));
                    }

                    @Override
                    public int getBatchSize() {
                        return replyIds.size();
                        //                return 100;
                    }
                });

        //        replyJpaRepository.deleteAllByCommentIds(replyIds);
    }
}
