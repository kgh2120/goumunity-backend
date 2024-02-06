package com.ssafy.goumunity.domain.user.controller.request;

import com.ssafy.goumunity.common.constraint.Password;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@Builder
public class PasswordModifyRequest {

    @NotBlank(message = "비밀번호는 필수값입니다.")
    @Password
    String password;
}
