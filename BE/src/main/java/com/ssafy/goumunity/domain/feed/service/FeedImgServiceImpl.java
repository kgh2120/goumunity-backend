package com.ssafy.goumunity.domain.feed.service;

import com.ssafy.goumunity.common.exception.feed.ResourceNotFoundException;
import com.ssafy.goumunity.domain.feed.controller.response.FeedImgResponse;
import com.ssafy.goumunity.domain.feed.service.post.FeedImgRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FeedImgServiceImpl implements FeedImgService {

    private final FeedImgRepository feedImgRepository;

    @Override
    public FeedImgResponse findOneByFeedImgId(Long feedImgId) {
        return FeedImgResponse.from(
                feedImgRepository
                        .findOneByFeedImgId(feedImgId)
                        .orElseThrow(() -> new ResourceNotFoundException("해당 사진을 찾을 수 없습니다.", this)));
    }

    @Override
    public List<FeedImgResponse> findAllByFeedId(Long feedId) {
        return feedImgRepository.findAllByFeedId(feedId).stream().map(FeedImgResponse::from).toList();
    }
}
