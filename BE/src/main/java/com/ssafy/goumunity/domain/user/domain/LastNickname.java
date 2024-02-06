package com.ssafy.goumunity.domain.user.domain;

import lombok.Getter;

@Getter
public enum LastNickname {
    CHUNPAL("춘팔"),
    DEOKCHUL("덕출"),
    CHEOLYONG("철용"),
    SHINPYEONG("신평"),
    HAKCHEOL("학철"),
    CHUNDONG("춘동"),
    SEOKDAE("석대"),
    PALGWANG("팔광"),
    GEUNCHEOL("근철"),
    DAEDU("대두"),
    PILJO("필조"),
    GEUGCHANG("극창"),
    ILJE("일제"),
    DEOKBOK("덕복"),
    KWAKUI("곽의"),
    GEUNCHEOK("근척"),
    CHANGGEON("창근"),
    MAGWON("마권"),
    WONCHANG("원창"),
    CHEONGYU("천규"),
    DEUKMAN("득만"),
    MANCHUN("만춘"),
    HYEOKGUNG("혁궁"),
    PALSIK("팔식"),
    PUNGGANG("풍강"),
    CHUCKSAL("척살"),
    GYUEOK("규억"),
    GWANGGU("광구"),
    GWIPIL("귀필"),
    DUCHANG("두창"),
    UKDU("억두"),
    ;

    private String kr;

    LastNickname(String kr) {
        this.kr = kr;
    }
}
