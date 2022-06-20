package com.car.dealership.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class EmailServiceImpl implements EmailService {

    private final String SUBJECT = "Car Dealership Confirm Registration";
    private final String HOSTNAME = "localhost";

    private final JavaMailSender emailSender;

    @Autowired
    public EmailServiceImpl(JavaMailSender emailSender) {
        this.emailSender = emailSender;
    }


    @Override
    public void sendRegistrationMessage(String to, String id, String username) {
        new Thread(() -> {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(to);
            message.setSubject(SUBJECT);
            String MESSAGE_TEXT = "Здравейте, " + username + "!\n" + "Моля потвърдете вашата регистрация, като цъкнете линка отдолу: \n" +
                    "https://www." + HOSTNAME + "/confirmEmail/" + id + "\n" + "Поздрави :)\n\n";
            message.setText(MESSAGE_TEXT);
            emailSender.send(message);
        }).start();
    }
}