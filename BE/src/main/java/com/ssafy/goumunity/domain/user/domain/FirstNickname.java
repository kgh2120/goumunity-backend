package com.ssafy.goumunity.domain.user.domain;

import lombok.Getter;

@Getter
public enum FirstNickname {
    GREEDY("탐욕스러운"),
    EAT12("하루 12끼 먹는"),
    GOOD_HUNTER("사냥을 잘하는"),
    ALWAYS_SLEEP("잠만 자는"),
    BE_HUMAN("사람이 되고 싶은"),
    LOVER("사랑꾼"),
    MZ("MZ한"),
    SUPER_ULTRA("슈퍼울트라"),
    RICH("돈 많은"),
    SELF_RICH("자수성가한"),
    FLOWER("꽃거지"),
    SEOUL_STATION("서울역 1출 대장"),
    EXIT_SSAFY("싸탈하고픈"),
    RICH_OF_EXCITEMENT("흥부자"),
    MILK_COLOR("우유빛깔"),
    HACK_CUTE("핵귀욤"),
    STRONGEST("세계최강"),
    POOR_HEAD("왕초"),
    POOR_CHILD("거린이"),
    QUICK_LOVER("금사빠"),
    ALL_IN("한탕주의자"),
    GAMBLER("도박꾼"),
    WORKER("노동자"),
    SEOUL_SHERIFF("서울보안관"),
    MILITARY_CAPTAIN("군기반장"),
    SPY("스파이"),
    DOUDLE_SPY("이중스파이"),
    PIGEON_LOVER("비둘기러버"),
    COIN_UNDER_VENDING_MACHINE("자판기 동전도둑"),
    EOMBOKDONG("엄복동"),
    TROUBLE_MAKER("트러블메이커"),
    TOO_MUCH_TALKER("투머치토커"),
    CHICKEN_OWNER("치킨집사장"),
    RIDER("폭주족"),
    YOUTUBER("구독자 10명 유튜버"),
    SOUL_MASTER("소울마스터"),
    HO("호동생"),
    ;

    private String kr;

    FirstNickname(String kr) {
        this.kr = kr;
    }
}
