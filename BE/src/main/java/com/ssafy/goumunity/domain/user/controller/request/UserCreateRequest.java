package com.ssafy.goumunity.domain.user.controller.request;

import com.ssafy.goumunity.common.constraint.Email;
import com.ssafy.goumunity.common.constraint.Password;
import com.ssafy.goumunity.domain.user.domain.Gender;
import com.ssafy.goumunity.domain.user.domain.UserCategory;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class UserCreateRequest {

    @Email private String email;

    @Password private String password;

    private Long monthBudget;

    @Min(message = "나이는 1살 이상이여야 합니다.", value = 0)
    @Max(message = "나이는 150살 이하여야 합니다.", value = 150)
    private Integer age;

    @NotNull(message = "유저 카테고리는 필수값입니다.")
    private UserCategory userCategory;

    @NotNull(message = "MALE 또는 FEMALE 값이 필요합니다.")
    private Gender gender;

    private String nickname;

    @NotNull(message = "지역 id는 필수값입니다.")
    private Long regionId;
}
