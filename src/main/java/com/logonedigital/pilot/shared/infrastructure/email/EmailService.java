package com.logonedigital.pilot.shared.infrastructure.email;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Map;

@Service
public class EmailService {
    private static final String DEFAULT_SENDER_EMAIL = "tpkdmta@gmail.com";
    private static final String MULTIPART_MODE = String.valueOf(MimeMessageHelper.MULTIPART_MODE_MIXED);
    private static final String ENCODING = StandardCharsets.UTF_8.name();

    private final JavaMailSender mailSender;
    private final SpringTemplateEngine templateEngine;

    public EmailService(JavaMailSender mailSender, SpringTemplateEngine templateEngine) {
        this.mailSender = mailSender;
        this.templateEngine = templateEngine;
    }

    @Async
    public void sendValidationEmail(
            String to, String username, EmailTemplateName emailTemplate,
            String confirmationUrl, String activationCode, String subject
    ) throws MessagingException {
        String templateName = resolveTemplateName(emailTemplate, "confirm-email");

        Map<String, Object> properties = Map.of(
                "username", username,
                "confirmationUrl", confirmationUrl,
                "activationCode", activationCode
        );

        MimeMessage message = createMimeMessage(to, subject, templateName, properties, null);
        mailSender.send(message);
    }

    private MimeMessage createMimeMessage(
            String to, String subject, String templateName,
            Map<String, Object> properties, String attachmentPath
    ) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, Boolean.parseBoolean(MULTIPART_MODE), ENCODING);

        helper.setFrom(DEFAULT_SENDER_EMAIL);
        helper.setTo(to);
        helper.setSubject(subject);

        if (templateName != null) {
            Context context = new Context();
            context.setVariables(properties);
            String template = templateEngine.process(templateName, context);
            helper.setText(template, true);
        } else {
            helper.setText((String) properties.get("message"), false);
        }

        if (attachmentPath != null) {
            FileSystemResource file = new FileSystemResource(new File(attachmentPath));
            helper.addAttachment(new File(attachmentPath).getName(), file);
        }

        return message;
    }

    private String resolveTemplateName(EmailTemplateName emailTemplate, String defaultTemplate) {
        return emailTemplate == null ? defaultTemplate : emailTemplate.getName();
    }
}