package com.example.shop.service;

import java.util.Map;

public interface EmailService {
    void sendEmail(String emailReceiver, String templateName, Map<String, Object> variables);
}
