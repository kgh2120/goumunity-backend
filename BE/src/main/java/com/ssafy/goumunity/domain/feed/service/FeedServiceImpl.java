package com.ssafy.goumunity.domain.feed.service;

import com.ssafy.goumunity.domain.feed.controller.request.FeedImgRequest;
import com.ssafy.goumunity.domain.feed.controller.request.FeedRequest;
import com.ssafy.goumunity.domain.feed.controller.response.FeedRecommendResponse;
import com.ssafy.goumunity.domain.feed.controller.response.FeedResponse;
import com.ssafy.goumunity.domain.feed.domain.Feed;
import com.ssafy.goumunity.domain.feed.domain.FeedImg;
import com.ssafy.goumunity.domain.feed.domain.FeedRecommendResource;
import com.ssafy.goumunity.domain.feed.domain.FeedWeight;
import com.ssafy.goumunity.domain.feed.exception.FeedErrorCode;
import com.ssafy.goumunity.domain.feed.exception.FeedException;
import com.ssafy.goumunity.domain.feed.service.post.FeedImageUploader;
import com.ssafy.goumunity.domain.feed.service.post.FeedImgRepository;
import com.ssafy.goumunity.domain.feed.service.post.FeedRepository;
import com.ssafy.goumunity.domain.user.domain.User;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import lombok.RequiredArgsConstructor;
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

        if (images != null && !images.isEmpty()) {
            for (int seq = 1; seq <= images.size(); seq++) {
                String savedUrl = feedImageUploader.uploadFeedImage(images.get(seq - 1));
                feedImgRepository.save(FeedImg.from(createdFeed.getFeedId(), savedUrl, seq));
            }
        }
    }

    @Override
    @Transactional(readOnly = true)
    public FeedRecommendResponse findFeed(User user, Long time, Long regionId) {
        List<FeedRecommendResource> feeds =
                feedRepository.findFeed(user.getId(), Instant.ofEpochMilli(time), regionId);
        List<FeedWeight> feedWeights = feeds.stream().map(item -> FeedWeight.from(item, user)).toList();
        Collections.sort(feedWeights);

        List<FeedRecommendResource> result = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            result.add(feedWeights.get(i).getFeedRecommendResource());
        }

        return FeedRecommendResponse.builder().feedRecommends(result).build();
    }

    @Override
    @Transactional(readOnly = true)
    public FeedResponse findOneByFeedId(Long feedId) {
        return feedRepository.findOneFeed(feedId);
    }

    @Override
    public void modifyFeed(
            Long userId, Long feedId, FeedRequest.Modify feedRequest, List<MultipartFile> images) {
        Feed originalFeed =
                feedRepository
                        .findOneById(feedId)
                        .orElseThrow(() -> new FeedException(FeedErrorCode.FEED_NOT_FOUND));

        // 조회해온 feed의 작성자와 세션에 로그인 되어 있는 유저가 다르면 exception 발생
        originalFeed.checkUser(userId);

        // 기존 feed images 삭제
        List<FeedImg> originalFeedImages = feedImgRepository.findAllFeedImgByFeedId(feedId);
        for (FeedImg img : originalFeedImages) {
            feedImgRepository.delete(img);
        }

        // feed 이미지 수정
        if (feedRequest.getFeedImages() == null && images != null) {
            // 기존에 있던 사진이 모두 없고 새로운 사진만 생겼을 경우
            for (int seq = 1; seq <= images.size(); seq++) {
                String savedUrl = feedImageUploader.uploadFeedImage(images.get(seq - 1));
                feedImgRepository.save(FeedImg.from(originalFeed.getFeedId(), savedUrl, seq));
            }
        } else if (feedRequest.getFeedImages() != null && images == null) {
            // 새로운 사진은 없고 기존에 있던 사진에 대한 수정 사항만 있을 경우
            List<FeedImgRequest.Modify> feedImages = feedRequest.getFeedImages();
            feedImages.sort(Comparator.comparingInt(FeedImgRequest.Modify::getSequence));

            for (FeedImgRequest.Modify img : feedImages) {
                feedImgRepository.save(
                        FeedImg.from(originalFeed.getFeedId(), img.getImgSrc(), img.getSequence()));
            }
        } else if (feedRequest.getFeedImages() != null && images != null) {
            // 기존에 있던 사진도 있고 새로운 사진도 있을 경우
            List<FeedImgRequest.Modify> feedImages = feedRequest.getFeedImages();
            feedImages.sort(Comparator.comparingInt(FeedImgRequest.Modify::getSequence));

            int nowIdx = 1;
            int newImageIdx = 0;
            for (int idx = 0; idx < feedImages.size(); ) {
                FeedImgRequest.Modify feedImage = feedImages.get(idx);
                if (feedImage.getSequence() == nowIdx) {
                    feedImgRepository.save(
                            FeedImg.from(originalFeed.getFeedId(), feedImage.getImgSrc(), nowIdx));
                    idx++;
                } else {
                    if (newImageIdx < images.size()) {
                        String savedUrl = feedImageUploader.uploadFeedImage(images.get(newImageIdx));
                        feedImgRepository.save(FeedImg.from(originalFeed.getFeedId(), savedUrl, nowIdx));
                        newImageIdx++;
                    }
                }
                nowIdx++;
            }

            for (int idx = newImageIdx; idx < images.size(); idx++) {
                String savedUrl = feedImageUploader.uploadFeedImage(images.get(newImageIdx));
                feedImgRepository.save(FeedImg.from(originalFeed.getFeedId(), savedUrl, nowIdx));
            }
        }

        // feed 내용 수정
        feedRepository.modify(Feed.from(originalFeed, feedRequest));
    }

    @Override
    public void deleteFeed(Long userId, Long feedId) {
        Feed originalFeed =
                feedRepository
                        .findOneById(feedId)
                        .orElseThrow(() -> new FeedException(FeedErrorCode.FEED_NOT_FOUND));

        // 조회해온 feed의 작성자와 세션에 로그인 되어 있는 유저가 다르면 exception 발생
        originalFeed.checkUser(userId);

        feedRepository.delete(originalFeed);
    }
}
