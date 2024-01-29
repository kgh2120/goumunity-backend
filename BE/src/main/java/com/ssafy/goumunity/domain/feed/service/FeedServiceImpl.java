package com.ssafy.goumunity.domain.feed.service;

import com.ssafy.goumunity.common.exception.feed.ResourceNotFoundException;
import com.ssafy.goumunity.domain.feed.controller.request.FeedRegistRequest;
import com.ssafy.goumunity.domain.feed.controller.response.FeedResponse;
import com.ssafy.goumunity.domain.feed.domain.Feed;
import com.ssafy.goumunity.domain.feed.infra.feed.FeedEntity;
import com.ssafy.goumunity.domain.feed.service.post.FeedRepository;
import com.ssafy.goumunity.domain.user.service.port.UserRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FeedServiceImpl implements FeedService {

    private final FeedRepository feedRepository;
    private final UserRepository userRepository;

    @Override
    public FeedResponse findOneByFeedId(Long feedId) {
        return FeedResponse.from(
                feedRepository
                        .findOneByFeedId(feedId)
                        .orElseThrow(() -> new ResourceNotFoundException("해당 피드를 찾을 수 없습니다.", this)));
    }

    @Override
    public List<FeedResponse> findAllByUserId(Long userId) {
        return feedRepository.findAllByUserId(userId).stream().map(FeedResponse::from).toList();
    }

    @Override
    public void save(FeedRegistRequest feedRegistRequest) {
        feedRepository.save(FeedEntity.from(Feed.from(feedRegistRequest)));
    }
}
