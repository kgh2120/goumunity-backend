package com.ssafy.goumunity.domain.feed.infra.comment;

import com.ssafy.goumunity.domain.feed.controller.response.CommentResponse;
import com.ssafy.goumunity.domain.feed.domain.Comment;
import com.ssafy.goumunity.domain.feed.service.post.CommentRepository;
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
public class CommentRepositoryImpl implements CommentRepository {

    private final CommentJpaRepository commentJpaRepository;
    private final CommentQueryDslRepository commentQueryDslRepository;
    private final JdbcTemplate jdbcTemplate;

    @Override
    public Comment create(Comment comment) {
        return commentJpaRepository.save(CommentEntity.from(comment)).to();
    }

    @Override
    public Slice<CommentResponse> findAllByFeedId(
            Long userId, Long feedId, Instant time, Pageable pageable) {
        return commentQueryDslRepository.findAllByFeedId(userId, feedId, time, pageable);
    }

    @Override
    public Optional<Comment> findOneById(Long commentId) {
        return commentJpaRepository.findById(commentId).map(CommentEntity::to);
    }

    @Override
    public CommentResponse findOneComment(Long userId, Long commentId) {
        return commentQueryDslRepository.findOneComment(userId, commentId);
    }

    @Override
    public void modify(Comment comment) {
        commentJpaRepository.save(CommentEntity.from(comment)).to();
    }

    @Override
    public void delete(Long commentId) {
        commentJpaRepository.deleteById(commentId);
    }

    @Override
    public boolean existsById(Long commentId) {
        return commentJpaRepository.existsById(commentId);
    }

    @Override
    public List<Long> findAllCommentIdsInFeedIds(List<Long> feedIds) {
        String inSql = String.join(",", Collections.nCopies(feedIds.size(), "?"));
        return jdbcTemplate.queryForList(
                String.format("select c.comment_id from Comment c where c.feed_id in (%s)", inSql),
                feedIds.toArray(),
                Long.class);
        //        return commentJpaRepository.findAllCommentIdsInFeedIds(feedIds);
    }

    @Override
    public void deleteAllByIds(List<Long> feedIds) {

        jdbcTemplate.batchUpdate(
                "delete from Comment c where c.feed_id = ? ",
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
        //        commentJpaRepository.deleteAllByIds(commentsIds);
        //        commentJpaRepository.deleteAllByFeedIds(feedIds);
    }
}
