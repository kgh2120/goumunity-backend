package com.ssafy.goumunity.user.domain;

import com.ssafy.goumunity.user.dto.UserCreateDto;
import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {

    private Long id;
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
    private Instant createdAt;
    private Instant updatedAt;

    public static User from(UserCreateDto userCreateDto, String imgUrl, String encodedPw) {
        return User.builder()
                .email(userCreateDto.getEmail())
                .password(encodedPw)
                .monthBudget(userCreateDto.getMonthBudget())
                .age(userCreateDto.getAge())
                .userCategory(userCreateDto.getUserCategory())
                .gender(userCreateDto.getGender())
                .nickname(userCreateDto.getNickname())
                .imgSrc(imgUrl)
                .registerDate(Instant.now())
                .userStatus(UserStatus.ACTIVE)
                .lastPasswordModifiedDate(Instant.now())
                .regionId(userCreateDto.getRegionId())
                .build();
    }

    public void modifyPassword(String password){
        this.password = password;
    }
}
