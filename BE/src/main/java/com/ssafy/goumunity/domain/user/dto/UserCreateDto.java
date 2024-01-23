package com.ssafy.goumunity.domain.user.dto;

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
public class UserCreateDto {

    @Email
    @NotBlank(message = "이메일은 필수값입니다.")
    private String email;

    @NotBlank(message = "비밀번호는 필수값입니다.")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#\\$%])[a-zA-Z\\d!@#\\$%]{8,20}$")
    private String password;

    private Long monthBudget;

    @Min(message = "나이는 1살 이상이여야 합니다.", value = 0)
    @Max(message = "나이는 150살 이하여야 합니다.", value = 150)
    private Integer age;

    @NotNull(message = "유저 카테고리는 필수값입니다.")
    private UserCategory userCategory;

    @Min(message = "성별은 0(남자) 또는 1(여자)로 입력해주세요.", value = 0)
    @Max(message = "성별은 0(남자) 또는 1(여자)로 입력해주세요.", value = 1)
    @NotNull(message = "성별은 필수값입니다.")
    private Integer gender;

    private String nickname;

    @NotNull(message = "지역 id는 필수값입니다.")
    private Integer regionId;
}
