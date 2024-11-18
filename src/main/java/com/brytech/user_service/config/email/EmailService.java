package com.brytech.user_service.config.email;

import com.brytech.user_service.exception.EmailSendingException;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.util.Map;

@Service
@Transactional
@RequiredArgsConstructor
public class EmailService {

    private KafkaTemplate<String, String> kafkaTemplate;
    private final JavaMailSender mailSender;
    private final SpringTemplateEngine templateEngine;
    private final Environment environment;

    public void sendMessageUsingThymeleafTemplate(
            String[] to, String subject, Map<String, Object> templateModel, String template
    ) {
        Context thymeleafContext = new Context();

        // Optional: Set locale if needed in the future
        // thymeleafContext.setLocale(locale);

        thymeleafContext.setVariables(templateModel);
        thymeleafContext.setVariable("environment", environment);
        String htmlBody = templateEngine.process(template, thymeleafContext);

        // Send the email payload to Kafka asynchronously
        kafkaTemplate.send("emailTopic", htmlBody);
        
        try {
            sendHtmlMessage(to, subject, htmlBody);
        } catch (MessagingException e) {
            throw new EmailSendingException("Failed to send the email");
        }
    }

    private void sendHtmlMessage(String[] to, String subject, String htmlBody) throws MessagingException {

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(htmlBody, true);
        mailSender.send(message);
    }
}
