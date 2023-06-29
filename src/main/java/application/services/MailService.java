package application.services;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

import java.util.Properties;

@Service
@RequiredArgsConstructor
public class MailService {

    private final JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
    private final Properties properties = mailSender.getJavaMailProperties();
    private final SimpleMailMessage message = new SimpleMailMessage();
    @Value("${MAIL.USERNAME}")
    private String username;

    @Value("${MAIL.PASSWORD}")
    private String password;

    private void setMessage(String to, String subject, String text) {
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
    }

    private void setMailProperties() {
        mailSender.setHost("smtp.gmail.com");
        mailSender.setPort(587);
        mailSender.setUsername(username);
        mailSender.setPassword(password);
    }

    private void putProperties() {
        properties.put("mail.transport.protocol", "smtp");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.debug", "true");
    }

    public JavaMailSender mail() {
        setMailProperties();
        putProperties();

        return mailSender;
    }

    public void sendMessage(String to, String subject, String text) {
        setMessage(to, subject, text);
        mail().send(message);
    }
}
