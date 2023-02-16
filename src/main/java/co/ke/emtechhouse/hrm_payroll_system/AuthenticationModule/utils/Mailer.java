package co.ke.emtechhouse.hrm_payroll_system.AuthenticationModule.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@Slf4j
public class Mailer {
    @Autowired
    JavaMailSender javaMailSender;

    @Value("${organisation.email}")
    private String fromEmail;
    public void sendEmail(String to, String message, String subject){
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom(fromEmail);
        mailMessage.setTo(to);
        mailMessage.setSubject(subject);
        mailMessage.setSentDate(new Date());
        mailMessage.setText(message);
        javaMailSender.send(mailMessage);
        log.info("Email Sent successfully.");
    }
}
