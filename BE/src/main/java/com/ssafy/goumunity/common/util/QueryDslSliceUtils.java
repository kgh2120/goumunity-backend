package com.ssafy.goumunity.common.util;

import java.util.List;
import org.springframework.data.domain.Pageable;

/** QueryDsl에서 SLice 기능을 이용할 때 필요한 작업들을 모아두는 유틸리티 클래스입니다. 이 클래스를 생성하려하면 예외가 발생합니다. @Author 김규현 */
public class QueryDslSliceUtils {

    private QueryDslSliceUtils() {
        throw new IllegalStateException("do not create this class");
    }

    /**
     * queryDsl에서 반복적으로 등장하는 hasNext에 대한 연산작업을 수행합니다. slice query의 경우 본래 필요한 limit 값보다 1개 더 조회를 해서 다음이
     * 있는지 확인하기 때문에, 더 크다면 (hasNext) 가장 마지막 인덱스의 값을 제거합니다.
     *
     * @param response queryDsl의 조회 결과입니다.
     * @param pageable request에서 입력받은 page, size를 가진 객체입니다.
     * @return hasNext 이 slice문이 다음 조회가 가능한지에 대해서 리턴합니다.
     */
    public static boolean hasNext(List<?> response, Pageable pageable) {
        boolean hasNext = response.size() > pageable.getPageSize();
        if (hasNext) {
            response.remove(pageable.getPageSize());
        }
        return hasNext;
    }
}
