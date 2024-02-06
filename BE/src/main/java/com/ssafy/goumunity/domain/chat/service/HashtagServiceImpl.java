package com.ssafy.goumunity.domain.chat.service;

import com.ssafy.goumunity.common.util.SliceResponse;
import com.ssafy.goumunity.domain.chat.controller.request.HashtagCreateRequest;
import com.ssafy.goumunity.domain.chat.domain.Hashtag;
import com.ssafy.goumunity.domain.chat.exception.ChatErrorCode;
import com.ssafy.goumunity.domain.chat.exception.ChatException;
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

    @Override
    public Hashtag getHashtag(String name) {
        return hashtagRepository
                .findOneHashtagByName(name)
                .orElseGet(() -> hashtagRepository.save(Hashtag.create(name)));
    }

    @Override
    public boolean existsOneByHashtagId(Long hashtagId) {
        return hashtagRepository.existsOneByHashtagId(hashtagId);
    }

    private void verifyDuplicateHashtag(HashtagCreateRequest dto) {
        if (hashtagRepository.existsOneByHashtagName(dto.getName())) {
            throw new ChatException(ChatErrorCode.ALREADY_CREATED_HASHTAG);
        }
    }
}
