package com.gigasma.hospital.service;

import com.azure.communication.email.EmailClient;
import com.azure.communication.email.EmailClientBuilder;
import com.azure.communication.email.models.EmailMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import lombok.extern.slf4j.Slf4j;
import java.util.Map;

@Slf4j
@Service
public class EmailService {
    private final TemplateEngine templateEngine;
    private final EmailClient emailClient;

    @Value("${azure.communication.email.sender}")
    private String fromEmail;

    public EmailService(TemplateEngine templateEngine,
            @Value("${azure.communication.email.connection-string}") String connectionString) {
        this.templateEngine = templateEngine;
        this.emailClient = new EmailClientBuilder()
                .connectionString(connectionString)
                .buildClient();
    }

    public void sendEmail(String to, String subject, String templateName, Map<String, Object> variables) {
        try {
            Context context = new Context();
            context.setVariables(variables); 

            String htmlContent = templateEngine.process(templateName, context);

            EmailMessage emailMessage = new EmailMessage()
                    .setSenderAddress(fromEmail)
                    .setToRecipients(to)
                    .setSubject(subject)
                    .setBodyHtml(htmlContent);

            emailClient.beginSend(emailMessage);

        } catch (Exception e) {
            throw new RuntimeException("Failed to send email. Please try again later.", e);
        }
    }
}
