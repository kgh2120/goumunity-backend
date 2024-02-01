package com.ssafy.goumunity.domain.chat.infra;

import com.ssafy.goumunity.common.util.SliceResponse;
import com.ssafy.goumunity.domain.chat.domain.Hashtag;
import com.ssafy.goumunity.domain.chat.service.port.HashtagRepository;
import java.time.Instant;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class HashtagRepositoryImpl implements HashtagRepository {
    private final HashtagJpaRepository hashtagJpaRepository;

    @Override
    public SliceResponse<Hashtag> findAllByHashtagName(
            String hashtagName, Long time, Pageable pageable) {
        Slice<HashtagEntity> entity =
                hashtagJpaRepository.findAllByKeyword(hashtagName, Instant.ofEpochSecond(time), pageable);
        return SliceResponse.from(
                entity.getContent().stream().map(HashtagEntity::to).toList(), entity.hasNext());
    }

    @Override
    public Hashtag save(Hashtag hashtag) {
        return hashtagJpaRepository.save(HashtagEntity.from(hashtag)).to();
    }

    @Override
    public boolean existsOneByHashtagName(String hashtagName) {
        return hashtagJpaRepository.existsOneByName(hashtagName);
    }

    @Override
    public boolean existsOneByHashtagId(Long hashtagId) {
        return hashtagJpaRepository.existsById(hashtagId);
    }
}
