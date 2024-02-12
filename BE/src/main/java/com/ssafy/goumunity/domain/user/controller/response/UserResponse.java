package com.ssafy.goumunity.domain.user.controller.response;

import com.ssafy.goumunity.domain.user.domain.Gender;
import com.ssafy.goumunity.domain.user.domain.User;
import com.ssafy.goumunity.domain.user.domain.UserCategory;
import com.ssafy.goumunity.domain.user.domain.UserSavingCategory;
import lombok.*;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Getter
public class UserResponse {
    private Long id;
    private String email;
    private Long monthBudget;
    private Integer age;
    private UserCategory userCategory;
    private UserSavingCategory savingCategory;
    private Gender gender;
    private String nickname;
    private String imgSrc;
    private Boolean isAuthenticated;
    private Long regionId;
    private String si;
    private String gungu;

    public static UserResponse from(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .monthBudget(user.getMonthBudget())
                .age(user.getAge())
                .userCategory(user.getUserCategory())
                .savingCategory(user.getSavingCategory())
                .gender(user.getGender())
                .nickname(user.getNickname())
                .imgSrc(user.getImgSrc())
                .isAuthenticated(user.getIsAuthenticated())
                .regionId(user.getRegionId())
                .si(user.getSi())
                .gungu(user.getGungu())
                .build();
    }
}
