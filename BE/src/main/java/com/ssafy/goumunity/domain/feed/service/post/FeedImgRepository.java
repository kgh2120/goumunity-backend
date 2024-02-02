package com.ssafy.goumunity.domain.feed.service.post;

import com.ssafy.goumunity.domain.feed.domain.FeedImg;
import java.util.List;

public interface FeedImgRepository {

    void save(FeedImg feedImg);

    List<FeedImg> findAllFeedImgByFeedId(Long feedId);

    void delete(Long feedImgId);
}
