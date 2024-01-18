package com.ssafy.goumunity.user.service;

import com.ssafy.goumunity.common.exception.CustomErrorCode;
import com.ssafy.goumunity.common.exception.CustomException;
import com.ssafy.goumunity.user.dto.VerificationCodeDto;
import com.ssafy.goumunity.user.service.port.MailSender;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VertificationServiceImpl implements VertificationService {

    private final MailSender mailSender;
    private final UserService userService;
    private final RedisTemplate<String, String> redisTemplate;

    @Override
    public void send(String email) {
        try {
            // 이메일 중복 검사
            userService.isExistEmail(email);

            String title = "거뮤니티 이메일 인증 번호";
            String authCode = createCode();

            // redis -> (code, email) 저장
            ValueOperations<String, String> vop = redisTemplate.opsForValue();
            vop.set(authCode, email, 5, TimeUnit.MINUTES);

            mailSender.send(email, title, authCode);
        } catch (NoSuchAlgorithmException e) {
            throw new CustomException(CustomErrorCode.EMAIL_NOT_FOUND);
        }
    }

    @Override
    public boolean verificate(VerificationCodeDto verificationCodeDto) {
        ValueOperations<String, String> vop = redisTemplate.opsForValue();
        String value = vop.getAndDelete(verificationCodeDto.getCode());
        return verificationCodeDto.getEmail().equals(value);
    }

    private String createCode() throws NoSuchAlgorithmException {
        int lenth = 6;

        Random random = SecureRandom.getInstanceStrong();
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < lenth; i++) {
            builder.append(random.nextInt(10));
        }
        return builder.toString();
    }
}
