package com.ssafy.goumunity.domain.feed.infra.feedimg;

import com.ssafy.goumunity.domain.feed.domain.FeedImg;
import com.ssafy.goumunity.domain.feed.service.post.FeedImgRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class FeedImgRepositoryImpl implements FeedImgRepository {

    private final FeedImgJpaRepository feedImgJpaRepository;

    @Override
    public void save(FeedImg feedImg) {
        feedImgJpaRepository.save(FeedImgEntity.from(feedImg));
    }
}
