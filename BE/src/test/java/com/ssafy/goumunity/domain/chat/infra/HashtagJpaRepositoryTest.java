package com.ssafy.goumunity.domain.chat.infra;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.time.Instant;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;

@DataJpaTest
class HashtagJpaRepositoryTest {

    @Autowired HashtagJpaRepository hashtagJpaRepository;

    @Test
    void 해시태그_슬라이스_테스트() throws Exception {
        // given
        // when
        Slice<HashtagEntity> sut =
                hashtagJpaRepository.findAllByKeyword("태그", Instant.now(), PageRequest.of(0, 12));
        // then

        assertThat(sut.getSize()).isSameAs(12);
        assertThat(sut.hasNext()).isFalse();
        assertThat(sut.getContent().size()).isZero();
    }
}
