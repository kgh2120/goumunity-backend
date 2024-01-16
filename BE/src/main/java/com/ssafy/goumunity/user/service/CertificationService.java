package com.ssafy.goumunity.user.service;

import com.ssafy.goumunity.common.exception.UserErrorCode;
import com.ssafy.goumunity.common.exception.UserException;
import com.ssafy.goumunity.user.service.port.MailSender;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class CertificationService {

    private final MailSender mailSender;
    private final UserService userService;

    public void send(String email){
        try {
            // 이메일 중복 검사
            userService.isExistEmail(email);

            String title = "거뮤니티 이메일 인증 번호";
            String authCode = createCode();

            mailSender.send(email, title, authCode);
        } catch(NoSuchAlgorithmException e){
            throw new UserException(UserErrorCode.EMAIL_NOT_FOUND);
        }
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
