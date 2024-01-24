package com.ssafy.goumunity.domain.chat.service;

import com.ssafy.goumunity.common.util.SliceResponse;
import com.ssafy.goumunity.domain.chat.domain.Hashtag;
import com.ssafy.goumunity.domain.chat.service.port.HashtagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class HashtagServiceImpl implements HashtagService {

    private final HashtagRepository hashtagRepository;

    @Override
    public SliceResponse<Hashtag> findAllByHashtagName(
            String hashtagName, Pageable pageable, Long time) {
        return hashtagRepository.findAllByHashtagName(hashtagName, time, pageable);
    }
}
