package com.ssafy.goumunity.domain.user.controller.response;

import lombok.*;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Getter
public class NicknameValidationResponse {

    private boolean isExist;
    private String msg;

    public NicknameValidationResponse(boolean isExist) {
        this.isExist = isExist;
        if (isExist) {
            this.msg = "중복된 닉네임입니다.";
        } else {
            this.msg = "존재하지 않는 닉네임입니다.";
        }
    }
}
