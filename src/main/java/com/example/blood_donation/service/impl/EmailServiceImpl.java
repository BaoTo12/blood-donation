package com.example.blood_donation.service.impl;

import com.example.blood_donation.exception.AppException;
import com.example.blood_donation.exception.ErrorCode;
import com.example.blood_donation.service.EmailService;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import com.sendgrid.helpers.mail.objects.Personalization;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;

@Service
public class EmailServiceImpl implements EmailService {
    private final SendGrid sendGrid;
    private final String fromEmail;
    private final String fromName;


    public EmailServiceImpl(@Value("${sendgrid.from-email}") String fromEmail,
                            @Value("${sendgrid.from-name}") String fromName,
                            SendGrid sendGrid) {
        this.sendGrid = sendGrid;
        this.fromEmail = fromEmail;
        this.fromName = fromName;
    }

    public void sendSimpleEmail(String toEmail, String subject, String content) {
        try {
            // Create the 'from' email object - this represents the sender
            Email from = new Email(fromEmail, fromName);

            // Create the 'to' email object - this represents the recipient
            Email to = new Email(toEmail);

            // Create the content object - this holds the actual message
            Content emailContent = new Content("text/plain", content);

            // Assemble everything into a Mail object
            Mail mail = new Mail(from, subject, to, emailContent);

            // Create the request and send it
            Request request = new Request();
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());

            // Execute the request
            Response response = sendGrid.api(request);

            // Log the result for debugging
            System.out.println("Email sent. Status: " + response.getStatusCode());

        } catch (IOException ex) {
            // Handle the exception appropriately for your application
            throw new AppException(ErrorCode.FAILED_TO_SEND_EMAIL, "Failed to send email: " + ex.getMessage());
        }
    }

    public void sendHtmlEmail(String toEmail, String subject, String htmlContent) {
        try {
            Email from = new Email(fromEmail, fromName);
            Email to = new Email(toEmail);

            // Notice we're now using "text/html" instead of "text/plain"
            Content content = new Content("text/html", htmlContent);

            Mail mail = new Mail(from, subject, to, content);

            // You can also add a plain text version as a fallback
            Content plainTextContent = new Content("text/plain",
                    "Please view this email in an HTML-capable email client.");
            mail.addContent(plainTextContent);

            Request request = new Request();
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());

            Response response = sendGrid.api(request);
            System.out.println("HTML email sent. Status: " + response.getStatusCode());

        } catch (IOException ex) {
            throw new AppException(ErrorCode.FAILED_TO_SEND_EMAIL, "Failed to send email: " + ex.getMessage());
        }
    }

    public void sendTemplatedEmail(String toEmail, String templateId, Map<String, String> templateData) {
        try {
            Email from = new Email(fromEmail, fromName);
            Email to = new Email(toEmail);

            Mail mail = new Mail();
            mail.setFrom(from);
            mail.setSubject("Your subject here"); // Can be overridden by template

            // Create personalization object - this is where dynamic data goes
            Personalization personalization = new Personalization();
            personalization.addTo(to);

            // Add dynamic template data
            templateData.forEach(personalization::addDynamicTemplateData);

            mail.addPersonalization(personalization);

            // Set the template ID - this tells SendGrid which template to use
            mail.setTemplateId(templateId);

            Request request = new Request();
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());

            Response response = sendGrid.api(request);
            System.out.println("Templated email sent. Status: " + response.getStatusCode());

        } catch (IOException ex) {
            throw new AppException(ErrorCode.FAILED_TO_SEND_EMAIL, "Failed to send email: " + ex.getMessage());
        }
    }
}

