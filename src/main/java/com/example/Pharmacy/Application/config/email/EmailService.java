package com.example.Pharmacy.Application.config.email;

import com.example.Pharmacy.Application.exception.CustomException;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.apache.camel.ProducerTemplate;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.util.Locale;
import java.util.Map;

@Service
@Transactional
@RequiredArgsConstructor
public class EmailService {

    private ProducerTemplate producerTemplate;
    private final JavaMailSender mailSender;
    private final SpringTemplateEngine templateEngine;

    // TODO: Add Logo
//    @Value("classpath:/static/images/logo.png")
//    private Resource resourceFile;

    public void sendMessageUsingThymeleafTemplate(
            String[] to, String subject, Map<String, Object> templateModel, String template, Locale locale
    ) {
        Context thymeleafContext = new Context();
        thymeleafContext.setLocale(locale);
        thymeleafContext.setVariables(templateModel);

        String htmlBody = templateEngine.process(template, thymeleafContext);

        // Send the email payload to Kafka asynchronously
        producerTemplate.sendBody("direct:emailProducer", htmlBody);
        
        try {
            sendHtmlMessage(to, subject, htmlBody);
        } catch (MessagingException e) {
            throw new CustomException("Can't send the email");
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
