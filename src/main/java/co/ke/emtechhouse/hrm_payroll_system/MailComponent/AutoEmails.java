package co.ke.emtechhouse.hrm_payroll_system.MailComponent;

import co.ke.emtechhouse.hrm_payroll_system.ActivityLogComponent.ActivityRepo;
import co.ke.emtechhouse.hrm_payroll_system.AuthenticationModule.Models.Users.Users;
import co.ke.emtechhouse.hrm_payroll_system.AuthenticationModule.Models.Users.UsersRepository;
import co.ke.emtechhouse.hrm_payroll_system.EmployeeComponent.EmployeeService;
import co.ke.emtechhouse.hrm_payroll_system.OrganizationSettings.EmailConfigurations.Emailconfig;
import co.ke.emtechhouse.hrm_payroll_system.OrganizationSettings.EmailConfigurations.EmailconfigRepo;
import co.ke.emtechhouse.hrm_payroll_system.OrganizationSettings.EmailConfigurations.EmailconfigService;
import co.ke.emtechhouse.hrm_payroll_system._Batch_Processes.Batch_processesRepo;
import com.sun.istack.ByteArrayDataSource;
import lombok.extern.slf4j.Slf4j;
import net.sf.jasperreports.engine.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import javax.activation.DataSource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Month;
import java.util.*;

