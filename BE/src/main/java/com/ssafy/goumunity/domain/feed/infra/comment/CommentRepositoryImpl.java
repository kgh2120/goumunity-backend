package com.ssafy.goumunity.domain.feed.infra.comment;

import com.ssafy.goumunity.domain.feed.controller.response.CommentResponse;
import com.ssafy.goumunity.domain.feed.domain.Comment;
import com.ssafy.goumunity.domain.feed.service.post.CommentRepository;
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
    public Comment save(Comment comment) {
        return commentJpaRepository.save(CommentEntity.from(comment)).to();
    }

    @Override
    public Slice<CommentResponse> findAllByFeedId(Long feedId, Pageable pageable) {
        return commentQueryDslRepository.findAllByFeedId(feedId, pageable);
    }
}
