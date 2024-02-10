package com.ssafy.goumunity.domain.user.infra;

public interface UserRankingInterface {
    Long getId();

    String getEmail();

    String getNickname();

    String getImgSrc();

    Long getLikedSum();
}
