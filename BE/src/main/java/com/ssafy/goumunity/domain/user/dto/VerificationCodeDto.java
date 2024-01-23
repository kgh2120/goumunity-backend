package com.ssafy.goumunity.domain.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class VerificationCodeDto {

    @NotBlank(message = "인증코드는 필수값입니다.")
    private String code;

    @Email
    @NotBlank(message = "이메일은 필수값입니다.")
    private String email;
}
