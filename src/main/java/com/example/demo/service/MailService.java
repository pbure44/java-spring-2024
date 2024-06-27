package com.example.demo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MailService {

    private final MailSender mailSender;

    @Value("${mail.sender.username}")
    private String mailSenderUsername;

    public void sendMail(String to, String subject, String content) {
        SimpleMailMessage message = new SimpleMailMessage();

        message.setSubject(subject);
        message.setText(content);

        message.setFrom(mailSenderUsername);
        message.setTo(to);
        mailSender.send(message);
    }
}
