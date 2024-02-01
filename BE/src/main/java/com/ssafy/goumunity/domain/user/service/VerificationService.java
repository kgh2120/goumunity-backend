package com.ssafy.goumunity.domain.user.service;

import com.ssafy.goumunity.domain.user.controller.request.VerificationCodeRequest;

public interface VerificationService {
    void send(String email);

    boolean verificate(VerificationCodeRequest verificationCodeRequest);
}
