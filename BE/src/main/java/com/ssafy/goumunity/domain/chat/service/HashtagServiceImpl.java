package com.ssafy.goumunity.domain.chat.service;

import com.ssafy.goumunity.domain.chat.service.port.HashtagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class HashtagServiceImpl {

    private final HashtagRepository hashtagRepository;
}
