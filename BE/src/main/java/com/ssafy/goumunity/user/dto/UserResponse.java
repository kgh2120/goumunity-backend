package com.ssafy.goumunity.user.dto;

import com.ssafy.goumunity.user.domain.User;
import com.ssafy.goumunity.user.domain.UserCategory;
import com.ssafy.goumunity.user.domain.UserStatus;
import java.time.Instant;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserResponse {
    private String email;
    private String password;
    private Long monthBudget;
    private Integer age;
    private UserCategory userCategory;
    private Integer gender;
    private String nickname;
    private String imgSrc;
    private Instant registerDate;
    private UserStatus userStatus;
    private Instant lastPasswordModifiedDate;
    private Integer regionId;

    public static UserResponse from(User user) {
        return UserResponse.builder()
                .email(user.getEmail())
                .password(user.getPassword())
                .monthBudget(user.getMonthBudget())
                .age(user.getAge())
                .userCategory(user.getUserCategory())
                .gender(user.getGender())
                .nickname(user.getNickname())
                .imgSrc(user.getImgSrc())
                .registerDate(user.getRegisterDate())
                .userStatus(UserStatus.ACTIVE)
                .lastPasswordModifiedDate(user.getLastPasswordModifiedDate())
                .regionId(user.getRegionId())
                .build();
    }
}
