package com.ssafy.goumunity.domain.user.controller.response;

import com.ssafy.goumunity.domain.user.domain.Gender;
import com.ssafy.goumunity.domain.user.domain.User;
import com.ssafy.goumunity.domain.user.domain.UserCategory;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserResponse {
    private Long id;
    private String email;
    private Long monthBudget;
    private Integer age;
    private UserCategory userCategory;
    private Gender gender;
    private String nickname;
    private String imgSrc;
    private Long regionId;

    public static UserResponse from(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .monthBudget(user.getMonthBudget())
                .age(user.getAge())
                .userCategory(user.getUserCategory())
                .gender(user.getGender())
                .nickname(user.getNickname())
                .imgSrc(user.getImgSrc())
                .regionId(user.getRegionId())
                .build();
    }
}
