package com.ssafy.goumunity.common.constraint;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class ConstraintsValidatorsTest {

    @Test
    void 이메일_검증_테스트_성공() throws Exception {
        // given
        String email = "ssafy@google.com";
        // when
        boolean sut = ConstraintsValidators.validateEmailPattern(email);
        // then
        assertThat(sut).isTrue();
    }

    @Test
    void 이메일_검증_테스트_실패_빈_값인_경우() throws Exception {
        // given
        String email = "";
        // when
        boolean sut = ConstraintsValidators.validateEmailPattern(email);
        // then
        assertThat(sut).isFalse();
    }

    @Test
    void 이메일_검증_테스트_실패_널인_경우() throws Exception {
        // given
        String email = null;
        // when
        boolean sut = ConstraintsValidators.validateEmailPattern(email);
        // then
        assertThat(sut).isFalse();
    }

    @Test
    void 이메일_검증_테스트_실패_골뱅이가_없는_경우() throws Exception {
        // given
        String email = "ssafy";
        // when
        boolean sut = ConstraintsValidators.validateEmailPattern(email);
        // then
        assertThat(sut).isFalse();
    }

    @Test
    void 이메일_검증_테스트_실패_최상위_도메인이_없는_경우() throws Exception {
        // given
        String email = "ssafy@naver";
        // when
        boolean sut = ConstraintsValidators.validateEmailPattern(email);
        // then
        assertThat(sut).isFalse();
    }

    @Test
    void 이메일_검증_테스트_실패_한글이_들어간_경우() throws Exception {
        // given
        String email = "ssafy싸핗@naver";
        // when
        boolean sut = ConstraintsValidators.validateEmailPattern(email);
        // then
        assertThat(sut).isFalse();
    }
}
