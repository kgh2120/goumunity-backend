package com.ssafy.goumunity.domain.chat.service;

import com.ssafy.goumunity.common.util.SliceResponse;
import com.ssafy.goumunity.domain.chat.controller.request.HashtagCreateRequest;
import com.ssafy.goumunity.domain.chat.domain.Hashtag;
import org.springframework.data.domain.Pageable;

public interface HashtagService {
    SliceResponse<Hashtag> findAllByHashtagName(String hashtagName, Pageable pageable, Long time);

    Hashtag createHashtag(HashtagCreateRequest dto);
}
