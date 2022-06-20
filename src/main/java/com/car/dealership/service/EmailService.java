package com.car.dealership.service;

public interface EmailService {
    void sendRegistrationMessage(String to, String id, String username);
}
