package com.ssafy.goumunity.user.infrastructure;

import com.ssafy.goumunity.user.service.port.MailSender;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class MailSenderImpl implements MailSender {

    private final JavaMailSender javaMailSender;

    @Override
    public void send(String email, String title, String content) {
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(email);
        msg.setSubject(title);
        msg.setText(content);
        javaMailSender.send(msg);
    }
}
