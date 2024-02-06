package com.ssafy.goumunity.common.util;

import java.util.Random;

public class RandomEnumGenerator<T extends Enum<T>> {
    private static final Random RANDOM_NUM = new Random();
    private final T[] values;

    public RandomEnumGenerator(Class<T> e) {
        values = e.getEnumConstants();
    }

    public T randomEnum() {
        return values[RANDOM_NUM.nextInt(values.length)];
    }
}
