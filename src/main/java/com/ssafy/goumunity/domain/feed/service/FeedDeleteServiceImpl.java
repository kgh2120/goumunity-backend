package com.ssafy.goumunity.domain.feed.service;

import com.ssafy.goumunity.domain.feed.service.post.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class FeedDeleteServiceImpl implements FeedDeleteService {

    public static final int PERFORMANCE_CROSSOVER_POINT = 200_000;
    private final FeedRepository feedRepository;
    private final FeedImgRepository feedImgRepository;
    private final FeedLikeRepository feedLikeRepository;
    private final FeedScrapRepository feedScrapRepository;
    private final CommentRepository commentRepository;
    private final CommentLikeRepository commentLikeRepository;
    private final ReplyRepository replyRepository;
    private final ReplyLikeRepository replyLikeRepository;

    @Override
    public void clearUsersFeed(Long userId) {
        List<Long> feedIds = feedRepository.findAllFeedIdsByUserId(userId);
        List<Long> commentsIds = commentRepository.findAllCommentIdsInFeedIds(feedIds);
        List<Long> replyIds = replyRepository.findAllReplyIdsInFeedIds(commentsIds);

        int totalSize = feedIds.size() + commentsIds.size() + replyIds.size();

        if (totalSize > PERFORMANCE_CROSSOVER_POINT) {
            feedRepository.deleteAllFeedByUserId(userId);
            return;
        }

        replyLikeRepository.deleteAllByReplyIds(replyIds);
        feedImgRepository.deleteAllByFeedIds(feedIds);
        feedLikeRepository.deleteAllByFeedIds(feedIds);
        feedScrapRepository.deleteAllByFeedIds(feedIds);
        commentLikeRepository.deleteAllByCommentsIds(commentsIds);
        replyRepository.deleteAllByReplyIds(commentsIds);
        commentRepository.deleteAllByIds(feedIds);
        feedRepository.deleteAllByUserId(userId);
    }
}
