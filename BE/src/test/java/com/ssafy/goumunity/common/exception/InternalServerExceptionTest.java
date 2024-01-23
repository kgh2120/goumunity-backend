package com.ssafy.goumunity.common.exception;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class InternalServerExceptionTest {

    @Test
    void causeTest() throws Exception {
        // given

        InternalServerException e = new InternalServerException("hi", "hi");

        System.out.println(e.getCause());

        // when

        // then
    }
}
