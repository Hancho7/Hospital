package com.gigasma.hospital.service;

import com.azure.communication.email.EmailClient;
import com.azure.communication.email.EmailClientBuilder;
import com.azure.communication.email.models.EmailMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class EmailService {
    private final TemplateEngine templateEngine;
    private final EmailClient emailClient;
    
    @Value("${azure.communication.email.sender}")
    private String fromEmail;
    
    @Value("${app.base-url}")
    private String baseUrl;
    
    public EmailService(TemplateEngine templateEngine, 
                        @Value("${azure.communication.email.connection-string}") String connectionString) {
        this.templateEngine = templateEngine;
        this.emailClient = new EmailClientBuilder()
                .connectionString(connectionString)
                .buildClient();
    }

    public void sendVerificationEmail(String to, String name, String token) {
        try {
            Context context = new Context();
            context.setVariable("name", name);
            context.setVariable("verificationLink", baseUrl + "/api/v1/auth/verify?token=" + token);
            
            String htmlContent = templateEngine.process("verification-email", context);
            
            EmailMessage emailMessage = new EmailMessage()
                .setSenderAddress(fromEmail)
                .setToRecipients(to)
                .setSubject("Verify your email")
                .setBodyHtml(htmlContent);
            
            log.info("Attempting to send verification email to: {}", to);
            log.info("Sending email", emailClient.beginSend(emailMessage));
            log.info("Successfully sent verification email to: {}", to);
            
        } catch (Exception e) {
            log.error("Failed to send verification email to: {}. Error: {}", to, e.getMessage(), e);
            throw new RuntimeException("Failed to send verification email. Please try again later.", e);
        }
    }
}
