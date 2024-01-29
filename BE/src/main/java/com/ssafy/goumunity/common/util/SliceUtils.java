package com.ssafy.goumunity.common.util;

import java.util.List;
import org.springframework.data.domain.Pageable;

public class SliceUtils {

    public static boolean hasNext(List<?> response, Pageable pageable) {
        boolean hasNext = false;
        if (response.size() > pageable.getPageSize()) {
            response.remove(pageable.getPageSize());
            hasNext = true;
        }
        return hasNext;
    }
}
