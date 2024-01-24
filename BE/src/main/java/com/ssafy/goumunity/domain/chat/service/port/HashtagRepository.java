package com.ssafy.goumunity.domain.chat.service.port;

import com.ssafy.goumunity.common.util.SliceResponse;
import com.ssafy.goumunity.domain.chat.domain.Hashtag;
import org.springframework.data.domain.Pageable;

public interface HashtagRepository {
    SliceResponse<Hashtag> findAllByHashtagName(String hashtagName, Long time, Pageable pageable);
}
