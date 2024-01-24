package com.ssafy.goumunity.domain.chat.infra;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.time.Instant;
import org.assertj.core.api.Assertions;
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

    @Test
    void 해시태그_이름_존재_유무_없다면_FALSE리턴() throws Exception {
        // given
        String name = "hash";
        // when
        boolean sut = hashtagJpaRepository.existsOneByName(name);
        // then
        Assertions.assertThat(sut).isFalse();
    }

    @Test
    void 해시태그_이름_존재_유무_있다면_TRUE_리턴() throws Exception {
        // given
        String name = "hash";
        hashtagJpaRepository.save(HashtagEntity.builder().name(name).build());
        // when
        boolean sut = hashtagJpaRepository.existsOneByName(name);
        // then
        Assertions.assertThat(sut).isTrue();
    }
}
