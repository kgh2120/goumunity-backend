package com.ssafy.goumunity.domain.feed.infra.reply;

import com.ssafy.goumunity.domain.feed.controller.response.ReplyResponse;
import com.ssafy.goumunity.domain.feed.domain.Reply;
import com.ssafy.goumunity.domain.feed.service.post.ReplyRepository;
import java.time.Instant;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ReplyRepositoryImpl implements ReplyRepository {

    private final ReplyJpaRepository replyJpaRepository;
    private final ReplyQueryDslRepository replyQueryDslRepository;

    @Override
    public void save(Reply reply) {
        replyJpaRepository.save(ReplyEntity.from(reply)).to();
    }

    @Override
    public Slice<ReplyResponse> findAllByCommentId(
            Long commentId, Instant instant, Pageable pageable) {
        return replyQueryDslRepository.findAllByCommentId(commentId, instant, pageable);
    }
}
