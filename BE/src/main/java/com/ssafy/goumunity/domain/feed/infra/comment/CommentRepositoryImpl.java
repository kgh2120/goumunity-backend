package com.ssafy.goumunity.domain.feed.infra.comment;

import com.ssafy.goumunity.domain.feed.domain.Comment;
import com.ssafy.goumunity.domain.feed.service.post.CommentRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CommentRepositoryImpl implements CommentRepository {

    private final CommentJpaRepository commentJpaRepository;

    @Override
    public Comment save(Comment comment) {
        return commentJpaRepository.save(CommentEntity.from(comment)).to();
    }

    @Override
    public List<Comment> findAllByFeedId(Long feedId) {
        return commentJpaRepository.findAllByFeedEntity_FeedId(feedId).stream()
                .map(CommentEntity::to)
                .toList();
    }
}
