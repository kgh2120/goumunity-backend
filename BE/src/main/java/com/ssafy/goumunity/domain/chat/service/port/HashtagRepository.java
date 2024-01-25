package com.ssafy.goumunity.domain.chat.service.port;

import com.ssafy.goumunity.common.util.SliceResponse;
import com.ssafy.goumunity.domain.chat.domain.Hashtag;
import java.util.Optional;
import org.springframework.data.domain.Pageable;

public interface HashtagRepository {
    SliceResponse<Hashtag> findAllByHashtagName(String hashtagName, Long time, Pageable pageable);

    Hashtag save(Hashtag hashtag);

    boolean existsOneByHashtagName(String hashtagName);

    Optional<Hashtag> findOneByHashtagId(Long id);

    boolean existsOneByHashtagId(Long hashtagId);
}
