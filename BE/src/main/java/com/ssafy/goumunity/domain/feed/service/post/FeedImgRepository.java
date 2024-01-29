package com.ssafy.goumunity.domain.feed.service.post;

import com.ssafy.goumunity.domain.feed.domain.FeedImg;
import java.util.List;
import java.util.Optional;

public interface FeedImgRepository {

    void save(FeedImg feedImg);

    Optional<FeedImg> findOneByFeedImgId(Long feedImgId);

    List<FeedImg> findAllByFeedId(Long feedId);
}
