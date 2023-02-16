package co.ke.emtechhouse.hrm_payroll_system.AuthenticationModule.Models.MailService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service @Slf4j
public class MailService {
    @Value("${company_logo_path}")
    private String company_logo_path;
    @Value("${image_banner}")
    private String banner_path;
    @Value("${from_mail}")
    private String from_mail;

    @Value("${emailSalutation}")
    private String emailSalutation;
    @Value("${emailMessage}")
    private String emailMessage;
    @Value("${emailRemarks}")
    private String emailRemarks;
    @Value("${emailRegards}")
    private String emailRegards;
    @Value("${emailOrganizationName}")
    private String emailOrganizationName;
    @Value("${emailOrganizationPhone}")
    private String emailOrganizationPhone;
    @Value("${emailOrganizationMail}")
    private String emailOrganizationMail;
    @Value("${emailOrganizationAddress}")
    private String emailOrganizationAddress;
    @Value("${emailOrganizationLocation}")
    private String emailOrganizationLocation;
    @Value("${emailOrganizationWebsite}")
    private String emailOrganizationWebsite;

    @Autowired

    JavaMailSender javaMailSender;

    @Value("${organisation.email}")
    private  String fromEmail;

    public void sendEmail(String to, String message, String subject) {
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

