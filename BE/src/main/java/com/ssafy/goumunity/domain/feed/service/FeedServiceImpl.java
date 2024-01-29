package com.ssafy.goumunity.domain.feed.service;

import com.ssafy.goumunity.common.exception.CustomErrorCode;
import com.ssafy.goumunity.common.exception.CustomException;
import com.ssafy.goumunity.common.exception.feed.ResourceNotFoundException;
import com.ssafy.goumunity.domain.feed.controller.request.FeedRegistRequest;
import com.ssafy.goumunity.domain.feed.controller.response.FeedResponse;
import com.ssafy.goumunity.domain.feed.domain.Feed;
import com.ssafy.goumunity.domain.feed.infra.feed.FeedEntity;
import com.ssafy.goumunity.domain.feed.service.post.FeedRepository;
import com.ssafy.goumunity.domain.user.domain.User;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FeedServiceImpl implements FeedService {

    private final FeedRepository feedRepository;

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
    public Feed save(FeedRegistRequest feedRegistRequest) {
        return feedRepository.save(FeedEntity.from(Feed.from(feedRegistRequest)));
    }

    @Override
    public void deleteOneByFeedId(Long feedId, User user) {
        Optional<Feed> feed = feedRepository.findOneByFeedId(feedId);
        if (feed.isEmpty()) throw new ResourceNotFoundException(feedId + " 번 피드를 찾을 수 없습니다.", this);
        else if (feed.get().getUserId() != user.getId())
            throw new CustomException(CustomErrorCode.INVALID_USER);
        else {
            // 1. 피드 삭제
            feedRepository.delete(FeedEntity.from(feed.get()));
            // TODO : 2. 피드별 코멘트별 답글 삭제
            // 3. 피드별 코멘트별 좋아요 삭제 onDelete추가
            // 4. 피드별 코멘트 삭제 onDelete추가
            // 5. 피드 이미지 삭제 onDelete추가
            // 6. 피드 좋아요 삭제 onDelete추가
        }
    }
}
