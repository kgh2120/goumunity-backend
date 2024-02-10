package com.ssafy.goumunity.domain.user.controller.response;

import com.ssafy.goumunity.domain.user.infra.UserRankingInterface;
import lombok.*;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Getter
public class UserRankingResponse {
    private Long id;
    private String email;
    private String nickname;
    private String imgSrc;
    private Long likedSum;

    public static UserRankingResponse from(UserRankingInterface user) {
        return UserRankingResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .nickname(user.getNickname())
                .imgSrc(user.getImgSrc())
                .likedSum(user.getLikedSum())
                .build();
    }
}