@Component
@Slf4j
public class AutoEmails<SalaryRepositoy> {
    @Value("${spring.datasource.url}")
    private String db;
    @Value("${spring.datasource.username}")
    private String username;
    @Value("${spring.datasource.password}")
    private String password;
    @Value("${reports_absolute_path}")
    private String files_path;
    @Value("${company_logo_path}")
    private String company_logo_path;
    @Value("${image_banner}")
    private String banner_path;
    @Value("${from_mail}")
    private String from_mail;
    private final JavaMailSender mailSender;
    private final ActivityRepo activityRepo;
    private final EmployeeService employeeService;
    private final EmailconfigService emailconfigService;
    private final EmailconfigRepo emailconfigRepo;
    private final Batch_processesRepo batch_processesRepo;
    private final UsersRepository userRepository;
    LocalDate currentDate= LocalDate.now();
    public AutoEmails(JavaMailSender mailSender, ActivityRepo activityRepo, EmployeeService employeeService, EmailconfigService emailconfigService, EmailconfigRepo emailconfigRepo, Batch_processesRepo batch_processesRepo, UsersRepository userRepository) {
        this.mailSender = mailSender;
        this.activityRepo = activityRepo;
        this.employeeService = employeeService;
        this.emailconfigService = emailconfigService;
        this.emailconfigRepo = emailconfigRepo;
        this.batch_processesRepo = batch_processesRepo;
        this.userRepository = userRepository;
    }
    //    @Scheduled(fixedRate = 5000L)
//@Scheduled(cron="0 0 3 * * *")
//    @Scheduled(fixedRate = 5200L)
    public void sendDummyMonthlySalaryReportToExecutives() throws FileNotFoundException, JRException, SQLException, MessagingException {
        try {
            //    Get Email Confirurations
            String email_type = "Dummy_Payroll_Report";
            Optional<Emailconfig> emailconfig = emailconfigRepo.findByEmailType(email_type);
            if (emailconfig.isPresent()) {
//        // Get month from date
                Month getMonth = currentDate.getMonth();
                String month = getMonth.toString();
                Integer getYear = currentDate.getYear();
                String year = getYear.toString();
//     TODO: Send Dummy Payroll to Director and HR
                List<Users> _staff = userRepository.findAll();
//            List<EmployeeEntity> employees = employeeService.findAllEmployee();
                for (int i = 0; i < _staff.size(); i++) {
                    String employeeEmail = _staff.get(i).getEmail();
                    Connection connection = DriverManager.getConnection(this.db, this.username, this.password);
                    JasperReport compileReport = JasperCompileManager.compileReport(new FileInputStream(files_path+"/dummypayrollreport.jrxml"));
                    Map<String, Object> parameter = new HashMap<String, Object>();
                    parameter.put("year", year);
                    parameter.put("month", month);
                    JasperPrint report = JasperFillManager.fillReport(compileReport, parameter, connection);
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    JasperExportManager.exportReportToPdfStream(report, baos);
                    DataSource dummypayrollreport = new ByteArrayDataSource(baos.toByteArray(), "application/pdf");

                    MimeMessage mimeMessage = mailSender.createMimeMessage();
                    MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
                    helper.setTo(employeeEmail);
                    helper.setFrom(from_mail);
                    helper.setSubject(emailconfig.get().getEmail_subject());
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
                                    "                 <h1 style=\"margin-top:0;margin-bottom:16px;font-size:26px;line-height:32px;font-weight:bold;letter-spacing:-0.02em;\">"+  emailconfig.get().getEmail_heading() +"</h1>\n" +
                                    "                <p style=\"margin:0;\">\n" + emailconfig.get().getEmail_salutation() + "\n" +
                                    "                    </p>\n" +
                                    "                   <p style=\"margin:0;\">\n" + emailconfig.get().getEmail_message() +"\n" +
                                    "                    </p>\n" +
                                    "                   <p style=\"margin:0;\">\n" + emailconfig.get().getEmail_remarks() +"\n" +
                                    "                    </p>\n" +
                                    "                   <p style=\"margin:0;\">\n" + emailconfig.get().getEmail_regards_from() + "\n" +
                                    "                    </p>\n" +
                                    "                   <p style=\"margin:0;\">\n" + emailconfig.get().getEmail_organization_name() + "\n" +
                                    "                    </p>\n" +
                                    "                   <p style=\"margin:0;\">\n"+  "<b>Tel/Phone: </b> "+ emailconfig.get().getEmail_organization_phone() +"\n" +
                                    "                    </p>\n" +
                                    "                   <p style=\"margin:0;\">\n" + "<b>Email: </b> " + emailconfig.get().getEmail_organization_mail() + "\n" +
                                    "                    </p>\n" +
                                    "                   <p style=\"margin:0;\">\n" + "<b>Address: </b> " + emailconfig.get().getEmail_organization_address() + "\n" +
                                    "                    </p>\n" +
                                    "                   <p style=\"margin:0;\">\n" + "<b>Location: </b> " + emailconfig.get().getEmail_organization_location() + "\n" +
                                    "                    </p>\n" +
                                    "                   <p style=\"margin:0;\">\n" + "<b>Website: </b> " + emailconfig.get().getEmail_organization_website() + "\n" +
                                    "                    </p>\n" +
                                    "              </td>\n" +
                                    "            </tr>\n" +
                                    "              <td style=\"padding:0px; margin-bottom: 0px;text-align:center;font-size:12px;background-color:#ffffff;\">\n" +
                                    "                       <img src='cid:rightSideImage' style='width:100%;'/>"  +
                                    "              </td>\n" +
                                    "              <td style=\"padding:0px; margin-bottom: 0px;text-align:center;font-size:12px;background-color:#ffffff;\">\n" +
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
                    helper.addAttachment("Dummy_Payroll_Report.pdf", dummypayrollreport);
                    mailSender.send(mimeMessage);
                    log.info("Sent successfully,sent to: {}", employeeEmail);
                }
            }else
            {
                log.info("Sorry! Emailing failed! No email Configuration set for Dummy Payroll Report!");
            }
        } catch (Exception e) {
            log.info("Generate Employee Salary Report Error {}"+e);
        }
        }
//    @Scheduled(fixedRate = 5250L)
    public void sendCommitedMonthlySalaryReportToExecutives() throws FileNotFoundException, JRException, SQLException, MessagingException {
        try {
            //    Get Email Confirurations
            String email_type = "Commited_Payroll_Report";
            Optional<Emailconfig> emailconfig = emailconfigRepo.findByEmailType(email_type);
            if (emailconfig.isPresent()) {
//        // Get month from date
                Month getMonth = currentDate.getMonth();
                String month = getMonth.toString();
                Integer getYear = currentDate.getYear();
                String year = getYear.toString();
//     TODO: Send Commited Payroll to Director and HR
                List<Users> _staff = userRepository.findAll();
//            List<EmployeeEntity> employees = employeeService.findAllEmployee();
                for (int i = 0; i < _staff.size(); i++) {
                    String employeeEmail = _staff.get(i).getEmail();
                    Connection connection = DriverManager.getConnection(this.db, this.username, this.password);
                    JasperReport compileReport = JasperCompileManager.compileReport(new FileInputStream(files_path+"/commitedsalaryreport.jrxml"));
                    Map<String, Object> parameter = new HashMap<String, Object>();
                    parameter.put("year", year);
                    parameter.put("month", month);
                    JasperPrint report = JasperFillManager.fillReport(compileReport, parameter, connection);
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    JasperExportManager.exportReportToPdfStream(report, baos);
                    DataSource commitedsalaryreport = new ByteArrayDataSource(baos.toByteArray(), "application/pdf");
                    MimeMessage mimeMessage = mailSender.createMimeMessage();
                    MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
                    helper.setTo(employeeEmail);
                    helper.setFrom(from_mail);
                    helper.setSubject(emailconfig.get().getEmail_subject());
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
                                    "                 <h1 style=\"margin-top:0;margin-bottom:16px;font-size:26px;line-height:32px;font-weight:bold;letter-spacing:-0.02em;\">"+  emailconfig.get().getEmail_heading() +"</h1>\n" +
                                    "                <p style=\"margin:0;\">\n" + emailconfig.get().getEmail_salutation()  + "\n" +
                                    "                    </p>\n" +
                                    "                   <p style=\"margin:0;\">\n" + emailconfig.get().getEmail_message() +"\n" +
                                    "                    </p>\n" +
                                    "                   <p style=\"margin:0;\">\n" + emailconfig.get().getEmail_remarks() +"\n" +
                                    "                    </p>\n" +
                                    "                   <p style=\"margin:0;\">\n" + emailconfig.get().getEmail_regards_from() + "\n" +
                                    "                    </p>\n" +
                                    "                   <p style=\"margin:0;\">\n" + emailconfig.get().getEmail_organization_name() + "\n" +
                                    "                    </p>\n" +
                                    "                   <p style=\"margin:0;\">\n"+  "<b>Tel/Phone: </b> "+ emailconfig.get().getEmail_organization_phone() +"\n" +
                                    "                    </p>\n" +
                                    "                   <p style=\"margin:0;\">\n" + "<b>Email: </b> " + emailconfig.get().getEmail_organization_mail() + "\n" +
                                    "                    </p>\n" +
                                    "                   <p style=\"margin:0;\">\n" + "<b>Address: </b> " + emailconfig.get().getEmail_organization_address() + "\n" +
                                    "                    </p>\n" +
                                    "                   <p style=\"margin:0;\">\n" + "<b>Location: </b> " + emailconfig.get().getEmail_organization_location() + "\n" +
                                    "                    </p>\n" +
                                    "                   <p style=\"margin:0;\">\n" + "<b>Website: </b> " + emailconfig.get().getEmail_organization_website() + "\n" +
                                    "                    </p>\n" +
                                    "              </td>\n" +
                                    "            </tr>\n" +
                                    "              <td style=\"padding:0px; margin-bottom: 0px;text-align:center;font-size:12px;background-color:#ffffff;\">\n" +
                                    "                       <img src='cid:rightSideImage' style='width:100%;'/>"  +
                                    "              </td>\n" +
                                    "              <td style=\"padding:0px; margin-bottom: 0px;text-align:center;font-size:12px;background-color:#ffffff;\">\n" +
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
                    helper.addAttachment("Commited_Salary_Report.pdf", commitedsalaryreport);
                    mailSender.send(mimeMessage);
                    log.info("Sent successfully,sent to: {}", employeeEmail);
                }
            }else
            {
                log.info("Sorry! Emailing failed! No email Configuration set for Committed Salary!");
            }
        } catch (Exception e) {
            log.info("Generate Employee Salary Report Error {}"+e);
        }
    }

