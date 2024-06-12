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

    private final FeedRepository feedRepository;
    private final FeedImgRepository feedImgRepository;
    private final FeedLikeRepository feedLikeRepository;
    private final FeedScrapRepository feedScrapRepository;
    private final CommentRepository commentRepository;
    private final CommentLikeRepository commentLikeRepository;
    private final ReplyRepository replyRepository;
    private final ReplyLikeRepository replyLikeRepository;

    @Override
    public void deleteFeed() {

    }

    /*
        FeedIds에 해당하는 CommentIds 불러오기.
        CommentsIds에 해당하는 ReplyIds 불러오기.
        1차 삭제 스크랩, 이미지, 피드 좋아요, 답글 좋아요
        2차 답글, 댓글 좋아요
        3차 댓글
        4차 피드
 */
    @Override
    public void clearUsersFeed(Long userId) {
        List<Long> feedIds = feedRepository.findAllFeedIdsByUserId(userId);
        List<Long> commentsIds = commentRepository.findAllCommentIdsInFeedIds(feedIds);
        List<Long> replyIds = replyRepository.findAllReplyIdsInFeedIds(commentsIds);

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
