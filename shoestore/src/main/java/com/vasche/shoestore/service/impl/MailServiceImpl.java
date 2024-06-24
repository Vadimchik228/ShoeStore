package com.vasche.shoestore.service.impl;

import com.vasche.shoestore.domain.mail.MailType;
import com.vasche.shoestore.domain.user.User;
import com.vasche.shoestore.service.MailService;
import freemarker.template.Configuration;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@Service
@RequiredArgsConstructor
public class MailServiceImpl implements MailService {

    private final Configuration configuration;
    private final JavaMailSender mailSender;

    @Override
    public void sendEmail(User user, MailType type, Properties params) {
        switch (type) {
            case REGISTRATION -> sendRegistrationEmail(user, params);
            case NOTIFICATION -> sendNotificationEmail(user, params);
            default -> {
            }
        }
    }

    @SneakyThrows
    private void sendRegistrationEmail(final User user, final Properties params) {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage,
                false,
                "UTF-8");
        helper.setSubject("Thank you for registration, " + user.getName());
        helper.setTo(user.getUsername());
        String emailContent = getRegistrationEmailContent(user, params);
        helper.setText(emailContent, true);
        mailSender.send(mimeMessage);
    }

    @SneakyThrows
    private void sendNotificationEmail(final User user, final Properties params) {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage,
                false,
                "UTF-8");
        helper.setSubject("Your order has been delivered to the pick-up point");
        helper.setTo(user.getUsername());
        String emailContent = getNotificationEmailContent(user, params);
        helper.setText(emailContent, true);
        mailSender.send(mimeMessage);
    }

    @SneakyThrows
    private String getRegistrationEmailContent(final User user, final Properties properties) {
        StringWriter writer = new StringWriter();
        Map<String, Object> model = new HashMap<>();
        model.put("name", user.getName());
        configuration.getTemplate("register.ftlh")
                .process(model, writer);
        return writer.getBuffer().toString();
    }

    @SneakyThrows
    private String getNotificationEmailContent(final User user, final Properties properties) {
        StringWriter writer = new StringWriter();
        Map<String, Object> model = new HashMap<>();
        model.put("name", user.getName());
        model.put("city", properties.getProperty("city"));
        model.put("address", properties.getProperty("address"));
        model.put("title", properties.getProperty("title"));
//        model.put("quantity", properties.getProperty("quantity"));

        configuration.getTemplate("notifier.ftlh")
                .process(model, writer);
        return writer.getBuffer().toString();
    }
}
