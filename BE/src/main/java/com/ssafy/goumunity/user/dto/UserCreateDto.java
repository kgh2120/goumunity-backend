package com.ssafy.goumunity.user.dto;

import com.ssafy.goumunity.user.domain.UserCategory;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
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

    @Size(min = 1, max = 150, message = "나이는 1이상 150이하여야 합니다.")
    private Integer age;

    @NotBlank(message = "유저 카테고리는 필수값입니다.")
    private UserCategory userCategory;

    @Size(min = 0, max = 1, message = "성별은 0 또는 1 이여야합니다.")
    @NotBlank(message = "성별은 필수값입니다.")
    private Integer gender;

    private String nickname;

    @NotBlank(message = "지역 id는 필수값입니다.")
    private Integer regionId;
}
