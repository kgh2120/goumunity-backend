package com.ssafy.goumunity.domain.feed.infra.commentlike;

import com.ssafy.goumunity.domain.feed.domain.CommentLike;
import com.ssafy.goumunity.domain.feed.service.post.CommentLikeRepository;
import java.sql.PreparedStatement;
import java.sql.SQLException;
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
public class CommentLikeRepositoryImpl implements CommentLikeRepository {

    private final CommentLikeJpaRepository commentLikeJpaRepository;
    private final JdbcTemplate jdbcTemplate;

    @Override
    public void create(CommentLike commentLike) {
        commentLikeJpaRepository.save(CommentLikeEntity.from(commentLike));
    }

    @Override
    public Optional<CommentLike> findOneByUserIdAndCommentId(Long userId, Long commentId) {
        return commentLikeJpaRepository
                .findByUserEntity_IdAndCommentEntity_Id(userId, commentId)
                .map(CommentLikeEntity::to);
    }

    @Override
    public void delete(Long commentLikeId) {
        commentLikeJpaRepository.deleteById(commentLikeId);
    }

    @Override
    public boolean existByCommentLike(CommentLike commentLike) {
        return commentLikeJpaRepository.existsByUserEntity_IdAndCommentEntity_Id(
                commentLike.getUserId(), commentLike.getCommentId());
    }

    @Override
    public void deleteAllByCommentsIds(List<Long> commentsIds) {
        jdbcTemplate.batchUpdate(
                "delete from Comment_Like c where c.comment_id = ? ",
                new BatchPreparedStatementSetter() {
                    @Override
                    public void setValues(PreparedStatement preparedStatement, int i) throws SQLException {
                        preparedStatement.setLong(1, commentsIds.get(i));
                    }

                    @Override
                    public int getBatchSize() {
                        return commentsIds.size();
                    }
                });
    }
}
