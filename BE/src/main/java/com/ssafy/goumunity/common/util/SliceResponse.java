package com.ssafy.goumunity.common.util;

import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SliceResponse<T> {

    private List<T> contents;
    private Boolean hasNext;

    public static <T> SliceResponse<T> from(List<T> contents, boolean hasNext) {
        return new SliceResponse<T>(contents, hasNext);
    }
}
