package com.ssafy.goumunity.common.util;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.PageRequest;

class QueryDslSliceUtilsTest {

    @Test
    void hasNext가_True인_경우() throws Exception {
        // given
        PageRequest pageable = PageRequest.of(0, 10);
        List<String> contents = new ArrayList<>();
        for (int i = 0; i < pageable.getPageSize() + 1; i++) {
            contents.add("hi");
        }
        // when
        boolean sut = QueryDslSliceUtils.hasNext(contents, pageable);
        // then
        assertThat(sut).isTrue();
    }

    @Test
    void hasNext가_False인_경우() throws Exception {
        // given
        PageRequest pageable = PageRequest.of(0, 10);
        List<String> contents = new ArrayList<>();
        for (int i = 0; i < pageable.getPageSize(); i++) {
            contents.add("hi");
        }
        // when
        boolean sut = QueryDslSliceUtils.hasNext(contents, pageable);
        // then
        assertThat(sut).isFalse();
    }
}
