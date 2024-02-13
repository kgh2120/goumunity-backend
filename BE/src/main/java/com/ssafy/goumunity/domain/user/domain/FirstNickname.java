package com.ssafy.goumunity.domain.user.domain;

import lombok.Getter;

@Getter
public enum FirstNickname {
    GREEDY("탐욕스러운"),
    EAT12("하루_12끼_먹는"),
    GOOD_HUNTER("사냥을_잘하는"),
    ALWAYS_SLEEP("잠만_자는"),
    BE_HUMAN("사람이_되고_싶은"),
    LOVER("사랑꾼"),
    MZ("MZ한"),
    SUPER_ULTRA("슈퍼울트라"),
    RICH("돈_많은"),
    SELF_RICH("자수성가한"),
    FLOWER("꽃거지"),
    SEOUL_STATION("서울역_1출_대장"),
    EXIT_SSAFY("싸탈하고픈"),
    RICH_OF_EXCITEMENT("흥부자"),
    MILK_COLOR("우유빛깔"),
    HACK_CUTE("핵귀욤"),
    STRONGEST("세계_최강"),
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
    COIN_UNDER_VENDING_MACHINE("자판기_동전도둑"),
    EOMBOKDONG("엄복동"),
    TROUBLE_MAKER("트러블메이커"),
    TOO_MUCH_TALKER("투머치토커"),
    CHICKEN_OWNER("치킨집사장"),
    RIDER("폭주족"),
    YOUTUBER("구독자_10명_유튜버"),
    ;

    private String kr;

    FirstNickname(String kr) {
        this.kr = kr;
    }
}
