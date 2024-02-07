package com.ssafy.goumunity.domain.user.domain;

import lombok.Getter;

@Getter
public enum MiddleNickname {
    MA("마"),
    WANG("왕"),
    YEO("여"),
    KWAK("곽"),
    HWANG("황"),
    PI("피"),
    UM("엄"),
    SEONG("성"),
    HONG("홍"),
    TAE("태"),
    YONG("용"),
    SEO("서"),
    GWAN("관"),
    SAM("삼"),
    BYUN("변"),
    TAK("탁"),
    CHEON("천"),
    NU("누"),
    JANG("장"),
    ;

    private String kr;

    MiddleNickname(String kr) {
        this.kr = kr;
    }
}
