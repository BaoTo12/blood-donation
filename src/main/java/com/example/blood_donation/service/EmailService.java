package com.example.blood_donation.service;

import java.util.Map;

public interface EmailService {
    public void sendSimpleEmail(String toEmail, String subject, String content);

    public void sendHtmlEmail(String toEmail, String subject, String htmlContent);

    public void sendTemplatedEmail(String toEmail, String templateId, Map<String, String> templateData);
}
