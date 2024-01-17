package com.ssafy.goumunity.user.service;

import com.ssafy.goumunity.user.dto.VerificationCodeDto;

public interface VertificationService {
    void send(String email);
    boolean verificate(VerificationCodeDto verificationCodeDto);
}
