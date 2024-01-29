package com.ssafy.goumunity.domain.feed.service;

import com.ssafy.goumunity.domain.feed.controller.request.FeedLikeCountRequest;
import com.ssafy.goumunity.domain.feed.controller.request.FeedLikeRequest;
import com.ssafy.goumunity.domain.feed.controller.response.FeedLikeCountResponse;
import com.ssafy.goumunity.domain.feed.domain.FeedLike;
import com.ssafy.goumunity.domain.feed.infra.feedlike.FeedLikeEntity;
import com.ssafy.goumunity.domain.feed.service.post.FeedLikeRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FeedLikeServiceImpl implements FeedLikeService {

    private final FeedLikeRepository feedLikeRepository;

    @Override
    public boolean pushLikeButton(FeedLikeRequest feedLikeRequest, Long nowUserId) {

        // TODO : user통합시 현재유저검증로직 추가

        Optional<FeedLike> feedLike =
                feedLikeRepository.findOneByFeedIdAndUserId(
                        feedLikeRequest.getFeedId(), feedLikeRequest.getUserId());

        if (feedLike.isEmpty()) {
            feedLikeRepository.save(FeedLikeEntity.from(FeedLike.from(feedLikeRequest)));
            return true;
        } else {
            feedLikeRepository.delete(FeedLikeEntity.from(feedLike.get()));
            return false;
        }
    }

    @Override
    public FeedLikeCountResponse countFeedLikeByFeedId(FeedLikeCountRequest feedLikeCountRequest) {
        return FeedLikeCountResponse.from(
                feedLikeRepository.countFeedLikeByFeedId(feedLikeCountRequest.getFeedId()));
    }
}
