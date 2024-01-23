package com.ssafy.goumunity.domain.user.service;

import com.ssafy.goumunity.domain.user.dto.VerificationCodeDto;

public interface VerificationService {
    void send(String email);

    boolean verificate(VerificationCodeDto verificationCodeDto);
}
