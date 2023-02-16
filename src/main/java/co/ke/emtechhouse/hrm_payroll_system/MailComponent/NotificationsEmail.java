package co.ke.emtechhouse.hrm_payroll_system.MailComponent;

import co.ke.emtechhouse.hrm_payroll_system.EmployeeComponent.EmployeeRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;

@Component
public class NotificationsEmail {
    private final JavaMailSender mailSender;
    private final EmployeeRepository employeeRepository;
    @Value("${reports_absolute_path}")
    private String files_path;
    @Value("${company_logo_path}")
    private String company_logo_path;
    @Value("${image_banner}")
    private String banner_path;
    @Value("${from_mail}")
    private String from_mail;
    @Value("${organizationMail}")
    private String organizationMail;
    public NotificationsEmail(JavaMailSender mailSender, EmployeeRepository employeeRepository) {
        this.mailSender = mailSender;
        this.employeeRepository = employeeRepository;
    }
    public void sendSalaryReportToManagers(
            String emailTo,
            String emailSubject,
            String emailDesignationName,
            String emailMessage
    ) throws MessagingException {
        String emailSender = "${organizationMail}";
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
        helper.setFrom(emailSender);
        helper.setTo(emailTo);
        helper.setSubject(emailSubject);
        helper.setText(
                "<!DOCTYPE html>\n" +
                        "<html lang=\"en\" xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:o=\"urn:schemas-microsoft-com:office:office\">\n" +
                        "<head>\n" +
                        "  <meta charset=\"utf-8\">\n" +
                        "  <meta name=\"viewport\" content=\"width=device-width,initial-scale=1\">\n" +
                        "  <meta name=\"x-apple-disable-message-reformatting\">\n" +
                        "  <title></title>\n" +
                        "  <!--[if mso]>\n" +
                        "  <style>\n" +
                        "    table {border-collapse:collapse;border-spacing:0;border:none;margin:0;}\n" +
                        "    div, td {padding:0;}\n" +
                        "    div {margin:0 !important;}\n" +
                        "  </style>\n" +
                        "  <noscript>\n" +
                        "    <xml>\n" +
                        "      <o:OfficeDocumentSettings>\n" +
                        "        <o:PixelsPerInch>96</o:PixelsPerInch>\n" +
                        "      </o:OfficeDocumentSettings>\n" +
                        "    </xml>\n" +
                        "  </noscript>\n" +
                        "  <![endif]-->\n" +
                        "  <style>\n" +
                        "    table, td, div, h1, p {\n" +
                        "      font-family: Arial, sans-serif;\\n\"\n" +
                        "    }\n" +
                        "    @media screen and (max-width: 530px) {\n" +
                        "      .unsub {\n" +
                        "        display: block;\n" +
                        "        padding: 8px;\n" +
                        "        margin-top: 14px;\n" +
                        "        border-radius: 6px;\n" +
                        "        background-color: #555555;\n" +
                        "        text-decoration: none !important;\n" +
                        "        font-weight: bold;\n" +
                        "      }\n" +
                        "      .col-lge {\n" +
                        "        max-width: 100% !important;\n" +
                        "      }\n" +
                        "    }\n" +
                        "    @media screen and (min-width: 531px) {\n" +
                        "      .col-sml {\n" +
                        "        max-width: 27% !important;\n" +
                        "      }\n" +
                        "      .col-lge {\n" +
                        "        max-width: 73% !important;\n" +
                        "      }\n" +
                        "    }\n" +
                        "  </style>\n" +
                        "</head>\n" +
                        "<body style=\"margin:0;padding:0;word-spacing:normal;background-color: #566fff;\">\n" +
//                            "  <div role=\"article\" aria-roledescription=\"email\" lang=\"en\" style=\"text-size-adjust:100%;-webkit-text-size-adjust:100%;-ms-text-size-adjust:100%;background-color:#939297;\">\n" +
                        "  <div role=\"article\" aria-roledescription=\"email\" lang=\"en\" style=\"text-size-adjust:100%;-webkit-text-size-adjust:100%;-ms-text-size-adjust:100%;background-color:#566fff;\">\n" +
                        "    <table role=\"presentation\" style=\"width:100%; padding-top: 10px; padding-bottom: 10px; border:none;border-spacing:0;\">\n" +
                        "      <tr>\n" +
                        "        <td align=\"center\" style=\"padding:0;\">\n" +
                        "          <!--[if mso]>\n" +
                        "          <table role=\"presentation\" align=\"center\" style=\"width:600px; margin-top: 10px; margin-bottom: 10px;\">\n" +
                        "          <tr>\n" +
                        "          <td>\n" +
                        "          <![endif]-->\n" +
                        "          <table role=\"presentation\" style=\"width:94%;max-width:600px;border:none;border-spacing:0;text-align:left;font-family:Arial,sans-serif;font-size:16px;line-height:22px;color:#363636;\">\n" +
                        "              <td style=\"padding:5px;text-align:center;font-size:12px;background-color:#ffffff;\">\n" +
                        "                <a href=\"http://www.example.com/\" style=\"text-decoration:none;\"><img src='cid:companyLogo' alt=\"Logo\" style=\"width:20%; text-align:center; margin:auto; height:auto;border:none;text-decoration:none;color:#ffffff;\"></a>\n" +
                        "                <hr>\n" +
                        "              </td>\n" +
                        "            <tr>\n" +
                        "              <td style=\"padding:30px;background-color:#ffffff;\">\n" +
                        "                 <h1 style=\"margin-top:0;margin-bottom:16px;font-size:26px;line-height:32px;font-weight:bold;letter-spacing:-0.02em;\">E&M Tech Payslip Notification!</h1>\n" +
                        "                <p style=\"margin:0;\">\n" +
                        "                                        Hello "  + emailDesignationName + "\n" +
                        "                    </p>\n" +
                        "                   <p style=\"margin:0;\">\n" +
                        "                                        Kind regards\n" +
                        "                    </p>\n" +
                        "                   <p style=\"margin:0;\">\n" +
                        "                                        Human Resource Manager\n" +
                        "                    </p>\n" +
                        "                   <p style=\"margin:0;\">\n" +
                        "                                        E&M Tech technologies\n" +
                        "                    </p>\n" +
                        "                   <p style=\"margin:0;\">\n" +
                        "                                        phone: +254 722 582 328\n" +
                        "                    </p>\n" +
                        "                   <p style=\"margin:0;\">\n" +
                        "                                        P.O BOX 11001-00100, Nairobi\n" +
                        "                    </p>\n" +

                        "                   <p style=\"margin:0;\">\n" +
                        "                                        Kind regards\n" +
                        "                    </p>\n" +
                        "                   <p style=\"margin:0;\">\n" +
                        "                                        Human Resource Manager\n" +
                        "                    </p>\n" +
                        "                   <p style=\"margin:0;\">\n" +
                        "                                        E&M Tech technologies\n" +
                        "                    </p>\n" +
                        "                   <p style=\"margin:0;\">\n" +
                        "                                        phone: +254 722 582 328\n" +
                        "                    </p>\n" +
                        "                   <p style=\"margin:0;\">\n" +
                        "                                        P.O BOX 11001-00100, Nairobi\n" +
                        "                    </p>\n" +
                        "              </td>\n" +
                        "            </tr>\n" +
                        "              <td style=\"padding:0px; margin-bottom: 0px;text-align:center;font-size:12px;background-color:#ffffff;\">\n" +
                        "                       <img src='cid:rightSideImage' style='width:100%;'/>"  +
                        "              </td>\n" +
                        "            <tr>\n" +
                        "            </tr>\n" +
                        "           \n" +
                        "            <tr>\n" +
                        "              <td style=\"padding:30px;text-align:center;font-size:12px;background-color:#001c27;color:#cccccc;\">\n" +
                        "              <p style=\"margin:0;font-size:14px;line-height:20px;\">&reg; copyright 2021<br></p>\n" +
                        "              </td>\n" +
                        "            </tr>\n" +
                        "          </table>\n" +
                        "          <!--[if mso]>\n" +
                        "          </td>\n" +
                        "          </tr>\n" +
                        "          </table>\n" +
                        "          <![endif]-->\n" +
                        "        </td>\n" +
                        "      </tr>\n" +
                        "    </table>\n" +
                        "  </div>\n" +
                        "</body>\n" +
                        "</html>", true);
        helper.addInline("companyLogo",
                new File(company_logo_path));
        helper.addInline("rightSideImage",
                new File(banner_path));
//        helper.addAttachment("Payslip.pdf", emailAttachment);
        mailSender.send(mimeMessage);
        System.out.println("Sent successfully,sent to" + emailTo);
    }

//        @Scheduled(fixedRate = 1000L)
    public void sendNotificationMail(
            String emailTo,
            String emailSubject,
            String emailDesignationName,
            String emailMessage
            ) throws MessagingException {
        String emailSender = "${organizationMail}";
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
        helper.setFrom(emailSender);
        helper.setTo(emailTo);
        helper.setSubject(emailSubject);
        helper.setText(
                "<!DOCTYPE html>\n" +
                        "<html lang=\"en\" xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:o=\"urn:schemas-microsoft-com:office:office\">\n" +
                        "<head>\n" +
                        "  <meta charset=\"utf-8\">\n" +
                        "  <meta name=\"viewport\" content=\"width=device-width,initial-scale=1\">\n" +
                        "  <meta name=\"x-apple-disable-message-reformatting\">\n" +
                        "  <title></title>\n" +
                        "  <!--[if mso]>\n" +
                        "  <style>\n" +
                        "    table {border-collapse:collapse;border-spacing:0;border:none;margin:0;}\n" +
                        "    div, td {padding:0;}\n" +
                        "    div {margin:0 !important;}\n" +
                        "  </style>\n" +
                        "  <noscript>\n" +
                        "    <xml>\n" +
                        "      <o:OfficeDocumentSettings>\n" +
                        "        <o:PixelsPerInch>96</o:PixelsPerInch>\n" +
                        "      </o:OfficeDocumentSettings>\n" +
                        "    </xml>\n" +
                        "  </noscript>\n" +
                        "  <![endif]-->\n" +
                        "  <style>\n" +
                        "    table, td, div, h1, p {\n" +
                        "      font-family: Arial, sans-serif;\\n\"\n" +
                        "    }\n" +
                        "    @media screen and (max-width: 530px) {\n" +
                        "      .unsub {\n" +
                        "        display: block;\n" +
                        "        padding: 8px;\n" +
                        "        margin-top: 14px;\n" +
                        "        border-radius: 6px;\n" +
                        "        background-color: #555555;\n" +
                        "        text-decoration: none !important;\n" +
                        "        font-weight: bold;\n" +
                        "      }\n" +
                        "      .col-lge {\n" +
                        "        max-width: 100% !important;\n" +
                        "      }\n" +
                        "    }\n" +
                        "    @media screen and (min-width: 531px) {\n" +
                        "      .col-sml {\n" +
                        "        max-width: 27% !important;\n" +
                        "      }\n" +
                        "      .col-lge {\n" +
                        "        max-width: 73% !important;\n" +
                        "      }\n" +
                        "    }\n" +
                        "  </style>\n" +
                        "</head>\n" +
                        "<body style=\"margin:0;padding:0;word-spacing:normal;background-color: #566fff;\">\n" +
//                            "  <div role=\"article\" aria-roledescription=\"email\" lang=\"en\" style=\"text-size-adjust:100%;-webkit-text-size-adjust:100%;-ms-text-size-adjust:100%;background-color:#939297;\">\n" +
                        "  <div role=\"article\" aria-roledescription=\"email\" lang=\"en\" style=\"text-size-adjust:100%;-webkit-text-size-adjust:100%;-ms-text-size-adjust:100%;background-color:#566fff;\">\n" +
                        "    <table role=\"presentation\" style=\"width:100%; padding-top: 10px; padding-bottom: 10px; border:none;border-spacing:0;\">\n" +
                        "      <tr>\n" +
                        "        <td align=\"center\" style=\"padding:0;\">\n" +
                        "          <!--[if mso]>\n" +
                        "          <table role=\"presentation\" align=\"center\" style=\"width:600px; margin-top: 10px; margin-bottom: 10px;\">\n" +
                        "          <tr>\n" +
                        "          <td>\n" +
                        "          <![endif]-->\n" +
                        "          <table role=\"presentation\" style=\"width:94%;max-width:600px;border:none;border-spacing:0;text-align:left;font-family:Arial,sans-serif;font-size:16px;line-height:22px;color:#363636;\">\n" +
                        "              <td style=\"padding:5px;text-align:center;font-size:12px;background-color:#ffffff;\">\n" +
                        "                <a href=\"http://www.example.com/\" style=\"text-decoration:none;\"><img src='cid:companyLogo' alt=\"Logo\" style=\"width:20%; text-align:center; margin:auto; height:auto;border:none;text-decoration:none;color:#ffffff;\"></a>\n" +
                        "                <hr>\n" +
                        "              </td>\n" +
                        "            <tr>\n" +
                        "              <td style=\"padding:30px;background-color:#ffffff;\">\n" +
                        "                 <h1 style=\"margin-top:0;margin-bottom:16px;font-size:26px;line-height:32px;font-weight:bold;letter-spacing:-0.02em;\">E&M Tech Payslip Notification!</h1>\n" +
                        "                <p style=\"margin:0;\">\n" +
                        "                                        Hello "  + emailDesignationName + "\n" +
                        "                    </p>\n" +
                        "                   <p style=\"margin:0;\">\n" +
                        "                                        Kind regards\n" +
                        "                    </p>\n" +
                        "                   <p style=\"margin:0;\">\n" +
                        "                                        Human Resource Manager\n" +
                        "                    </p>\n" +
                        "                   <p style=\"margin:0;\">\n" +
                        "                                        E&M Tech technologies\n" +
                        "                    </p>\n" +
                        "                   <p style=\"margin:0;\">\n" +
                        "                                        phone: +254 722 582 328\n" +
                        "                    </p>\n" +
                        "                   <p style=\"margin:0;\">\n" +
                        "                                        P.O BOX 11001-00100, Nairobi\n" +
                        "                    </p>\n" +

                        "                   <p style=\"margin:0;\">\n" +
                        "                                        Kind regards\n" +
                        "                    </p>\n" +
                        "                   <p style=\"margin:0;\">\n" +
                        "                                        Human Resource Manager\n" +
                        "                    </p>\n" +
                        "                   <p style=\"margin:0;\">\n" +
                        "                                        E&M Tech technologies\n" +
                        "                    </p>\n" +
                        "                   <p style=\"margin:0;\">\n" +
                        "                                        phone: +254 722 582 328\n" +
                        "                    </p>\n" +
                        "                   <p style=\"margin:0;\">\n" +
                        "                                        P.O BOX 11001-00100, Nairobi\n" +
                        "                    </p>\n" +
                        "              </td>\n" +
                        "            </tr>\n" +
                        "              <td style=\"padding:0px; margin-bottom: 0px;text-align:center;font-size:12px;background-color:#ffffff;\">\n" +
                        "                       <img src='cid:rightSideImage' style='width:100%;'/>"  +
                        "              </td>\n" +
                        "            <tr>\n" +
                        "            </tr>\n" +
                        "           \n" +
                        "            <tr>\n" +
                        "              <td style=\"padding:30px;text-align:center;font-size:12px;background-color:#001c27;color:#cccccc;\">\n" +
                        "              <p style=\"margin:0;font-size:14px;line-height:20px;\">&reg; copyright 2021<br></p>\n" +
                        "              </td>\n" +
                        "            </tr>\n" +
                        "          </table>\n" +
                        "          <!--[if mso]>\n" +
                        "          </td>\n" +
                        "          </tr>\n" +
                        "          </table>\n" +
                        "          <![endif]-->\n" +
                        "        </td>\n" +
                        "      </tr>\n" +
                        "    </table>\n" +
                        "  </div>\n" +
                        "</body>\n" +
                        "</html>", true);
        helper.addInline("companyLogo",
                new File(company_logo_path));
        helper.addInline("rightSideImage",
                new File(banner_path));
//        helper.addAttachment("Payslip.pdf", emailAttachment);
        mailSender.send(mimeMessage);
        System.out.println("Sent successfully,sent to" + emailTo);
    }
    public void sendEmailWithAttachment(String email, String body, String subject, String attachment) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
        helper.setTo(email);
        helper.setFrom(from_mail);
        helper.setSubject("E&M Tech | Confidential! Password Reset.");
        helper.setText(
                "<!DOCTYPE html>\n" +
                        "<html lang=\"en\" xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:o=\"urn:schemas-microsoft-com:office:office\">\n" +
                        "<head>\n" +
                        "  <meta charset=\"utf-8\">\n" +
                        "  <meta name=\"viewport\" content=\"width=device-width,initial-scale=1\">\n" +
                        "  <meta name=\"x-apple-disable-message-reformatting\">\n" +
                        "  <title></title>\n" +
                        "  <!--[if mso]>\n" +
                        "  <style>\n" +
                        "    table {border-collapse:collapse;border-spacing:0;border:none;margin:0;}\n" +
                        "    div, td {padding:0;}\n" +
                        "    div {margin:0 !important;}\n" +
                        "  </style>\n" +
                        "  <noscript>\n" +
                        "    <xml>\n" +
                        "      <o:OfficeDocumentSettings>\n" +
                        "        <o:PixelsPerInch>96</o:PixelsPerInch>\n" +
                        "      </o:OfficeDocumentSettings>\n" +
                        "    </xml>\n" +
                        "  </noscript>\n" +
                        "  <![endif]-->\n" +
                        "  <style>\n" +
                        "    table, td, div, h1, p {\n" +
                        "      font-family: Arial, sans-serif;\\n\"\n" +
                        "    }\n" +
                        "    @media screen and (max-width: 530px) {\n" +
                        "      .unsub {\n" +
                        "        display: block;\n" +
                        "        padding: 8px;\n" +
                        "        margin-top: 14px;\n" +
                        "        border-radius: 6px;\n" +
                        "        background-color: #555555;\n" +
                        "        text-decoration: none !important;\n" +
                        "        font-weight: bold;\n" +
                        "      }\n" +
                        "      .col-lge {\n" +
                        "        max-width: 100% !important;\n" +
                        "      }\n" +
                        "    }\n" +
                        "    @media screen and (min-width: 531px) {\n" +
                        "      .col-sml {\n" +
                        "        max-width: 27% !important;\n" +
                        "      }\n" +
                        "      .col-lge {\n" +
                        "        max-width: 73% !important;\n" +
                        "      }\n" +
                        "    }\n" +
                        "  </style>\n" +
                        "</head>\n" +
                        "<body style=\"margin:0;padding:0;word-spacing:normal;background-color:#939297;\">\n" +
                        "  <div role=\"article\" aria-roledescription=\"email\" lang=\"en\" style=\"text-size-adjust:100%;-webkit-text-size-adjust:100%;-ms-text-size-adjust:100%;background-color:#939297;\">\n" +
                        "    <table role=\"presentation\" style=\"width:100%;border:none;border-spacing:0;\">\n" +
                        "      <tr>\n" +
                        "        <td align=\"center\" style=\"padding:0;\">\n" +
                        "          <!--[if mso]>\n" +
                        "          <table role=\"presentation\" align=\"center\" style=\"width:600px;\">\n" +
                        "          <tr>\n" +
                        "          <td>\n" +
                        "          <![endif]-->\n" +
                        "          <table role=\"presentation\" style=\"width:94%;max-width:600px;border:none;border-spacing:0;text-align:left;font-family:Arial,sans-serif;font-size:16px;line-height:22px;color:#363636;\">\n" +
                        "            <tr>\n" +
                        "              <td style=\"padding:40px 30px 30px 30px;text-align:center;font-size:24px;font-weight:bold;\">\n" +
                        "                <a href=\"http://www.example.com/\" style=\"text-decoration:none;\"><img src='cid:rightSideImage' alt=\"Logo\" style=\"width:100%;height:auto;border:none;text-decoration:none;color:#ffffff;\"></a>\n" +
                        "              </td>\n" +
                        "            </tr>\n" +
                        "            <tr>\n" +
                        "              <td style=\"padding:30px;background-color:#ffffff;\">\n" +
                        "                <h1 style=\"margin-top:0;margin-bottom:16px;font-size:26px;line-height:32px;font-weight:bold;letter-spacing:-0.02em;\">E&M Tech | Confidential!</h1>\n" +
                        "                   <p style=\"margin:0;\">\n" +
                        "                                        Hello \n" +
                        "                    </p>\n" +
                        "                   <p style=\"margin:0;\">\n" +
                        "                                         Your account has been created. \n" +
                        "                    </p>\n" +
                        "                   <p style=\"margin:0;\">\n" +
                        "                                        Find Your credentials bellow;\n" +
                        "                    </p>\n" +
                        "                   <p style=\"margin:0;\">\n" +
                        "                                        ==============> Sign in Credentials\n" +
                        "                    </p>\n" +

                        "                <p style=\"margin:0;\">\n" +
                        "                                        Username: " + email + "\n" +
                        "                    </p>\n" +
                        "                <p style=\"margin:0;\">\n" +
//                        "                                        Password: " + password + "\n" +
                        "                    </p>\n" +

                        "                   <p style=\"margin:0;\">\n" +
                        "                                        <==============> \n" +
                        "                    </p>\n" +
                        "                <p style=\"margin:0;\">\n" +
                        "                                        Warning!: Keep these credential private! Failure to that may lead to your account being revoked!.This is an automated process hence, any technical errors kindly contact us through the contact info below for assistance. Thank you. \n" +
                        "                    </p>\n" +

                        "                   <p style=\"margin:0;\">\n" +
                        "                                        Kind regards\n" +
                        "                    </p>\n" +
                        "                   <p style=\"margin:0;\">\n" +
                        "                                        Human Resource Manager\n" +
                        "                    </p>\n" +
                        "                   <p style=\"margin:0;\">\n" +
                        "                                        E&M Tech technologies\n" +
                        "                    </p>\n" +
                        "                   <p style=\"margin:0;\">\n" +
                        "                                        phone: +254 722 582 328\n" +
                        "                    </p>\n" +
                        "                   <p style=\"margin:0;\">\n" +
                        "                                        P.O BOX 11001-00100, Nairobi\n" +
                        "                    </p>\n" +
                        "              </td>\n" +
                        "            </tr>\n" +
                        "           \n" +
                        "            <tr>\n" +
                        "              <td style=\"padding:30px;text-align:center;font-size:12px;background-color:#001c27;color:#cccccc;\">\n" +
                        "              <p style=\"margin:0;font-size:14px;line-height:20px;\">&reg; copyright 2021<br></p>\n" +
                        "              </td>\n" +
                        "            </tr>\n" +
                        "          </table>\n" +
                        "          <!--[if mso]>\n" +
                        "          </td>\n" +
                        "          </tr>\n" +
                        "          </table>\n" +
                        "          <![endif]-->\n" +
                        "        </td>\n" +
                        "      </tr>\n" +
                        "    </table>\n" +
                        "  </div>\n" +

                        "<img src='cid:rightSideImage' style='width:100%;'/>" +
                        "</body>\n" +
                        "</html>", true);
        helper.addInline("rightSideImage",
                new File(files_path + "/e&m_banner.jpg"));
    }
}
