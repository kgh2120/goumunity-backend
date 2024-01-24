package com.ssafy.goumunity.domain.feed.service;

import com.ssafy.goumunity.common.exception.feed.ResourceNotFoundException;
import com.ssafy.goumunity.domain.feed.controller.response.FeedImgResponse;
import com.ssafy.goumunity.domain.feed.domain.FeedImg;
import com.ssafy.goumunity.domain.feed.service.post.FeedImgRepository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FeedImgServiceImpl implements FeedImgService {

    private final FeedImgRepository feedImgRepository;

    @Override
    public FeedImgResponse findOneByFeedImgId(Long feedImgId) {
        Optional<FeedImg> feedImg = feedImgRepository.findOneByFeedImgId(feedImgId);

        if (feedImg.isEmpty()) {
            throw new ResourceNotFoundException("해당 사진을 찾을 수 없습니다.", this);
        } else {
            return feedImg.get().to();
        }
    }

    @Override
    public List<FeedImgResponse> findAllByFeedId(Long feedId) {
        return feedImgRepository.findAllByFeedId(feedId).stream()
                .map(item -> item.to())
                .collect(Collectors.toList());
    }
}
