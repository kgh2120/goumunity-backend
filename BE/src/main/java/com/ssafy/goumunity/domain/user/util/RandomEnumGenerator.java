package com.ssafy.goumunity.domain.user.util;

import com.ssafy.goumunity.domain.user.domain.FirstNickname;
import com.ssafy.goumunity.domain.user.domain.LastNickname;
import com.ssafy.goumunity.domain.user.domain.MiddleNickname;
import java.util.Random;

public class RandomEnumGenerator<T extends Enum<T>> {

    public static final RandomEnumGenerator<FirstNickname> FIRST_NICKNAME_RANDOM_ENUM_GENERATOR =
            new RandomEnumGenerator<>(FirstNickname.class);
    public static final RandomEnumGenerator<MiddleNickname> MIDDLE_NICKNAME_RANDOM_ENUM_GENERATOR =
            new RandomEnumGenerator<>(MiddleNickname.class);
    public static final RandomEnumGenerator<LastNickname> LAST_NICKNAME_RANDOM_ENUM_GENERATOR =
            new RandomEnumGenerator<>(LastNickname.class);
    private static final Random RANDOM_NUM = new Random();
    private final T[] values;

    public RandomEnumGenerator(Class<T> e) {
        values = e.getEnumConstants();
    }

    public T randomEnum() {
        return values[RANDOM_NUM.nextInt(values.length)];
    }
}
