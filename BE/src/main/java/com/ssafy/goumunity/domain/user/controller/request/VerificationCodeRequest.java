package com.ssafy.goumunity.domain.user.controller.request;

import com.ssafy.goumunity.common.constraint.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class VerificationCodeRequest {

    @NotBlank(message = "인증코드는 필수값입니다.")
    private String code;

    @Email
    @NotBlank(message = "이메일은 필수값입니다.")
    private String email;
}
