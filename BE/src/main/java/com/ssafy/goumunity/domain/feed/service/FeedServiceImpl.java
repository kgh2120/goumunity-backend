package com.ssafy.goumunity.domain.feed.service;

import com.ssafy.goumunity.common.exception.feed.ResourceNotFoundException;
import com.ssafy.goumunity.domain.feed.controller.response.FeedResponse;
import com.ssafy.goumunity.domain.feed.domain.Feed;
import com.ssafy.goumunity.domain.feed.service.post.FeedRepository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FeedServiceImpl implements FeedService {

    private final FeedRepository feedRepository;

    @Override
    public FeedResponse findOneByFeedId(Long feedId) {
        Optional<Feed> feed = feedRepository.findOneByFeedId(feedId);
        if (feed.isEmpty()) {
            throw new ResourceNotFoundException("해당 피드를 찾을 수 없습니다.", this);
        } else {
            return feedRepository.findOneByFeedId(feedId).get().to();
        }
    }

    @Override
    public List<FeedResponse> findAllByUserId(Long userId) {
        return feedRepository.findAllByUserId(userId).stream()
                .map(item -> item.to())
                .collect(Collectors.toList());
    }
}