    //    @Scheduled(fixedRate = 5250L)
    public void accountCreation(String email_type, String email, String password) throws FileNotFoundException, JRException, SQLException, MessagingException {
        try {
            //    Get Email Confirurations
            Optional<Emailconfig> emailconfig = emailconfigRepo.findByEmailType(email_type);
            if (emailconfig.isPresent()) {
//        // Get month from date
                Month getMonth = currentDate.getMonth();
                String month = getMonth.toString();
                Integer getYear = currentDate.getYear();
                String year = getYear.toString();
//     TODO: Send Commited Payroll to Director and HR
                MimeMessage mimeMessage = mailSender.createMimeMessage();
                MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
                helper.setTo(email);
                helper.setFrom(from_mail);
                helper.setSubject(emailconfig.get().getEmail_subject());
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
                                "                 <h1 style=\"margin-top:0;margin-bottom:16px;font-size:26px;line-height:32px;font-weight:bold;letter-spacing:-0.02em;\">"+  emailconfig.get().getEmail_heading() +"</h1>\n" +
                                "                <p style=\"margin:0;\">\n" + emailconfig.get().getEmail_salutation()  + "\n" +
                                "                    </p>\n" +
                                "                   <p style=\"margin:0;\">\n" + emailconfig.get().getEmail_message() +"\n" +
                                "                   <p style=\"margin:0;\">\n" +
                                "                                        Sign in Credentials\n" +
                                "                    </p>\n" +
                                "                   <p style=\"margin:0;\">\n" +
                                "                                        =========================\n" +
                                "                    </p>\n" +

                                "                <p style=\"margin:0;\">\n" +
                                "                                        Username: " + email + "\n" +
                                "                    </p>\n" +
                                "                <p style=\"margin:0;\">\n" +
                                "                                        Password: " + password + "\n" +
                                "                    </p>\n" +
                                "                    </p>\n" +
                                "                   <p style=\"margin:0;\">\n" + emailconfig.get().getEmail_remarks() +"\n" +
                                "                    </p>\n" +
                                "                   <p style=\"margin:0;\">\n" + emailconfig.get().getEmail_regards_from() + "\n" +
                                "                    </p>\n" +
                                "                   <p style=\"margin:0;\">\n" + emailconfig.get().getEmail_organization_name() + "\n" +
                                "                    </p>\n" +
                                "                   <p style=\"margin:0;\">\n"+  "<b>Tel/Phone: </b> "+ emailconfig.get().getEmail_organization_phone() +"\n" +
                                "                    </p>\n" +
                                "                   <p style=\"margin:0;\">\n" + "<b>Email: </b> " + emailconfig.get().getEmail_organization_mail() + "\n" +
                                "                    </p>\n" +
                                "                   <p style=\"margin:0;\">\n" + "<b>Address: </b> " + emailconfig.get().getEmail_organization_address() + "\n" +
                                "                    </p>\n" +
                                "                   <p style=\"margin:0;\">\n" + "<b>Location: </b> " + emailconfig.get().getEmail_organization_location() + "\n" +
                                "                    </p>\n" +
                                "                   <p style=\"margin:0;\">\n" + "<b>Website: </b> " + emailconfig.get().getEmail_organization_website() + "\n" +
                                "                    </p>\n" +
                                "              </td>\n" +
                                "            </tr>\n" +
                                "              <td style=\"padding:0px; margin-bottom: 0px;text-align:center;font-size:12px;background-color:#ffffff;\">\n" +
                                "                       <img src='cid:rightSideImage' style='width:100%;'/>"  +
                                "              </td>\n" +
                                "              <td style=\"padding:0px; margin-bottom: 0px;text-align:center;font-size:12px;background-color:#ffffff;\">\n" +
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
                mailSender.send(mimeMessage);
                log.info("Sent successfully,sent to {}", email);

            }else
            {
                log.info("Sorry! Emailing failed! No email Configuration set for {}", email_type, "!");
            }
        } catch (Exception e) {
            log.info("Generate Employee Salary Report Error {}"+e);
        }
    }

        }
