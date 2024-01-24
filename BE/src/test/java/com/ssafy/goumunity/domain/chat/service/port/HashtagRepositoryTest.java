package com.ssafy.goumunity.domain.chat.service.port;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import com.ssafy.goumunity.common.util.SliceResponse;
import com.ssafy.goumunity.domain.chat.domain.Hashtag;
import com.ssafy.goumunity.domain.chat.infra.HashtagEntity;
import com.ssafy.goumunity.domain.chat.infra.HashtagJpaRepository;
import com.ssafy.goumunity.domain.chat.infra.HashtagRepositoryImpl;
import java.time.Instant;
import java.util.List;
import javax.swing.*;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.SliceImpl;

@ExtendWith(MockitoExtension.class)
class HashtagRepositoryTest {

    @Mock HashtagJpaRepository hashtagJpaRepository;

    @InjectMocks HashtagRepositoryImpl hashtagRepository;

    @Test
    void 해시태그_검색_테스트() throws Exception {
        // given
        given(hashtagJpaRepository.findAllByKeyword(any(), any(), any()))
                .willReturn(
                        new SliceImpl<>(
                                List.of(
                                        HashtagEntity.builder().id(1L).name("흠..").build(),
                                        HashtagEntity.builder().id(2L).name("흠..").build()),
                                PageRequest.of(0, 12),
                                false));
        // when
        SliceResponse<Hashtag> sut =
                hashtagRepository.findAllByHashtagName("해시태그", 0L, PageRequest.of(0, 12));
        // then
        SoftAssertions softAssertions = new SoftAssertions();
        softAssertions.assertThat(sut.getHasNext()).isFalse();
        softAssertions.assertThat(sut.getContents().size()).isSameAs(2);
        for (int i = 1; i <= 2; i++) {
            Hashtag tag = sut.getContents().get(i - 1);
            softAssertions.assertThat(tag.getId()).isSameAs(Long.parseLong(i + ""));
            softAssertions.assertThat(tag.getName()).isEqualTo("흠..");
        }
        softAssertions.assertAll();
    }

    @Test
    void 해시태그_생성_테스트() throws Exception {
        // given
        Hashtag hashtag = Hashtag.builder().name("hashtag").createdAt(Instant.now()).build();

        HashtagEntity from = HashtagEntity.from(hashtag);
        given(hashtagJpaRepository.save(any()))
                .willReturn(
                        HashtagEntity.builder()
                                .id(1L)
                                .name(from.getName())
                                .createdAt(from.getCreatedAt())
                                .updatedAt(from.getUpdatedAt())
                                .build());

        // when
        Hashtag sut = hashtagRepository.save(hashtag);
        // then
        SoftAssertions sa = new SoftAssertions();

        sa.assertThat(sut.getId()).isSameAs(1L);
        sa.assertThat(sut.getName()).isEqualTo(hashtag.getName());
        sa.assertThat(sut.getCreatedAt()).isEqualTo(hashtag.getCreatedAt());
        sa.assertThat(sut.getUpdatedAt()).isNull();

        sa.assertAll();
    }
}
