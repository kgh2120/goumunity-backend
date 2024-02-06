package com.ssafy.goumunity.domain.chat.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;

import com.ssafy.goumunity.common.util.SliceResponse;
import com.ssafy.goumunity.domain.chat.domain.Hashtag;
import com.ssafy.goumunity.domain.chat.service.port.HashtagRepository;
import java.util.List;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@ExtendWith(MockitoExtension.class)
class HashtagServiceImplTest {

    @Mock HashtagRepository hashtagRepository;
    @InjectMocks HashtagServiceImpl hashtagService;

    @Test
    void 해시태그_검색_테스트() throws Exception {
        // given
        given(hashtagRepository.findAllByHashtagName(any(), any(), any()))
                .willReturn(
                        SliceResponse.from(
                                List.of(
                                        Hashtag.builder().id(1L).name("tag1").build(),
                                        Hashtag.builder().id(2L).name("tag2").build(),
                                        Hashtag.builder().id(3L).name("tag3").build()),
                                false));
        String hashtagName = "hi";
        Pageable pageable = PageRequest.of(0, 12);
        Long time = 1L;
        // when
        SliceResponse<Hashtag> sut = hashtagService.findAllByHashtagName(hashtagName, pageable, time);
        // then
        SoftAssertions softAssertions = new SoftAssertions();
        softAssertions.assertThat(sut.getHasNext()).isFalse();
        softAssertions.assertThat(sut.getContents().size()).isSameAs(3);
        for (int i = 1; i <= 3; i++) {
            Hashtag tag = sut.getContents().get(i - 1);
            softAssertions.assertThat(tag.getId()).isSameAs(Long.parseLong(i + ""));
            softAssertions.assertThat(tag.getName()).isEqualTo("tag" + i);
        }
        softAssertions.assertAll();
    }
}
