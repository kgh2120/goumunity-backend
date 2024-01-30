package com.ssafy.goumunity.domain.feed.service;

import com.ssafy.goumunity.common.exception.feed.ResourceNotFoundException;
import com.ssafy.goumunity.domain.feed.controller.request.FeedRequest;
import com.ssafy.goumunity.domain.feed.controller.response.FeedResponse;
import com.ssafy.goumunity.domain.feed.domain.Feed;
import com.ssafy.goumunity.domain.feed.domain.FeedImg;
import com.ssafy.goumunity.domain.feed.infra.feed.FeedEntity;
import com.ssafy.goumunity.domain.feed.service.post.FeedImageUploader;
import com.ssafy.goumunity.domain.feed.service.post.FeedImgRepository;
import com.ssafy.goumunity.domain.feed.service.post.FeedRepository;
import com.ssafy.goumunity.domain.user.domain.User;
import com.ssafy.goumunity.domain.user.exception.UserErrorCode;
import com.ssafy.goumunity.domain.user.exception.UserException;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
@Transactional
public class FeedServiceImpl implements FeedService {

    private final FeedRepository feedRepository;
    private final FeedImgRepository feedImgRepository;
    private final FeedImageUploader feedImageUploader;

    @Override
    public void createFeed(Long userId, FeedRequest.Create feedRequest, List<MultipartFile> images) {
        Feed createdFeed = feedRepository.save(Feed.from(feedRequest, userId));

        if (!images.isEmpty()) {
            for (int seq = 1; seq <= images.size(); seq++) {
                String savedUrl = feedImageUploader.uploadFeedImage(images.get(seq - 1));
                feedImgRepository.save(FeedImg.from(createdFeed.getFeedId(), savedUrl, seq));
            }
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Slice<FeedResponse> findFeed(Long time, Pageable pageable) {
        return feedRepository.findFeed(Instant.ofEpochMilli(time), pageable);
    }

    //    @Override
    //    @Transactional(readOnly = true)
    //    public FeedResponse findOneByFeedId(Long feedId) {
    //        return FeedResponse.from(
    //                feedRepository
    //                        .findOneByFeedId(feedId)
    //                        .orElseThrow(() -> new ResourceNotFoundException("해당 피드를 찾을 수 없습니다.",
    // this)));
    //    }
    //
    //    @Override
    //    @Transactional(readOnly = true)
    //    public List<FeedResponse> findAllByUserId(Long userId) {
    //        return feedRepository.findAllByUserId(userId).stream().map(FeedResponse::from).toList();
    //    }

    @Override
    public void deleteOneByFeedId(Long feedId, User user) {
        Optional<Feed> feed = feedRepository.findOneByFeedId(feedId);
        if (feed.isEmpty()) throw new ResourceNotFoundException(feedId + " 번 피드를 찾을 수 없습니다.", this);
        else if (!feed.get().getUserId().equals(user.getId()))
            throw new UserException(UserErrorCode.INVALID_USER);
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
