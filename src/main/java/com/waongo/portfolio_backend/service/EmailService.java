package com.waongo.portfolio_backend.service;

import com.waongo.portfolio_backend.dto.ContactRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;

    public void envoyerEmailContact(ContactRequest request) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo("waongowahlerwendyam@gmail.com");
        message.setSubject("Nouveau message de " + request.getNom());
        message.setText("De : " + request.getEmail() + "\n\n" + request.getMessage());
        message.setFrom("waongowahlerwendyam@gmail.com");

        mailSender.send(message);
    }
}