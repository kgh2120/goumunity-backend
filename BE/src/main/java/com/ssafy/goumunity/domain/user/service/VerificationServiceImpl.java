package com.ssafy.goumunity.domain.user.service;

import com.ssafy.goumunity.domain.user.controller.request.VerificationCodeRequest;
import com.ssafy.goumunity.domain.user.exception.UserErrorCode;
import com.ssafy.goumunity.domain.user.exception.UserException;
import com.ssafy.goumunity.domain.user.service.port.MailSender;
import com.ssafy.goumunity.domain.user.service.port.UserRepository;
import com.ssafy.goumunity.domain.user.util.RandomCodeGenerator;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class VerificationServiceImpl implements VerificationService {

    private final MailSender mailSender;
    private final RedisTemplate<String, String> redisTemplate;
    private final UserRepository userRepository;

    private final int VERIFICATION_CODE_MAX_LENGTH = 6;

    @Override
    public void send(String email) {
        // 이메일 중복 검사
        if (userRepository.existsByEmail(email)) {
            throw new UserException(UserErrorCode.EXIST_EMAIL);
        }

        String title = "거뮤니티 이메일 인증 번호";
        String authCode = RandomCodeGenerator.randomCode(VERIFICATION_CODE_MAX_LENGTH);

        // redis -> (code, email) 저장
        ValueOperations<String, String> vop = redisTemplate.opsForValue();
        vop.set(authCode, email, 5, TimeUnit.MINUTES);

        mailSender.send(email, title, authCode);
    }

    @Override
    public boolean verificate(VerificationCodeRequest verificationCodeRequest) {
        ValueOperations<String, String> vop = redisTemplate.opsForValue();
        String value = vop.getAndDelete(verificationCodeRequest.getCode());
        return verificationCodeRequest.getEmail().equals(value);
    }
}
