package com.ssafy.goumunity.domain.feed.infra.comment;

import com.ssafy.goumunity.domain.feed.controller.response.CommentResponse;
import com.ssafy.goumunity.domain.feed.domain.Comment;
import com.ssafy.goumunity.domain.feed.service.post.CommentRepository;
import java.time.Instant;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CommentRepositoryImpl implements CommentRepository {

    private final CommentJpaRepository commentJpaRepository;
    private final CommentQueryDslRepository commentQueryDslRepository;

    @Override
    public Comment create(Comment comment) {
        return commentJpaRepository.save(CommentEntity.from(comment)).to();
    }

    @Override
    public Slice<CommentResponse> findAllByFeedId(Long feedId, Instant time, Pageable pageable) {
        return commentQueryDslRepository.findAllByFeedId(feedId, time, pageable);
    }

    @Override
    public Optional<Comment> findOneById(Long commentId) {
        return commentJpaRepository.findById(commentId).map(CommentEntity::to);
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
}
